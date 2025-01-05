package com.pbo.warehouse.api.services;

import org.mindrot.jbcrypt.BCrypt;

import com.pbo.warehouse.api.dto.request.UpdateUserProfileRequestDto;
import com.pbo.warehouse.api.dto.response.GetUserProfileResponseDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.UserRepository;
import com.pbo.warehouse.api.services.interfaces.UserServiceIf;

public class UserService implements UserServiceIf {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public GetUserProfileResponseDto getUserProfile(String email) {
        GetUserProfileResponseDto response = new GetUserProfileResponseDto();

        User user = userRepository.getUserByEmail(email);

        response.setEmail(user.getEmail());
        response.setName(user.getName());

        return response;
    }

    @Override
    public void updateUserProfile(UpdateUserProfileRequestDto data) {
        User currentUser = userRepository.getUserByEmail(data.getOldEmail());
        if (currentUser == null) {
            throw new AppException(404, "User tidak ditemukan");
        }

        if (data.getNewEmail() == currentUser.getEmail()) {
            data.setNewEmail(null);
        }

        if (data.getNewEmail() != null) {
            User existingUser = userRepository.getUserByEmail(data.getNewEmail());
            if (existingUser != null) {
                if (!existingUser.getEmail().equals(data.getNewEmail())) {
                    throw new AppException(400, "Email sudah digunakan");
                }
            }
            currentUser.setEmail(data.getNewEmail());
        }

        if (data.getPassword() != null) {
            if (!data.getPassword().equals(data.getConfirmPassword())) {
                throw new AppException(400, "Password tidak sama");
            }
            currentUser.setPassword(BCrypt.hashpw(data.getPassword(), BCrypt.gensalt()));
        }

        currentUser.setName(data.getName());

        if (!userRepository.updateUser(currentUser)) {
            throw new AppException(500, "Gagal mengupdate user");
        }
    }

}

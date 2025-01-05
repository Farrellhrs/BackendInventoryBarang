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
        User user = new User(data.getName(), data.getNewEmail(), data.getPassword());

        User currentUser = userRepository.getUserByEmail(data.getOldEmail());

        User existingUser = new User();
        if (data.getNewEmail() != null) {
            existingUser = userRepository.getUserByEmail(data.getNewEmail());
            if (existingUser != null) {
                if (!existingUser.getEmail().equals(data.getNewEmail())) {
                    throw new AppException(400, "Email sudah digunakan");
                }
            }
        }

        String hashedPassword = BCrypt.hashpw(data.getPassword(), BCrypt.gensalt());

        user.setId(currentUser.getId());
        user.setPassword(hashedPassword);

        if (!userRepository.updateUser(user)) {
            throw new AppException(500, "Gagal mengupdate user");
        }
    }

}

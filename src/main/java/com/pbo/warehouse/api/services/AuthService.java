package com.pbo.warehouse.api.services;

import com.pbo.warehouse.api.dto.request.LoginRequestDto;
import com.pbo.warehouse.api.dto.request.RegisterRequestDto;
import com.pbo.warehouse.api.dto.response.LoginResponseDto;
import com.pbo.warehouse.api.dto.response.RegisterResponseDto;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.UserRepository;
import com.pbo.warehouse.api.services.interfaces.AuthServiceIf;

public class AuthService implements AuthServiceIf {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public LoginResponseDto login(LoginRequestDto data) {
        User user = userRepository.getUserByEmail(data.getEmail());

        if (user == null) {
            throw new UnsupportedOperationException("User tidak ditemukan");
        }

        if (!data.getPassword().equals(user.getPassword())) {
            throw new UnsupportedOperationException("Password salah");
        }

        String token = "token";

        return new LoginResponseDto(user.getName(), user.getEmail(), token);
    }

    @Override
    public RegisterResponseDto register(RegisterRequestDto data) {
        try {
            User user = new User(data.getName(), data.getEmail(), data.getPassword());

            User existingUser = userRepository.getUserByEmail(data.getEmail());
            if (existingUser != null) {
                throw new Error("Email sudah terdaftar");
            }

            if (!userRepository.addUser(user)) {
                throw new Error("Gagal menambahkan user");
            }

            String token = "token";

            return new RegisterResponseDto(data.getName(), data.getEmail(), token);
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

}

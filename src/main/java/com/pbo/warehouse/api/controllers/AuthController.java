package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.AuthControllerIf;
import com.pbo.warehouse.api.dto.ResponseBody;
import com.pbo.warehouse.api.dto.request.LoginRequestDto;
import com.pbo.warehouse.api.dto.request.RegisterRequestDto;
import com.pbo.warehouse.api.dto.response.LoginResponseDto;
import com.pbo.warehouse.api.dto.response.RegisterResponseDto;
import com.pbo.warehouse.api.services.AuthService;

import spark.Request;
import spark.Response;

public class AuthController implements AuthControllerIf {
    private final AuthService authService = new AuthService();

    @Override
    public ResponseBody login(Request req, Response res) {
        LoginRequestDto loginRequest = gson.fromJson(req.body(), LoginRequestDto.class);

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (email == null || password == null) {
            return responseBody.error(400, "Email dan password tidak boleh kosong", null);
        }

        LoginResponseDto response = authService.login(loginRequest);

        return responseBody.success(200, "Login berhasil", gson.toJson(response));
    }

    @Override
    public ResponseBody register(Request req, Response res) {
        RegisterRequestDto registerRequest = gson.fromJson(req.body(), RegisterRequestDto.class);

        String username = registerRequest.getName();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();

        if (username == null || email == null || password == null || confirmPassword == null) {
            return responseBody.error(400, "Semua field harus diisi", null);
        }

        if (!password.equals(confirmPassword)) {
            return responseBody.error(400, "Password dan konfirmasi password tidak sama", null);
        }

        try {
            RegisterResponseDto response = authService.register(registerRequest);
            return responseBody.success(200, "Registrasi berhasil", gson.toJson(response));
        } catch (Exception e) {
            return responseBody.error(400, e.getMessage(), null);
        }
    }

    @Override
    public void logout(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

}

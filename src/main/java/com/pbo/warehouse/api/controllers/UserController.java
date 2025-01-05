package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.UserControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.UpdateUserProfileRequestDto;
import com.pbo.warehouse.api.dto.response.GetUserProfileResponseDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.services.UserService;

import spark.Request;
import spark.Response;

public class UserController implements UserControllerIf {
    private final UserService userService = new UserService();

    @Override
    public ResponseBodyDto getUserProfile(Request req, Response res) {
        ResponseBodyDto responseBody = new ResponseBodyDto();

        String email = req.attribute("email");
        if (email == null) {
            return responseBody.error(401, "Unauthorized", null);
        }

        GetUserProfileResponseDto response = userService.getUserProfile(email);

        return responseBody.success(200, "Berhasil mendapatkan profil", gson.toJson(response));
    }

    @Override
    public ResponseBodyDto updateUserProfile(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        UpdateUserProfileRequestDto requestBody = gson.fromJson(req.body(), UpdateUserProfileRequestDto.class);

        String oldEmail = req.attribute("email");
        if (oldEmail == null) {
            return responseBody.error(401, "Unauthorized", null);
        }

        String password = requestBody.getPassword();
        String confirmPassword = requestBody.getConfirmPassword();
        
        if (password != null || confirmPassword != null) {
            if (!password.equals(confirmPassword)) {
                return responseBody.error(400, "Password tidak sama", null);
            }
        }

        requestBody.setOldEmail(oldEmail);

        try {
            userService.updateUserProfile(requestBody);
            return responseBody.success(200, "Update profil berhasil", null);
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), null);
        }
    }

}

package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.UserControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.response.GetUserProfileResponseDto;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
    }

}

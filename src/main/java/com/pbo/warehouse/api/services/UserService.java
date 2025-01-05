package com.pbo.warehouse.api.services;

import com.pbo.warehouse.api.dto.request.UpdateUserProfileRequestDto;
import com.pbo.warehouse.api.dto.response.GetUserProfileResponseDto;
import com.pbo.warehouse.api.services.interfaces.UserServiceIf;

public class UserService implements UserServiceIf {

    @Override
    public GetUserProfileResponseDto getUserProfile(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfile'");
    }

    @Override
    public void updateUserProfile(UpdateUserProfileRequestDto user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
    }

}

package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.request.UpdateUserProfileRequestDto;
import com.pbo.warehouse.api.dto.response.GetUserProfileResponseDto;

public interface UserServiceIf {
    GetUserProfileResponseDto getUserProfile(String email);

    void updateUserProfile(UpdateUserProfileRequestDto user);
}

package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.request.LoginRequestDto;
import com.pbo.warehouse.api.dto.request.RegisterRequestDto;
import com.pbo.warehouse.api.dto.response.LoginResponseDto;
import com.pbo.warehouse.api.dto.response.RegisterResponseDto;

public interface AuthServiceIf {
    LoginResponseDto login(LoginRequestDto data);

    RegisterResponseDto register(RegisterRequestDto data);

    void logout();
}

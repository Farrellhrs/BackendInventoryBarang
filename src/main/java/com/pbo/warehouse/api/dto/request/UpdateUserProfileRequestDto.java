package com.pbo.warehouse.api.dto.request;

public class UpdateUserProfileRequestDto {
    private String name;
    private String email;
    private String password;

    public UpdateUserProfileRequestDto() {
    }

    public UpdateUserProfileRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}

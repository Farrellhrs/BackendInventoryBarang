package com.pbo.warehouse.api.dto.response;

public class CreatorResponseDto {
    private String name;
    private String email;

    public CreatorResponseDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}

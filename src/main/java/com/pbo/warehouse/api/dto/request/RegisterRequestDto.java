package com.pbo.warehouse.api.dto.request;

public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String registerKey;

    public RegisterRequestDto(String name, String email, String password, String confirmPassword, String registerKey) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.registerKey = registerKey;
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

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public String getRegisterKey() {
        return this.registerKey;
    }
}

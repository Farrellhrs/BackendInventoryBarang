package com.pbo.warehouse.api.dto.request;

public class UpdateUserProfileRequestDto {
    private String name;
    private String newEmail;
    private String oldEmail;
    private String password;
    private String confirmPassword;

    public UpdateUserProfileRequestDto() {
    }

    public UpdateUserProfileRequestDto(String name, String newEmail, String oldEmail, String password, String confirmPassword) {
        this.name = name;
        this.newEmail = newEmail;
        this.oldEmail = oldEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}

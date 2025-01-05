package com.pbo.warehouse.api.dto.response;

import java.util.Date;

public class GetUserProfileResponseDto {
    private String id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

    public GetUserProfileResponseDto() {
    }

    public GetUserProfileResponseDto(String id, String name, String email, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.pbo.warehouse.api.dto.response;

import java.util.Date;

public class ProductDetailsResponseDto {
    // Electronic details
    private String type;

    // Cosmetic & Fnb details
    private Date expireDate;

    public ProductDetailsResponseDto() {
    }

    public ProductDetailsResponseDto(String type, Date expireDate) {
        this.type = type;
        this.expireDate = expireDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}

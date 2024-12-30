package com.pbo.warehouse.api.dto.request;

public class AddInOutRequestDto {
    private String productId;
    private int quantity;
    private String recordDate;

    public AddInOutRequestDto(String productId, int quantity, String recordDate) {
        this.productId = productId;
        this.quantity = quantity;
        this.recordDate = recordDate;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRecordDate() {
        return recordDate;
    }
}

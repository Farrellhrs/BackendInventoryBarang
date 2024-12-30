package com.pbo.warehouse.api.dto.request;

public class UpdateInOutRequestDto {
    private String currentProductId;
    private String newProductId;
    private int quantity;
    private String recordDate;

    public UpdateInOutRequestDto() {
    }

    public UpdateInOutRequestDto(String currentProductId, String newProductId, int quantity, String recordDate) {
        this.currentProductId = currentProductId;
        this.newProductId = newProductId;
        this.quantity = quantity;
        this.recordDate = recordDate;
    }

    public String getCurrentProductId() {
        return currentProductId;
    }

    public String getNewProductId() {
        return newProductId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRecordDate() {
        return recordDate;
    }
}

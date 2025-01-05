package com.pbo.warehouse.api.dto.request;

public class UpdateInOutRequestDto {
    private int id;
    private String currentProductId;
    private String newProductId;
    private int quantity;
    private String recordDate;

    public UpdateInOutRequestDto() {
    }

    public UpdateInOutRequestDto(int id, String currentProductId, String newProductId, int quantity, String recordDate) {
        this.id = id;
        this.currentProductId = currentProductId;
        this.newProductId = newProductId;
        this.quantity = quantity;
        this.recordDate = recordDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentProductId() {
        return currentProductId;
    }

    public void setCurrentProductId(String currentProductId) {
        this.currentProductId = currentProductId;
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

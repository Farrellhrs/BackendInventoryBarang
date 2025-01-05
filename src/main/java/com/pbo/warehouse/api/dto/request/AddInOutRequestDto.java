package com.pbo.warehouse.api.dto.request;

public class AddInOutRequestDto {
    private String productId;
    private int quantity;
    private String recordDate;
    private String type;
    private String createdBy;

    public AddInOutRequestDto(String productId, int quantity, String recordDate, String type) {
        this.productId = productId;
        this.quantity = quantity;
        this.recordDate = recordDate;
        this.type = type;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
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

    public String getType() {
        return type;
    }
}

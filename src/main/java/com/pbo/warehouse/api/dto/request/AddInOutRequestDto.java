package com.pbo.warehouse.api.dto.request;

public class AddInOutRequestDto {
    private String productId;
    private int quantity;
    private String recordDate;
    private String type;

    public AddInOutRequestDto(String productId, int quantity, String recordDate, String type) {
        this.productId = productId;
        this.quantity = quantity;
        this.recordDate = recordDate;
        this.type = type;
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

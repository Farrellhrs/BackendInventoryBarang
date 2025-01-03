package com.pbo.warehouse.api.dto.request;

public class DeleteProductRequestDto {
    private String id;
    private String skuCode;
    private String name;
    private String category;
    private int maxStock;
    private String createdBy;

    public DeleteProductRequestDto() {
    }

    public DeleteProductRequestDto(String id, String skuCode, String name, String category, int maxStock, String createdBy) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.category = category;
        this.maxStock = maxStock;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}

package com.pbo.warehouse.api.dto.request;

public class AddProductRequestDto {
    private String skuCode;
    private String name;
    private String category;
    private int maxStock;
    private int stock;
    private String createdBy;
    private Object additionalField; 
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(Object additionalField) {
        this.additionalField = additionalField;
    }
}

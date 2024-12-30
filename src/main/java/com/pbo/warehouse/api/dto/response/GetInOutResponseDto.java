package com.pbo.warehouse.api.dto.response;

public class GetInOutResponseDto {
    private int id;
    private String productId;
    private String productName;
    private String skuCode;
    private String category;
    private int quantity;
    private int currentStock;
    private int maxStock;
    private String entryDate;
    private CreatorResponseDto createdBy;
    private ProductDetailsResponseDto details;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCurrentStock() {
        return this.currentStock;
    }

    public void setCurrentStock(int stock) {
        this.currentStock = stock;
    }

    public int getMaxStock() {
        return this.maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public String getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public CreatorResponseDto getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CreatorResponseDto createdBy) {
        this.createdBy = createdBy;
    }

    public ProductDetailsResponseDto getDetails() {
        return this.details;
    }

    public void setDetails(ProductDetailsResponseDto details) {
        this.details = details;
    }
}

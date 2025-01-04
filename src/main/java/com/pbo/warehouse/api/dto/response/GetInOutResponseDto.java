package com.pbo.warehouse.api.dto.response;

import java.util.Date;

import com.pbo.warehouse.api.models.InOutRecord;

public class GetInOutResponseDto {
    private int id;
    private String productId;
    private String productName;
    private String skuCode;
    private String category;
    private int quantity;
    private int currentStock;
    private int maxStock;
    private Date entryDate;
    private CreatorResponseDto createdBy;
    private ProductDetailsResponseDto details;

    public GetInOutResponseDto() {

    }

    public GetInOutResponseDto(int id, String productId, String productName, String skuCode, String category,
            int quantity,
            int currentStock, int maxStock, Date entryDate, ProductDetailsResponseDto details) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.skuCode = skuCode;
        this.category = category;
        this.quantity = quantity;
        this.currentStock = currentStock;
        this.maxStock = maxStock;
        this.entryDate = entryDate;
        this.details = details;
    }

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

    public Date getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(Date entryDate) {
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

    public static GetInOutResponseDto fromEntityElectronic(InOutRecord inout) {
        return new GetInOutResponseDto(
                inout.getId(),
                inout.getProductId(),
                inout.getProductElectronic().getName(),
                inout.getProductElectronic().getSkuCode(),
                inout.getProductElectronic().getCategory(),
                inout.getQuantity(),
                inout.getProductElectronic().getStock(),
                inout.getProductElectronic().getMaxStock(),
                inout.getRecordDate(),
                new ProductDetailsResponseDto(inout.getProductElectronic().getType(), null));
    }

    public static GetInOutResponseDto fromEntityFnb(InOutRecord inout) {
        return new GetInOutResponseDto(
                inout.getId(),
                inout.getProductId(),
                inout.getProductFnb().getName(),
                inout.getProductFnb().getSkuCode(),
                inout.getProductFnb().getCategory(),
                inout.getQuantity(),
                inout.getProductFnb().getStock(),
                inout.getProductFnb().getMaxStock(),
                inout.getRecordDate(),
                new ProductDetailsResponseDto(null, inout.getProductFnb().getExpireDate()));
    }

    public static GetInOutResponseDto fromEntityCosmetic(InOutRecord inout) {
        return new GetInOutResponseDto(
                inout.getId(),
                inout.getProductId(),
                inout.getProductCosmetic().getName(),
                inout.getProductCosmetic().getSkuCode(),
                inout.getProductCosmetic().getCategory(),
                inout.getQuantity(),
                inout.getProductCosmetic().getStock(),
                inout.getProductCosmetic().getMaxStock(),
                inout.getRecordDate(),
                new ProductDetailsResponseDto(null, inout.getProductCosmetic().getExpireDate()));
    }
}

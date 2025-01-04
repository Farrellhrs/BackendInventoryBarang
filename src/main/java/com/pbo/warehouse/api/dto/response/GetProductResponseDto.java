package com.pbo.warehouse.api.dto.response;

import java.util.Date;

import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;

public class GetProductResponseDto {
    private String id;
    private String skuCode;
    private String productName;
    private String category;
    private Date entryDate;
    private int stock;
    private int maxStock;
    private CreatorResponseDto createdBy;
    private ProductDetails details;

    public static class ProductDetails {
        // Electronic details
        private String type;

        // Cosmetic & Fnb details
        private Date expireDate;
        private Integer daysBeforeExpire;

        public ProductDetails() {
        }

        public ProductDetails(String type, Date expireDate, Integer daysBeforeExpire) {
            this.type = type;
            this.expireDate = expireDate;
            this.daysBeforeExpire = daysBeforeExpire;
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

        public Integer getDaysBeforeExpire() {
            return daysBeforeExpire;
        }

        public void setDaysBeforeExpire(Integer daysBeforeExpire) {
            this.daysBeforeExpire = daysBeforeExpire;
        }
    }

    public GetProductResponseDto() {
    }

    public void setName(String name){
        this.productName = name;
    }

    public void setSkuCode(String code){
        this.skuCode = code;
    }

    public void setMaxStock(int maxstock){
        this.maxStock = maxstock;
    }

    public void setId(String id){
        this.id = id;
    }
    
    public GetProductResponseDto(String id, String skuCode, String productName, String category, Date entryDate,
            int stock, int maxStock, ProductDetails details) {
        this.id = id;
        this.skuCode = skuCode;
        this.productName = productName;
        this.category = category;
        this.entryDate = entryDate;
        this.stock = stock;
        this.maxStock = maxStock;
        this.details = details;
    }

    public String getId() {
        return this.id;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getCategory() {
        return this.category;
    }

    public Date getEntryDate() {
        return this.entryDate;
    }

    public int getStock() {
        return this.stock;
    }

    public int getMaxStock() {
        return this.maxStock;
    }

    public CreatorResponseDto getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CreatorResponseDto user) {
        this.createdBy = user;
    }

    public ProductDetails getDetails() {
        return this.details;
    }

    public void setDetails(ProductDetails details) {
        this.details = details;
    }

    public static GetProductResponseDto fromEntityElectronic(ProductElectronic product) {
        return new GetProductResponseDto(
                product.getId(),
                product.getSkuCode(),
                product.getName(),
                product.getCategory(),
                product.getCreatedAt(),
                product.getStock(),
                product.getMaxStock(),
                new ProductDetails(product.getType(), null, null));
    }

    public static GetProductResponseDto fromEntityCosmetic(ProductCosmetic product) {
        return new GetProductResponseDto(
                product.getId(),
                product.getSkuCode(),
                product.getName(),
                product.getCategory(),
                product.getCreatedAt(),
                product.getStock(),
                product.getMaxStock(),
                new ProductDetails(null, product.getExpireDate(), product.getDaysBeforeExpire()));
    }

    public static GetProductResponseDto fromEntityFnb(ProductFnb product) {
        return new GetProductResponseDto(
                product.getId(),
                product.getSkuCode(),
                product.getName(),
                product.getCategory(),
                product.getCreatedAt(),
                product.getStock(),
                product.getMaxStock(),
                new ProductDetails(null, product.getExpireDate(), product.getDaysBeforeExpire()));
    }
}

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
    private ProductDetails details;

    public static class ProductDetails {
        // Electronic details
        private String type;

        // Cosmetic & Fnb details
        private Date expireDate;

        public ProductDetails(String type, Date expireDate) {
            this.type = type;
            this.expireDate = expireDate;
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
    }

    public GetProductResponseDto() {
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
        return id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public int getStock() {
        return stock;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public ProductDetails getDetails() {
        return details;
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
                new ProductDetails(product.getType(), null));
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
                new ProductDetails(null, product.getExpireDate()));
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
                new ProductDetails(null, product.getExpireDate()));
    }
}

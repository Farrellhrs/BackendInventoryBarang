package com.pbo.warehouse.api.dto.request;

public class AddProductRequestDto {
    private String id;
    private String skuCode;
    private String name;
    private String category;
    private int maxStock;
    private String createdBy;
    private ProductDetails details;

    public static class ProductDetails {
        // Electronic details
        private String type;

        // Cosmetic & Fnb details
        private String expireDate;
        private int daysBeforeExpire;

        public ProductDetails() {
        }

        public ProductDetails(String type, String expireDate) {
            this.type = type;
            this.expireDate = expireDate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public int getDaysBeforeExpire() {
            return daysBeforeExpire;
        }

        public void setDaysBeforeExpire(int daysBeforeExpire) {
            this.daysBeforeExpire = daysBeforeExpire;
        }
    }

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

    public ProductDetails getDetails() {
        return details;
    }

    public void setDetails(ProductDetails details) {
        this.details = details;
    }
}

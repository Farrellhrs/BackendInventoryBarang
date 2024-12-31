package com.pbo.warehouse.api.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Product extends Model {
    protected String subTableName;
    private String id;
    private String skuCode; // stock keeping unit
    private String name;
    private String category;
    private int maxStock;
    private int stock;
    private String createdBy;
    private User creator;

    public Product(String subTableName) {
        super("products");
        this.subTableName = subTableName;
    }

    public String getSubTableName() {
        return this.subTableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generateId() {
        this.id = java.util.UUID.randomUUID().toString();
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public static List<String> toColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("sku_code");
        columns.add("name");
        columns.add("category");
        columns.add("max_stock");
        columns.add("stock");
        columns.add("created_by");
        columns.add("created_at");
        columns.add("updated_at");

        return columns;
    }
}

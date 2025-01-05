package com.pbo.warehouse.api.models;

import java.sql.Date;

public class StockRecord extends Model {
    private int id;
    private String productId;
    private Product product;
    private int stock;
    private Date recordDate;

    public StockRecord() {
        super("stock_records");
    }

    public StockRecord(int id, String productId, int stock, Date recordDate) {
        super("stock_records");
        this.id = id;
        this.productId = productId;
        this.stock = stock;
        this.recordDate = recordDate;
    }

    public StockRecord(String productId, int stock, Date recordDate) {
        super("stock_records");
        this.productId = productId;
        this.stock = stock;
        this.recordDate = recordDate;
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

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getRecordDate() {
        return this.recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

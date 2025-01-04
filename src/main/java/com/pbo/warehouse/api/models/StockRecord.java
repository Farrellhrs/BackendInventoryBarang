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
        //TODO Auto-generated constructor stub
        super("stock_records");
        this.id = id;
        this.productId = productId;
        this.stock = stock;
        this.recordDate = recordDate;
    }

    public StockRecord(String productId, int stock, Date recordDate) {
        //TODO Auto-generated constructor stub
        super("stock_records");
        this.productId = productId;
        this.stock = stock;
        this.recordDate = recordDate;
    }

    public int getId(){
        return this.id;
    }

    public String getProductId() {
        return this.productId;
    }

    public int getStock() {
        return this.stock;
    }

    public java.util.Date getRecordDate() {
        return this.recordDate;
    }
}

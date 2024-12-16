package com.pbo.warehouse.api.models;

import java.util.Date;

public class StockRecord extends Model {
    private int id;
    private String productId;
    private Product product;
    private int stock;
    private Date recordDate;

    public StockRecord() {
        super("stock_records");
    }
}

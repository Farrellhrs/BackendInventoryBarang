package com.pbo.warehouse.api.models;

import java.sql.Date;

public class StockRecord extends Model {
    private int id;
    private String productId;
    private Product product;
    private int inOutId;
    private InOutRecord inOutRecord;
    private int stock;
    private Date recordDate;

    public StockRecord() {
        super("stock_records");
    }
}

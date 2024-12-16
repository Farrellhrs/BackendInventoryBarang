package com.pbo.warehouse.api.models;

import java.util.Date;

public class InOutRecord extends Model {
    private int id;
    private String productId;
    private Product product;
    private String type; // in or out
    private int quantity;
    private Date recordDate;
    private String createdBy;
    private User creator;

    public InOutRecord() {
        super("in_out_records");
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

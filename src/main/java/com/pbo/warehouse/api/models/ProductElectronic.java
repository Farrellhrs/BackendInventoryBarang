package com.pbo.warehouse.api.models;

public class ProductElectronic extends Product {
    private String type;

    public ProductElectronic() {
        super("product_electronics");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

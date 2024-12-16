package com.pbo.warehouse.api.models;

import java.util.Date;

public class ProductFnb extends Product {
    private Date expireDate;

    public ProductFnb() {
        super("product_fnbs");
    }
}

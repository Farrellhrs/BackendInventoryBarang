package com.pbo.warehouse.api.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCosmetic extends Product {
    private Date expireDate;

    public ProductCosmetic() {
        super("product_cosmetics");
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public static List<String> toColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("expire_date");

        return columns;
    }
}

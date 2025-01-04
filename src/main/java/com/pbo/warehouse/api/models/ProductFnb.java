package com.pbo.warehouse.api.models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.pbo.warehouse.api.models.interfaces.ProductExpireable;

public class ProductFnb extends Product implements ProductExpireable {
    private Date expireDate;

    public ProductFnb() {
        super("product_fnbs");
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

    @Override
    public int getDaysBeforeExpire() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDaysBeforeExpire'");
    }
}

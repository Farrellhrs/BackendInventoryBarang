package com.pbo.warehouse.api.models;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.models.interfaces.ProductExpireable;

public class ProductCosmetic extends Product implements ProductExpireable {
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

    @Override
    public int getDaysBeforeExpire() {
        if (expireDate == null) {
            throw new IllegalArgumentException("Tanggal kedaluwarsa belum diatur");
        }

        LocalDate expireLocalDate = expireDate.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long daysBeforeExpire = ChronoUnit.DAYS.between(currentDate, expireLocalDate);

        return (int) daysBeforeExpire;
    }
    
}

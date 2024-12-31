package com.pbo.warehouse.api.models;

import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.utils.FormatUtil;

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

    public void setTypeCapitalized(String type) {
        this.type = FormatUtil.capitalizeFirstLetter(type);
    }

    public static List<String> toColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("type");

        return columns;
    }
}

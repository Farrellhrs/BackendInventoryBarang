package com.pbo.warehouse.api.models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class InOutRecord extends Model {
    private int id;
    private String productId;
    private ProductCosmetic productCosmetic;
    private ProductElectronic productElectronic;
    private ProductFnb productFnb;
    private Product product;
    private String type; // in or out
    private int quantity;
    private Date recordDate;
    private String createdBy;
    private User creator;

    public InOutRecord() {
        super("in_out_records");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductCosmetic getProductCosmetic() {
        return this.productCosmetic;
    }

    public void setProductCosmetic(ProductCosmetic product) {
        this.productCosmetic = product;
    }

    public ProductElectronic getProductElectronic() {
        return this.productElectronic;
    }

    public void setProductElectronic(ProductElectronic product) {
        this.productElectronic = product;
    }

    public ProductFnb getProductFnb() {
        return this.productFnb;
    }

    public void setProductFnb(ProductFnb product) {
        this.productFnb = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product.getId();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
        this.createdBy = creator.getId();
    }

    public static List<String> toColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("product_id");
        columns.add("type");
        columns.add("quantity");
        columns.add("record_date");
        columns.add("created_by");
        columns.add("created_at");
        columns.add("updated_at");

        return columns;
    }
}

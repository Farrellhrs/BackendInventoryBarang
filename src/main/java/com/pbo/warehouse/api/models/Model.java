package com.pbo.warehouse.api.models;

import java.util.Date;

public abstract class Model {
    protected String tableName;
    protected Date createdAt;
    protected Date updatedAt;

    public Model(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }
}

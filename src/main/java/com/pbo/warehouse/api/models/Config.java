package com.pbo.warehouse.api.models;

public class Config {
    private int id;
    private String registerKey;

    public Config() {
    }

    public Config(int id, String registerKey) {
        this.id = id;
        this.registerKey = registerKey;
    }

    public int getId() {
        return id;
    }

    public String getRegisterKey() {
        return registerKey;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegisterKey(String registerKey) {
        this.registerKey = registerKey;
    }
}

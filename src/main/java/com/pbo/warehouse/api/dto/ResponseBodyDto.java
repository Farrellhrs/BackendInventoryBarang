package com.pbo.warehouse.api.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ResponseBodyDto {
    @Expose(serialize = false)
    private int statusCode;
    @Expose
    private boolean success;
    @Expose
    private String message;
    @Expose(serialize = false)
    private String dataStr;
    @Expose
    private Object data;
    @Expose(serialize = false)
    private String paginationStr;
    @Expose
    private Object pagination;
    @Expose
    private Object error;

    public ResponseBodyDto success(int statusCode, String message, String data) {
        this.success = true;
        this.statusCode = statusCode;
        this.message = message;
        this.dataStr = data;

        return this;
    }

    public ResponseBodyDto successWithPagination(int statusCode, String message, String data,
            String pagination) {
        this.success = true;
        this.statusCode = statusCode;
        this.message = message;
        this.dataStr = data;
        this.paginationStr = pagination;

        return this;
    }

    public ResponseBodyDto error(int statusCode, String message, Object error) {
        this.success = false;
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;

        return this;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        this.data = gson.fromJson(this.dataStr, Object.class);
        this.pagination = gson.fromJson(this.paginationStr, Object.class);

        return gson.toJson(this);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Object getError() {
        return error;
    }
}

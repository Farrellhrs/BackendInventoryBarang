package com.pbo.warehouse.api.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ResponseBody {
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
    @Expose
    private String error;

    public ResponseBody success(int statusCode, String message, String data) {
        this.success = true;
        this.statusCode = statusCode;
        this.message = message;
        this.dataStr = data;

        return this;
    }

    public ResponseBody error(int statusCode, String message, String error) {
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

    public String getError() {
        return error;
    }
}

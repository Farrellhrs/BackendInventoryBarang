package com.pbo.warehouse.api.dto.request;

public class PaginationRequest {
    private int page;
    private int limit;

    public PaginationRequest() {
    }

    public PaginationRequest(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return (page - 1) * limit;
    }

    public int getLimit() {
        return limit;
    }
}

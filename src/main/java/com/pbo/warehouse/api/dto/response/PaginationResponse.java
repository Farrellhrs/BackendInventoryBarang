package com.pbo.warehouse.api.dto.response;

public class PaginationResponse {
    private int page;
    private int limit;
    private int totalData;
    private int totalPage;
    private boolean hasNextPage;
    private boolean hasPrevPage;

    public PaginationResponse() {
    }

    public PaginationResponse(int page, int limit, int totalData, int totalPage, boolean hasNextPage,
            boolean hasPrevPage) {
        this.page = page;
        this.limit = limit;
        this.totalData = totalData;
        this.totalPage = totalPage;
        this.hasNextPage = hasNextPage;
        this.hasPrevPage = hasPrevPage;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotalData() {
        return totalData;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public boolean isHasPrevPage() {
        return hasPrevPage;
    }
}

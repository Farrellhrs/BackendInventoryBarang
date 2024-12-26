package com.pbo.warehouse.api.utils;

import com.pbo.warehouse.api.dto.response.PaginationResponse;

public class PaginationUtil {
    public static PaginationResponse getPagination(int page, int limit, int totalData) {
        int totalPage = (int) Math.ceil((double) totalData / limit);

        boolean hasNext = page < totalPage;
        boolean hasPrev = page > 1;

        return new PaginationResponse(page, limit, totalData, totalPage, hasNext, hasPrev);
    }
}

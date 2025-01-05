package com.pbo.warehouse.api.dto.response;

import java.util.List;

public class GetAllProductsResponseDto {
    List<GetProductResponseDto> data;
    PaginationResponse pagination;

    public GetAllProductsResponseDto() {
    }

    public GetAllProductsResponseDto(List<GetProductResponseDto> data, PaginationResponse pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public List<GetProductResponseDto> getData() {
        return data;
    }

    public PaginationResponse getPagination() {
        return pagination;
    }
}

package com.pbo.warehouse.api.dto.response;

import java.util.List;

public class GetAllInOutResponseDto {
    List<GetInOutResponseDto> data;
    PaginationResponse pagination;

    public GetAllInOutResponseDto(List<GetInOutResponseDto> data, PaginationResponse pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public List<GetInOutResponseDto> getData() {
        return data;
    }

    public void setData(List<GetInOutResponseDto> data) {
        this.data = data;
    }

    public PaginationResponse getPagination() {
        return pagination;
    }

    public void setPagination(PaginationResponse pagination) {
        this.pagination = pagination;
    }
}

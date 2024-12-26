package com.pbo.warehouse.api.dto.request;

public class GetProductsRequestDto extends PaginationRequest {
    private String category;
    private String name;

    public GetProductsRequestDto() {
    }

    public GetProductsRequestDto(int page, int limit, String category, String name) {
        super(page, limit);
        this.category = category;
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}

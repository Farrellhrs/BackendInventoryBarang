package com.pbo.warehouse.api.dto.request;

public class GetProductsRequestDto extends PaginationRequest {
    private String category;
    private String name;
    private String sort;
    private String sortByDetail;
    private String order;

    public GetProductsRequestDto() {
    }

    public GetProductsRequestDto(int page, int limit, String category, String name, String sort, String sortByDetail, String order) {
        super(page, limit);

        String defaultSort = "name";
        String defaultOrder = "asc";

        if (sort == null) {
            sort = defaultSort;
        }

        if (order == null) {
            order = defaultOrder;
        }

        this.category = category;
        this.name = name;
        this.sort = sort;
        this.sortByDetail = sortByDetail;
        this.order = order;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSort() {
        return sort;
    }

    public String getSortByDetail() {
        return sortByDetail;
    }

    public String getOrder() {
        return order;
    }
}

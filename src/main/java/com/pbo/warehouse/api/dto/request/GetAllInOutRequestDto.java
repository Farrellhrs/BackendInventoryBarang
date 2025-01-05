package com.pbo.warehouse.api.dto.request;

import java.util.Date;

public class GetAllInOutRequestDto extends PaginationRequest {
    private String category;
    private Date startDate;
    private Date endDate;
    private String sort;
    private String order;
    private String type;

    public GetAllInOutRequestDto() {
    }

    public GetAllInOutRequestDto(int page, int limit, String category, Date startDate, Date endDate, String sort, String order, String type) {
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
        this.startDate = startDate;
        this.endDate = endDate;
        this.sort = sort;
        this.order = order;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }
}

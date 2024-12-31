package com.pbo.warehouse.api.dto.request;

public class GetAllInOutRequestDto extends PaginationRequest {
    private String category;
    private String startDate;
    private String endDate;

    public GetAllInOutRequestDto() {
    }

    public GetAllInOutRequestDto(int page, int limit, String category, String startDate, String endDate) {
        super(page, limit);

        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

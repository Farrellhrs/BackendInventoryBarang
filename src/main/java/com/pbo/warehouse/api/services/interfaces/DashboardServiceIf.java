package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto;
import com.pbo.warehouse.api.dto.response.GetDashboardSummaryResponseDto;

public interface DashboardServiceIf {
    GetDashboardChartResponseDto getChart(int year, int month);

    GetDashboardSummaryResponseDto getSummary();
}

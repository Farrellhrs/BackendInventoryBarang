package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto;

public interface DashboardServiceIf {
    GetDashboardChartResponseDto getChart(int year, int month);
}

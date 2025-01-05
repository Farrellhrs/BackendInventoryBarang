package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.DashboardControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto;
import com.pbo.warehouse.api.dto.response.GetDashboardSummaryResponseDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.services.DashboardService;

import spark.Request;
import spark.Response;

public class DashboardController implements DashboardControllerIf {
    private final DashboardService service = new DashboardService();

    @Override
    public ResponseBodyDto getChart(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();

        try {
            // Load params
            String yearStr = req.queryParams("year");
            String monthStr = req.queryParams("month");

            // Validate params
            if (yearStr != null) {
                if (!yearStr.matches("^\\d{4}$")) {
                    throw new AppException(400, "Parameter 'year' harus berupa angka 4 digit");
                }
            }

            if (monthStr != null) {
                if (!monthStr.matches("^\\d{1,2}$")) {
                    throw new AppException(400, "Parameter 'month' harus berupa angka 1-2 digit");
                }
            }

            int year = 0;
            int month = 0;

            if (yearStr != null) {
                year = Integer.parseInt(yearStr);
            }

            if (monthStr != null) {
                month = Integer.parseInt(monthStr);
            }

            GetDashboardChartResponseDto response = service.getChart(year, month);

            return responseBody.success(200, "Berhasil mendapatkan data chart", gson.toJson(response));
        } catch (AppException e) {
            return responseBody.error(400, e.getMessage(), "");
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), "");
        }
    }

    @Override
    public ResponseBodyDto getSummary(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();

        try {
            GetDashboardSummaryResponseDto response = service.getSummary();

            return responseBody.success(200, "Berhasil mendapatkan data summary", gson.toJson(response));
        } catch (AppException e) {
            return responseBody.error(400, e.getMessage(), "");
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), "");
        }
    }

}

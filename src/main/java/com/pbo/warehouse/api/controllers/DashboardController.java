package com.pbo.warehouse.api.controllers;

import java.time.Year;
import java.time.YearMonth;

import com.pbo.warehouse.api.controllers.interfaces.DashboardControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto.ChartData;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto.ChartDetail;
import com.pbo.warehouse.api.exceptions.AppException;

import spark.Request;
import spark.Response;

public class DashboardController implements DashboardControllerIf {

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

            // Get current year and month
            YearMonth currentYearMonth = YearMonth.now();

            int year = currentYearMonth.getYear();
            int month = currentYearMonth.getMonthValue();

            if (yearStr != null) {
                year = Integer.parseInt(yearStr);
            }

            if (monthStr != null) {
                month = Integer.parseInt(monthStr);
            }
            int daysInMonth = currentYearMonth.lengthOfMonth();

            // Prepare response
            int[] stock = { 1, 2, 4 };
            int[] inbound = { 1, 2, 4 };
            int[] outbound = { 1, 2, 2 };
            ChartData chartData = new ChartData(stock, inbound, outbound);

            ChartDetail chartDetail = new ChartDetail(chartData, chartData, chartData);

            int[] totalStock = new int[stock.length];
            for (int i = 0; i < stock.length; i++) {
                totalStock[i] += (stock[i] * 3);
            }

            GetDashboardChartResponseDto response = new GetDashboardChartResponseDto(year, month,
                    daysInMonth, totalStock, chartDetail);

            return responseBody.success(200, "Berhasil mendapatkan data chart", gson.toJson(response));
        } catch (AppException e) {
            return responseBody.error(400, "", "");
        } catch (Exception e) {
            return responseBody.error(500, "", "");
        }
    }

    @Override
    public ResponseBodyDto getSummary(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSummary'");
    }

}

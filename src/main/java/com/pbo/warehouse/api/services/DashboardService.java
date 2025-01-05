package com.pbo.warehouse.api.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto.ChartData;
import com.pbo.warehouse.api.dto.response.GetDashboardChartResponseDto.ChartDetail;
import com.pbo.warehouse.api.dto.response.GetDashboardSummaryResponseDto.CategorySummary;
import com.pbo.warehouse.api.dto.response.GetDashboardSummaryResponseDto.ProductByCategory;
import com.pbo.warehouse.api.dto.response.GetDashboardSummaryResponseDto;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;
import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.repositories.InOutRecordRepository;
import com.pbo.warehouse.api.repositories.ProductRepository;
import com.pbo.warehouse.api.repositories.StockRecordRepository;
import com.pbo.warehouse.api.services.interfaces.DashboardServiceIf;

public class DashboardService implements DashboardServiceIf {
    private final StockRecordRepository stockRecordRepository = new StockRecordRepository();
    private final InOutRecordRepository inOutRecordRepository = new InOutRecordRepository();
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    public GetDashboardChartResponseDto getChart(int year, int month) {
        // Get current year and month
        YearMonth currentYearMonth = YearMonth.now();
        int daysInMonth;

        // Set default year and month if not provided
        if (year == 0) {
            year = currentYearMonth.getYear();
        }
        if (month == 0) {
            month = currentYearMonth.getMonthValue();
        }

        // Determine the number of days in the specified month
        if (year == currentYearMonth.getYear() && month == currentYearMonth.getMonthValue()) {
            daysInMonth = LocalDate.now().getDayOfMonth();
        } else {
            daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        }

        if (year == currentYearMonth.getYear() && month > currentYearMonth.getMonthValue()) {
            return new GetDashboardChartResponseDto(year, month, daysInMonth, new int[daysInMonth],
                    new ChartDetail(new ChartData(new int[daysInMonth], new int[daysInMonth], new int[daysInMonth]),
                            new ChartData(new int[daysInMonth], new int[daysInMonth], new int[daysInMonth]),
                            new ChartData(new int[daysInMonth], new int[daysInMonth], new int[daysInMonth])));
        }

        int[] stockElectronic = new int[daysInMonth];
        int[] stockCosmetic = new int[daysInMonth];
        int[] stockFnb = new int[daysInMonth];

        int[] inboundElectronic = new int[daysInMonth];
        int[] inboundCosmetic = new int[daysInMonth];
        int[] inboundFnb = new int[daysInMonth];

        int[] outboundElectronic = new int[daysInMonth];
        int[] outboundCosmetic = new int[daysInMonth];
        int[] outboundFnb = new int[daysInMonth];

        // Get stock records
        List<StockRecord> stockRecords = stockRecordRepository.getRecordByPeriod(year, month);

        for (int i = 0; i < daysInMonth; i++) {
            stockElectronic[i] = -1;
            stockCosmetic[i] = -1;
            stockFnb[i] = -1;

            inboundElectronic[i] = 0;
            inboundCosmetic[i] = 0;
            inboundFnb[i] = 0;

            outboundElectronic[i] = 0;
            outboundCosmetic[i] = 0;
            outboundFnb[i] = 0;
        }

        for (StockRecord record : stockRecords) {
            int day = record.getRecordDate().toLocalDate().getDayOfMonth();

            if ("electronic".equals(record.getProduct().getCategory())) {
                stockElectronic[day - 1] = record.getStock();
            } else if ("cosmetic".equals(record.getProduct().getCategory())) {
                stockCosmetic[day - 1] = record.getStock();
            } else if ("fnb".equals(record.getProduct().getCategory())) {
                stockFnb[day - 1] = record.getStock();
                System.out.println(record.getProduct().getCategory());
            }
        }

        // Fill gap between dates
        fillGap(stockElectronic, year, month, daysInMonth, "electronic");
        fillGap(stockCosmetic, year, month, daysInMonth, "cosmetic");
        fillGap(stockFnb, year, month, daysInMonth, "fnb");

        int[] totalStocks = new int[daysInMonth];
        for (int i = 0; i < daysInMonth; i++) {
            totalStocks[i] = stockElectronic[i] + stockCosmetic[i] + stockFnb[i];
        }

        List<InOutRecord> inOutRecords = inOutRecordRepository.getRecordsByPeriod(year, month);

        // Handle inbound and outbound stock
        List<InOutRecord> inRecords = new ArrayList<>();
        List<InOutRecord> outRecords = new ArrayList<>();

        for (InOutRecord record : inOutRecords) {
            if ("in".equals(record.getType())) {
                inRecords.add(record);
            } else {
                outRecords.add(record);
            }
        }

        for (InOutRecord record : inRecords) {
            int day = record.getRecordDate().toLocalDate().getDayOfMonth();

            if ("electronic".equals(record.getProduct().getCategory())) {
                inboundElectronic[day - 1] = record.getQuantity();
            } else if ("cosmetic".equals(record.getProduct().getCategory())) {
                inboundCosmetic[day - 1] = record.getQuantity();
            } else if ("fnb".equals(record.getProduct().getCategory())) {
                inboundFnb[day - 1] = record.getQuantity();
            }
        }

        for (InOutRecord record : outRecords) {
            int day = record.getRecordDate().toLocalDate().getDayOfMonth();

            if ("electronic".equals(record.getProduct().getCategory())) {
                outboundElectronic[day - 1] = record.getQuantity();
            } else if ("cosmetic".equals(record.getProduct().getCategory())) {
                outboundCosmetic[day - 1] = record.getQuantity();
            } else if ("fnb".equals(record.getProduct().getCategory())) {
                outboundFnb[day - 1] = record.getQuantity();
            }
        }

        ChartData electronic = new ChartData(stockElectronic, inboundElectronic, outboundElectronic);
        ChartData cosmetic = new ChartData(stockCosmetic, inboundCosmetic, outboundCosmetic);
        ChartData fnb = new ChartData(stockFnb, inboundFnb, outboundFnb);

        ChartDetail chartDetail = new ChartDetail(electronic, cosmetic, fnb);

        GetDashboardChartResponseDto response = new GetDashboardChartResponseDto(year, month, daysInMonth, totalStocks,
                chartDetail);

        return response;
    }

    private void fillGap(int[] stock, int year, int month, int daysInMonth, String productCategory) {
        // Retrieve the last stock record
        StockRecord lastStock = stockRecordRepository.getRecordBeforePeriod(year, month, productCategory);

        // Handle the case where lastStock is null
        int defaultStockValue = (lastStock == null) ? 0 : lastStock.getStock();

        int i = 0;
        int startIndex = 0;
        int endIndex = 0;

        while (i < daysInMonth) {
            if (stock[i] == -1) {
                startIndex = i;
                while (i < daysInMonth && stock[i] == -1) {
                    i++;
                }
                endIndex = i;

                if (startIndex == 0) {
                    for (int j = startIndex; j < endIndex; j++) {
                        stock[j] = defaultStockValue; // Use default value if lastStock is null
                    }
                } else {
                    for (int j = startIndex; j < endIndex; j++) {
                        stock[j] = stock[startIndex - 1];
                    }
                }
            }
            i++;
        }
    }

    @Override
    public GetDashboardSummaryResponseDto getSummary() {
        StockRecord lastElectronic = stockRecordRepository.getLastRecordByCategory("electronic");
        StockRecord lastCosmetic = stockRecordRepository.getLastRecordByCategory("cosmetic");
        StockRecord lastFnb = stockRecordRepository.getLastRecordByCategory("fnb");

        int stockElectronic = (lastElectronic == null) ? 0 : lastElectronic.getStock();
        int stockCosmetic = (lastCosmetic == null) ? 0 : lastCosmetic.getStock();
        int stockFnb = (lastFnb == null) ? 0 : lastFnb.getStock();

        int countElectronic = productRepository.getTotalData("electronic");
        int countCosmetic = productRepository.getTotalData("cosmetic");
        int countFnb = productRepository.getTotalData("fnb");

        GetProductsRequestDto paramsElectronic = new GetProductsRequestDto(1, 5, "electronic", null, "stock", null,
                "desc");
        GetProductsRequestDto paramsCosmetic = new GetProductsRequestDto(1, 5, "cosmetic", null, "stock", null, "desc");
        GetProductsRequestDto paramsFnb = new GetProductsRequestDto(1, 5, "fnb", null, "stock", null, "desc");

        List<ProductElectronic> topProductElectronics = productRepository.getAllProductElectronics(paramsElectronic);
        List<ProductCosmetic> topProductCosmetics = productRepository.getAllProductCosmetics(paramsCosmetic);
        List<ProductFnb> topProductFnbs = productRepository.getAllProductFnbs(paramsFnb);

        CategorySummary stockPerCategory = new CategorySummary(stockElectronic, stockCosmetic, stockFnb);
        CategorySummary countPerCategory = new CategorySummary(countElectronic, countCosmetic, countFnb);

        ProductByCategory topProducts = new ProductByCategory(topProductElectronics, topProductCosmetics,
                topProductFnbs);

        GetDashboardSummaryResponseDto response = new GetDashboardSummaryResponseDto(stockPerCategory, countPerCategory,
                topProducts);

        return response;
    }

}

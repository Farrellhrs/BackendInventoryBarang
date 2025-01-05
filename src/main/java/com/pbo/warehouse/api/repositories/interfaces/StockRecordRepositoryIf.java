package com.pbo.warehouse.api.repositories.interfaces;

import java.sql.Date;
import java.util.List;

import com.pbo.warehouse.api.models.StockRecord;

public interface StockRecordRepositoryIf {
    List<StockRecord> getRecordByPeriod(int year, int month);

    StockRecord getRecordBeforePeriod(int year, int month, String productCategory);

    StockRecord getLastRecordByCategory(String productCategory);

    void updateCumulativeStocks(String productId, Date recordDate, int delta);

    StockRecord getLastRecordBeforeDate(String productId, Date recordDate);
}

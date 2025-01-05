package com.pbo.warehouse.api.repositories.interfaces;

import java.sql.Date;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.StockRecord;

public interface InOutRecordRepositoryIf {
    int getTotalData(GetAllInOutRequestDto params);

    List<InOutRecord> getAllRecords(GetAllInOutRequestDto params);

    List<InOutRecord> getRecordsByPeriod(int year, int month);

    InOutRecord getRecordById(int id);

    void insertRecord(InOutRecord record);

    void updateRecord(InOutRecord record);

    boolean deleteRecord(int id);

    // table stock record
    StockRecord getStockRecordByDateAndProductId(Date recordDate, String productId);

    void insertStockRecord(StockRecord stockRecord);

    void updateStockRecord(int stock, Date recordDate, String productId);
}
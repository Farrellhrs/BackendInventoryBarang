package com.pbo.warehouse.api.repositories.interfaces;

import java.sql.Date;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.StockRecord;

public interface InOutRecordRepositoryIf {
    int getTotalData(String productCategory);

    List<InOutRecord> getAllRecords(GetAllInOutRequestDto params);

    GetInOutResponseDto getRecordById(int id);

    InOutRecord getRecordByDateAndProductId(Date date, String productId);

    void insertRecord(InOutRecord record);

    void updateRecord(InOutRecord record);

    boolean deleteRecord(int id);

    // table stock record
    StockRecord getStockRecordByDateAndProductId(Date recordDate, String productId);
    void insertStockRecord(StockRecord stockRecord);
    void updateStockRecord(int stock, Date recordDate, String productId);
    void updateCumulativeStocks(String productId, Date recordDate, int delta);
}
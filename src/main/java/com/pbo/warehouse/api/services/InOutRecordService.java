package com.pbo.warehouse.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.InOutRecordRepository;
import com.pbo.warehouse.api.repositories.ProductRepository;
import com.pbo.warehouse.api.repositories.UserRepository;
import com.pbo.warehouse.api.services.interfaces.InOutRecordServiceIf;

public class InOutRecordService implements InOutRecordServiceIf {
    private final InOutRecordRepository InOutRecordRepository = new InOutRecordRepository();
    @Override
    public List<GetInOutResponseDto> getAllRecords(GetAllInOutRequestDto params) {
        /*
         * TODO: implement this logics
         * - get all Records (call InOutRecordRepository.getAllRecords)
         * - get total data (call InOutRecordRepository.getTotalData)
         * - create pagination response (call PaginationUtil.createPaginationResponse)
         * - return GetAllInOutResponseDto
         * - throw AppException if there is an exception
         */
        
        throw new UnsupportedOperationException("Unimplemented method 'getAllRecords'");
    }

    @Override
    public GetInOutResponseDto getRecordById(String id) {
        /*
         * TODO: implement this logics
         * - get Record by id (call InOutRecordRepository.getRecordById)
         * - return GetInOutResponseDto
         * - throw AppException if there is an exception
         */
        throw new UnsupportedOperationException("Unimplemented method 'getRecordById'");
    }

    @Override
    public void addRecord(AddInOutRequestDto Record) {
        /*
         * TODO: implement this logics
         * - get existing record by date and product id (call InOutRecordRepository.getRecordByDateAndProductId)
         * - if record exists, update the record (call InOutRecordRepository.updateRecord)
         * - else, insert new record (call InOutRecordRepository.insertRecord)
         * - throw AppException if there is an exception
         */
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        UserRepository userRepository = new UserRepository();
        User currentUser = userRepository.getUserByEmail(Record.getCreatedBy());
        if (currentUser == null) {
            throw new AppException(400, "User tidak ditemukan");
        }
        try {
            // Check if an existing record exists by date and product ID
            try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date utilDate = sdf.parse(Record.getRecordDate());
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
                    // Insert a new record
                    InOutRecord newRecord = new InOutRecord();
                    newRecord.setProductId(Record.getProductId());
                    newRecord.setQuantity(Record.getQuantity());
                    newRecord.setRecordDate(sqlDate);
                    newRecord.setType(Record.getType());
                    newRecord.setCreatedBy(currentUser.getId());
                    InOutRecordRepository.insertRecord(newRecord);
                    addRecordToStockRecords(Record);
                } catch (ParseException e) {
                    responseBody.error(400, "Format tanggal Recorddate tidak sesuai yyyy-MM-dd", e.getMessage());
                }
        } catch (Exception e) {
            // Throw an application exception for any issues
            throw new AppException(500, "An error occurred while processing the record: " + e.getMessage());
        }
    }

    @Override
    public void updateRecord(UpdateInOutRequestDto record) {
        try {
            // Check if the record exists
            InOutRecord existingRecord = InOutRecordRepository.getRecordById(record.getCurrentProductId());
            if (existingRecord == null) {
                throw new AppException(404, "Record not found");
            }

            // Update record details
            existingRecord.setProductId(record.getNewProductId());
            existingRecord.setQuantity(record.getQuantity());
            existingRecord.setRecordDate(new SimpleDateFormat("yyyy-MM-dd").parse(record.getRecordDate()));

            // Call repository to update record in database
            InOutRecordRepository.updateRecord(existingRecord);

        } catch (ParseException e) {
            throw new AppException(400, "Invalid date format. Expected yyyy-MM-dd");
        } catch (Exception e) {
            throw new AppException(500, "An error occurred while updating the record: " + e.getMessage());
        }
    }

    @Override
    public void deleteRecord(String id) {
        /*
         * TODO: implement this logics
         * - call InOutRecordRepository.deleteRecord
         * - throw AppException if there is an exception
         */
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecord'");
    }

    // ----- function stock_record bantuan -------
    public void addRecordToStockRecords(AddInOutRequestDto record) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = sdf.parse(record.getRecordDate());
        java.sql.Date recordDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
        String productId = record.getProductId();
        int quantity = record.getQuantity();
        String type = record.getType();

        // Hitung delta stok berdasarkan tipe transaksi
        int delta = type.equals("in") ? quantity : -quantity;

        // Periksa apakah ada record di stock_records untuk tanggal tersebut
        StockRecord existingRecord = InOutRecordRepository.getStockRecordByDateAndProductId(recordDate, productId);

        if (existingRecord != null) {
            // Update record jika sudah ada
            int updatedStock = existingRecord.getStock() + delta;
            InOutRecordRepository.updateStockRecord(updatedStock, recordDate, productId);
        } else {
            // Insert record baru jika belum ada
            StockRecord newRecord = new StockRecord(
                productId,
                delta,
                recordDate
            );
            InOutRecordRepository.insertStockRecord(newRecord);
        }

        // Update kumulatif stok untuk semua tanggal setelah tanggal ini
        InOutRecordRepository.updateCumulativeStocks(productId, recordDate, delta);
    }

}

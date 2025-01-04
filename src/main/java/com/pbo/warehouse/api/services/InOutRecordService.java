package com.pbo.warehouse.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.protobuf.TextFormat.ParseException;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetAllInOutResponseDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.dto.response.PaginationResponse;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.InOutRecordRepository;
import com.pbo.warehouse.api.repositories.UserRepository;
import com.pbo.warehouse.api.services.interfaces.InOutRecordServiceIf;
import com.pbo.warehouse.api.utils.PaginationUtil;

public class InOutRecordService implements InOutRecordServiceIf {
    private final InOutRecordRepository inOutRecordRepository = new InOutRecordRepository();

    @Override
    public GetAllInOutResponseDto getAllRecords(GetAllInOutRequestDto params) {
        /*
         * TODO: implement this logics
         * - get all Records (call InOutRecordRepository.getAllRecords)
         * - get total data (call InOutRecordRepository.getTotalData)
         * - create pagination response (call PaginationUtil.createPaginationResponse)
         * - return GetAllInOutResponseDto
         * - throw AppException if there is an exception
         */
        List<InOutRecord> records = new ArrayList<>();
        List<GetInOutResponseDto> inouts = new ArrayList<>();
        int totalData = 0;
        records = inOutRecordRepository.getAllRecords(params);

        for (InOutRecord inout : records) {
            inouts.add(GetInOutResponseDto.fromEntityElectronic(inout));
        }

        for (InOutRecord inout : records) {
            inouts.add(GetInOutResponseDto.fromEntityCosmetic(inout));
        }

        for (InOutRecord inout : records) {
            inouts.add(GetInOutResponseDto.fromEntityFnb(inout));
        }

        totalData = inOutRecordRepository.getTotalData(params.getCategory());
        PaginationResponse pagination = PaginationUtil.getPagination(params.getPage(), params.getLimit(), totalData);

        GetAllInOutResponseDto response = new GetAllInOutResponseDto(
                inouts, pagination);

        return response;
    }

    @Override
    public GetInOutResponseDto getRecordById(int id) {
        /*
         * TODO: implement this logics
         * - get Record by id (call InOutRecordRepository.getRecordById)
         * - return GetInOutResponseDto
         * - throw AppException if there is an exception
         */
        GetInOutResponseDto response = new GetInOutResponseDto();

        InOutRecord inout = inOutRecordRepository.getRecordById(id);
        if (inout == null) {
            throw new AppException(404, "Record not found");
        }

        if (inout.getProductElectronic() != null) {
            response = GetInOutResponseDto.fromEntityElectronic(inout);
        } else if (inout.getProductCosmetic() != null) {
            response = GetInOutResponseDto.fromEntityCosmetic(inout);
        } else if (inout.getProductFnb() != null) {
            response = GetInOutResponseDto.fromEntityFnb(inout);
        }

        return response;
    }

    @Override
    public void addRecord(AddInOutRequestDto Record) {
        /*
         * TODO: implement this logics
         * - get existing record by date and product id (call
         * InOutRecordRepository.getRecordByDateAndProductId)
         * - if record exists, update the record (call
         * InOutRecordRepository.updateRecord)
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
                inOutRecordRepository.insertRecord(newRecord);
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
            String productIdString = record.getCurrentProductId();
            int currentProductId = Integer.parseInt(productIdString);
            InOutRecord existingRecord = inOutRecordRepository.getRecordById(currentProductId);
            if (existingRecord == null) {
                throw new AppException(404, "Record not found");
            }

            // Update record details
            existingRecord.setProductId(record.getNewProductId());
            existingRecord.setQuantity(record.getQuantity());
            existingRecord.setRecordDate(new SimpleDateFormat("yyyy-MM-dd").parse(record.getRecordDate()));

            // Call repository to update record in database
            inOutRecordRepository.updateRecord(existingRecord);

        } catch (Exception e) {
            throw new AppException(500, "An error occurred while updating the record: " + e.getMessage());
        }
    }

    @Override
    public void deleteRecord(String id) {
        try {
            // Check if the record exists
            InOutRecord record = inOutRecordRepository.getRecordById(Integer.parseInt(id));
            if (record == null) {
                throw new AppException(404, "Record not found");
            }

            // Call repository to delete the record
            boolean isDeleted = inOutRecordRepository.deleteRecord(Integer.parseInt(id));
            if (!isDeleted) {
                throw new AppException(500, "Failed to delete record");
            }
        } catch (Exception e) {
            throw new AppException(500, "An error occurred while deleting the record: " + e.getMessage());
        }
    }

    // ----- function stock_record bantuan -------
    public void addRecordToStockRecords(AddInOutRequestDto record) throws ParseException, java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = sdf.parse(record.getRecordDate());
        java.sql.Date recordDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
        String productId = record.getProductId();
        int quantity = record.getQuantity();
        String type = record.getType();

        // Hitung delta stok berdasarkan tipe transaksi
        int delta = type.equals("in") ? quantity : -quantity;

        // Periksa apakah ada record di stock_records untuk tanggal tersebut
        StockRecord existingRecord = inOutRecordRepository.getStockRecordByDateAndProductId(recordDate, productId);

        if (existingRecord != null) {
            // Update record jika sudah ada
            int updatedStock = existingRecord.getStock() + delta;
            inOutRecordRepository.updateStockRecord(updatedStock, recordDate, productId);
        } else {
            // Insert record baru jika belum ada
            StockRecord newRecord = new StockRecord(
                    productId,
                    delta,
                    recordDate);
            inOutRecordRepository.insertStockRecord(newRecord);
        }

        // Update kumulatif stok untuk semua tanggal setelah tanggal ini
        inOutRecordRepository.updateCumulativeStocks(productId, recordDate, delta);
    }

}

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
import com.pbo.warehouse.api.dto.response.CreatorResponseDto;
import com.pbo.warehouse.api.dto.response.GetAllInOutResponseDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.dto.response.PaginationResponse;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.InOutRecordRepository;
import com.pbo.warehouse.api.repositories.StockRecordRepository;
import com.pbo.warehouse.api.repositories.UserRepository;
import com.pbo.warehouse.api.services.interfaces.InOutRecordServiceIf;
import com.pbo.warehouse.api.utils.PaginationUtil;

public class InOutRecordService implements InOutRecordServiceIf {
    private final InOutRecordRepository inOutRecordRepository = new InOutRecordRepository();
    private final StockRecordRepository stockRecordRepository = new StockRecordRepository();

    @Override
    public GetAllInOutResponseDto getAllRecords(GetAllInOutRequestDto params) {
        int totalData = 0;
        List<GetInOutResponseDto> inouts = new ArrayList<>();

        List<InOutRecord> records = inOutRecordRepository.getAllRecords(params);

        totalData = inOutRecordRepository.getTotalData(params);
        PaginationResponse pagination = PaginationUtil.getPagination(params.getPage(), params.getLimit(), totalData);

        for (InOutRecord record : records) {
            GetInOutResponseDto response = new GetInOutResponseDto();
            if (record.getProductElectronic() != null) {
                response = GetInOutResponseDto.fromEntityElectronic(record);
            } else if (record.getProductCosmetic() != null) {
                response = GetInOutResponseDto.fromEntityCosmetic(record);
            } else if (record.getProductFnb() != null) {
                response = GetInOutResponseDto.fromEntityFnb(record);
            } else {
                throw new AppException(404, "Error getting record");
            }

            response.setId(record.getId());
            response.setQuantity(record.getQuantity());
            response.setRecordDate(record.getRecordDate());

            inouts.add(response);
        }

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
        } else {
            throw new AppException(404, "Error getting record");
        }

        response.setCreatedBy(new CreatorResponseDto(inout.getCreator().getName(), inout.getCreator().getEmail()));

        return response;
    }

    @Override
    public void addRecord(AddInOutRequestDto Record) {
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
                Date utilDate = sdf
                        .parse(Record.getRecordDate() + new SimpleDateFormat(" HH:mm:ss").format(new Date()));
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
            InOutRecord existingRecord = inOutRecordRepository.getRecordById(record.getId());
            if (existingRecord == null) {
                throw new AppException(404, "Record not found");
            }

            String newProductId = "";
            if (record.getNewProductId().equals(record.getCurrentProductId())) {
                newProductId = record.getCurrentProductId();
            } else {
                newProductId = record.getNewProductId();
            }

            // Update record details
            existingRecord.setId(record.getId());
            existingRecord.setProductId(newProductId);
            existingRecord.setQuantity(record.getQuantity());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date utilDate = sdf.parse(record.getRecordDate());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date

            existingRecord.setRecordDate(sqlDate);

            int delta = 0;
            if (existingRecord.getType().equals("in")) {
                delta = record.getQuantity() - existingRecord.getQuantity();
            } else {
                delta = existingRecord.getQuantity() - record.getQuantity();
            }
            // Call repository to update record in database
            inOutRecordRepository.updateRecord(existingRecord);
            stockRecordRepository.updateCumulativeStocks(newProductId, sqlDate, delta);

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

            int delta = record.getType().equals("in") ? -record.getQuantity() : record.getQuantity();
            stockRecordRepository.updateCumulativeStocks(id, record.getRecordDate(), delta);

        } catch (Exception e) {
            throw new AppException(500, "An error occurred while deleting the record: " + e.getMessage());
        }
    }

    // ----- function stock_record bantuan -------
    private void addRecordToStockRecords(AddInOutRequestDto record) throws ParseException, java.text.ParseException {
        // Parsing tanggal dari data request
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = sdf.parse(record.getRecordDate());
        java.sql.Date recordDate = new java.sql.Date(utilDate.getTime());

        // Mendapatkan detail produk dari request
        String productId = record.getProductId();
        int quantity = record.getQuantity();
        String type = record.getType();

        // Menghitung perubahan stok (delta) berdasarkan jenis transaksi (in/out)
        int delta = type.equalsIgnoreCase("in") ? quantity : -quantity;

        // Memeriksa apakah sudah ada stok record untuk tanggal dan produk ini
        StockRecord existingRecord = inOutRecordRepository.getStockRecordByDateAndProductId(recordDate, productId);

        if (existingRecord != null) {
            // Validasi apakah operasi "out" akan menyebabkan stok negatif
            int updatedStock = existingRecord.getStock() + delta;
            if (updatedStock < 0) {
                throw new AppException(400, "Stok tidak boleh negatif untuk produk: " + productId);
            }

            // Jika valid, update stok yang sudah ada
            inOutRecordRepository.updateStockRecord(updatedStock, recordDate, productId);
            System.out.println(
                    "Stok untuk produk " + productId + " pada tanggal " + recordDate + " diperbarui menjadi "
                            + updatedStock);
        } else {
            // Jika tidak ada record, periksa stok terakhir sebelum tanggal ini
            StockRecord lastRecordBefore = stockRecordRepository.getLastRecordBeforeDate(productId, recordDate);
            int lastStock = (lastRecordBefore != null) ? lastRecordBefore.getStock() : 0;

            // Validasi apakah operasi "out" akan menyebabkan stok negatif
            int newStock = lastStock + delta;
            if (newStock < 0) {
                throw new AppException(400, "Stok tidak boleh negatif untuk produk: " + productId);
            }

            // Jika valid, buat record stok baru
            StockRecord newRecord = new StockRecord(productId, newStock, recordDate);
            inOutRecordRepository.insertStockRecord(newRecord);
            System.out.println(
                    "Stok baru untuk produk " + productId + " pada tanggal " + recordDate + " dimasukkan dengan jumlah "
                            + newStock);
        }

        // Memperbarui stok kumulatif untuk tanggal-tanggal setelah tanggal ini
        stockRecordRepository.updateCumulativeStocks(productId, recordDate, delta);
    }

}

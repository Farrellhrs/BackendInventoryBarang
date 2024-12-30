package com.pbo.warehouse.api.services;

import java.util.List;

import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.services.interfaces.InOutRecordServiceIf;

public class InOutRecordService implements InOutRecordServiceIf {

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
        throw new UnsupportedOperationException("Unimplemented method 'addRecord'");
    }

    @Override
    public void updateRecord(UpdateInOutRequestDto Record) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRecord'");
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

}

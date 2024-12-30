package com.pbo.warehouse.api.services;

import java.util.List;

import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.services.interfaces.InOutRecordServiceIf;

public class InOutRecordService implements InOutRecordServiceIf {

    @Override
    public List<GetInOutResponseDto> getAllInbounds(GetAllInOutRequestDto params) {
        /*
         * TODO: implement this logics
         * - get all inbounds (call InboundRepository.getAllRecords)
         * - get total data (call InboundRepository.getTotalData)
         * - create pagination response (call PaginationUtil.createPaginationResponse)
         * - return GetAllInOutResponseDto
         * - throw AppException if there is an exception
         */
        
        throw new UnsupportedOperationException("Unimplemented method 'getAllInbounds'");
    }

    @Override
    public GetInOutResponseDto getInboundById(String id) {
        /*
         * TODO: implement this logics
         * - get inbound by id (call InboundRepository.getRecordById)
         * - return GetInOutResponseDto
         * - throw AppException if there is an exception
         */
        throw new UnsupportedOperationException("Unimplemented method 'getInboundById'");
    }

    @Override
    public void addInbound(AddInOutRequestDto inbound) {
        /*
         * TODO: implement this logics
         * - get existing record by date and product id (call InboundRepository.getRecordByDateAndProductId)
         * - if record exists, update the record (call InboundRepository.updateRecord)
         * - else, insert new record (call InboundRepository.insertRecord)
         * - throw AppException if there is an exception
         */
        throw new UnsupportedOperationException("Unimplemented method 'addInbound'");
    }

    @Override
    public void updateInbound(UpdateInOutRequestDto inbound) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInbound'");
    }

    @Override
    public void deleteInbound(String id) {
        /*
         * TODO: implement this logics
         * - call InboundRepository.deleteRecord
         * - throw AppException if there is an exception
         */
        throw new UnsupportedOperationException("Unimplemented method 'deleteInbound'");
    }

}

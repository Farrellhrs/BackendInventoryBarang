package com.pbo.warehouse.api.services.interfaces;

import java.util.List;

import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;

public interface InOutRecordServiceIf {
    List<GetInOutResponseDto> getAllRecords(GetAllInOutRequestDto params);

    GetInOutResponseDto getRecordById(String id);

    void addRecord(AddInOutRequestDto record);

    void updateRecord(UpdateInOutRequestDto record);

    void deleteRecord(String id);
}

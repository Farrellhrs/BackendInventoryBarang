package com.pbo.warehouse.api.services.interfaces;

import java.util.List;

import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;

public interface InboundServiceIf {
    List<GetInOutResponseDto> getAllInbounds(GetAllInOutRequestDto params);

    GetInOutResponseDto getInboundById(String id);

    void addInbound(AddInOutRequestDto inbound);

    void updateInbound(UpdateInOutRequestDto inbound);

    void deleteInbound(String id);
}

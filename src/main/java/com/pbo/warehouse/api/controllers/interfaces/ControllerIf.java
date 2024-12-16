package com.pbo.warehouse.api.controllers.interfaces;

import com.google.gson.Gson;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface ControllerIf {
    final Gson gson = new Gson();
    final ResponseBodyDto responseBody = new ResponseBodyDto();
}

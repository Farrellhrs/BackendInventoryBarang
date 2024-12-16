package com.pbo.warehouse.api.controllers.interfaces;

import com.google.gson.Gson;
import com.pbo.warehouse.api.dto.ResponseBody;

public interface ControllerIf {
    final Gson gson = new Gson();
    final ResponseBody responseBody = new ResponseBody();
}

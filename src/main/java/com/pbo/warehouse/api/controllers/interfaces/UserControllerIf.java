package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface UserControllerIf extends ControllerIf {
    ResponseBodyDto getUserProfile(spark.Request req, spark.Response res);

    ResponseBodyDto updateUserProfile(spark.Request req, spark.Response res);
}

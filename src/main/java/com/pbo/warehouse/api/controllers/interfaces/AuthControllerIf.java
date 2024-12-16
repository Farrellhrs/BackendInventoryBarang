package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface AuthControllerIf extends ControllerIf {
    ResponseBodyDto login(spark.Request req, spark.Response res);

    ResponseBodyDto register(spark.Request req, spark.Response res);

    void logout(spark.Request req, spark.Response res);
}

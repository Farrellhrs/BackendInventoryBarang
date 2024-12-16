package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBody;

public interface AuthControllerIf extends ControllerIf {
    ResponseBody login(spark.Request req, spark.Response res);

    ResponseBody register(spark.Request req, spark.Response res);

    void logout(spark.Request req, spark.Response res);
}

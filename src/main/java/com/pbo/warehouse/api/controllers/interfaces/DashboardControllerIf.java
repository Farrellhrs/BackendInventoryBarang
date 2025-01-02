package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface DashboardControllerIf extends ControllerIf {
    ResponseBodyDto getChart(spark.Request req, spark.Response res);

    ResponseBodyDto getSummary(spark.Request req, spark.Response res);
}

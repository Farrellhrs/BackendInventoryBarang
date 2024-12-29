package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface OutboundControllerIf extends ControllerIf {
    ResponseBodyDto getAllOutbounds(spark.Request req, spark.Response res);

    ResponseBodyDto getOutboundById(spark.Request req, spark.Response res);

    ResponseBodyDto addOutbound(spark.Request req, spark.Response res);

    ResponseBodyDto updateOutbound(spark.Request req, spark.Response res);

    ResponseBodyDto deleteOutbound(spark.Request req, spark.Response res);
}

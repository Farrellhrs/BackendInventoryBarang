package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface InOutRecordControllerIf extends ControllerIf {
    ResponseBodyDto getAllInbounds(spark.Request req, spark.Response res);

    ResponseBodyDto getInboundById(spark.Request req, spark.Response res);

    ResponseBodyDto addInbound(spark.Request req, spark.Response res);

    ResponseBodyDto updateInbound(spark.Request req, spark.Response res);

    ResponseBodyDto deleteInbound(spark.Request req, spark.Response res);
}

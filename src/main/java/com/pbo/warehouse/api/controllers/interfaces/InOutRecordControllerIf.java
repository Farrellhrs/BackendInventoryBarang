package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface InOutRecordControllerIf extends ControllerIf {
    ResponseBodyDto getAllRecords(spark.Request req, spark.Response res, String type);

    ResponseBodyDto getRecordById(spark.Request req, spark.Response res);

    ResponseBodyDto addRecord(spark.Request req, spark.Response res, String type);

    ResponseBodyDto updateRecord(spark.Request req, spark.Response res);

    ResponseBodyDto deleteRecord(spark.Request req, spark.Response res);
}

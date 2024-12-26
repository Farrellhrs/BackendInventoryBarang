package com.pbo.warehouse.api.controllers.interfaces;

import com.pbo.warehouse.api.dto.ResponseBodyDto;

public interface ProductControllerIf extends ControllerIf {
    ResponseBodyDto getProducts(spark.Request req, spark.Response res);

    ResponseBodyDto getProductById(spark.Request req, spark.Response res);

    ResponseBodyDto addProduct(spark.Request req, spark.Response res);

    ResponseBodyDto updateProduct(spark.Request req, spark.Response res);

    ResponseBodyDto deleteProduct(spark.Request req, spark.Response res);
}

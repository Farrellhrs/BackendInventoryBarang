package com.pbo.warehouse.api.controllers;

import java.util.List;

import com.pbo.warehouse.api.controllers.interfaces.ProductControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductsResponseDto;
import com.pbo.warehouse.api.dto.response.PaginationResponse;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.services.ProductService;

import spark.Request;
import spark.Response;

public class ProductController implements ProductControllerIf {
    private final ProductService productService = new ProductService();

    @Override
    public ResponseBodyDto getProducts(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            String page = req.queryParams("page");
            String limit = req.queryParams("limit");
            String category = req.queryParams("category");
            String name = req.queryParams("name");

            if (category == null) {
                return responseBody.error(400, "Kategori tidak boleh kosong", null);
            }

            GetProductsRequestDto params = new GetProductsRequestDto(
                    page != null ? Integer.parseInt(page) : 1,
                    limit != null ? Integer.parseInt(limit) : 10,
                    category,
                    name);

            GetProductsResponseDto response = productService.getProducts(params);

            return responseBody.successWithPagination(
                    200,
                    "Berhasil",
                    gson.toJson(response.getData()),
                    gson.toJson(response.getPagination()));
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto getProductById(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
    }

    @Override
    public ResponseBodyDto addProduct(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public ResponseBodyDto updateProduct(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public ResponseBodyDto deleteProduct(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

}

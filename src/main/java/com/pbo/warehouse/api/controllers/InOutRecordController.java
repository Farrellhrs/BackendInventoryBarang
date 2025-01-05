package com.pbo.warehouse.api.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pbo.warehouse.api.controllers.interfaces.InOutRecordControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.dto.response.GetAllInOutResponseDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.services.InOutRecordService;

import spark.Request;
import spark.Response;

public class InOutRecordController implements InOutRecordControllerIf {
    private final InOutRecordService InOutRecordService = new InOutRecordService();

    @Override
    public ResponseBodyDto getAllRecords(Request req, Response res, String type) {
        /*
         * TODO: implement this logics
         * - get request query params (page, limit, sort, order, category, startDate,
         * endDate)
         * - validate query params
         * - page, limit, sort, order: integer (optional)
         * - category: string (optional) includes only 'electronic', 'cosmetic', 'fnb'
         * - startDate, endDate: date format (yyyy-MM-dd) (optional)
         * - call Record service method to get all Records
         * - return responses
         * - 200: success
         * - 400: bad request (invalid query params)
         * - 500: internal server error (exception handling)
         * - response body: must include array of json (id, productId, skuCode,
         * productName, category, quantity, recordDate)
         */
        final ResponseBodyDto responseBody = new ResponseBodyDto();

        try {
            String page = req.queryParams("page");
            String limit = req.queryParams("limit");
            String category = req.queryParams("category");
            String startDateStr = req.queryParams("startDate");
            String sort = req.queryParams("sort");
            String endDateStr = req.queryParams("endDate");
            String order = req.queryParams("order");

            // Validasi kategori
            List<String> validCategories = new ArrayList<>();
            validCategories.add("electronic");
            validCategories.add("cosmetic");
            validCategories.add("fnb");

            if (category != null && !category.isEmpty() && !validCategories.contains(category)) {
                return responseBody.error(400, "Kategori tidak valid", null);
            }

            // Validasi sort (kolom)
            List<String> validColumns = InOutRecord.toColumns();
            validColumns.add("sku_code");
            validColumns.add("name");
            validColumns.add("category");
            if (sort != null && !validColumns.contains(sort)) {
                return responseBody.error(400, "Kolom sort tidak valid", null);
            }

            // Validasi order
            if (order != null && !"asc".equals(order) && !"desc".equals(order)) {
                return responseBody.error(400, "Order tidak valid", null);
            }

            Date startDate = null;
            Date endDate = null;

            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
            }

            // Validasi date
            if (startDate != null && endDate != null && startDate.after(endDate)) {
                return responseBody.error(400, "Tanggal awal tidak boleh lebih besar dari tanggal akhir", null);
            }

            GetAllInOutRequestDto params = new GetAllInOutRequestDto(
                    page != null ? Integer.parseInt(page) : 1,
                    limit != null ? Integer.parseInt(limit) : 10,
                    category, startDate, endDate, sort, order, type);

            GetAllInOutResponseDto response = InOutRecordService.getAllRecords(params);

            return responseBody.successWithPagination(
                    200,
                    "Berhasil mendapatkan data barang",
                    gson.toJson(response.getData()),
                    gson.toJson(response.getPagination()));
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto getRecordById(Request req, Response res) {
        /*
         * TODO: implement this logics
         * - get request path params (id) (req.params("id"))
         * - validate path params (id cannot be null)
         * - call Record service method to get Record by id
         * - return responses
         * - 200: success
         * - 400: bad request (invalid path params)
         * - 404: not found (Record not found)
         * - 500: internal server error (exception handling)
         * - response body: must include json (id, productId, skuCode, productName,
         * category, quantity, recordDate, stock, maxStock, createdBy, details)
         */

        final ResponseBodyDto responseBody = new ResponseBodyDto();

        try {
            String id = req.params("id");

            if (id == null) {
                return responseBody.error(400, "ID tidak boleh kosong", null);
            }

            int idInt = Integer.parseInt(id);

            GetInOutResponseDto response = InOutRecordService.getRecordById(idInt);

            return responseBody.success(
                    200,
                    "Berhasil mendapatkan data barang",
                    gson.toJson(response));
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), e.getStackTrace());
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), e.getStackTrace());
        }
    }

    @Override
    public ResponseBodyDto addRecord(Request req, Response res, String type) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            // Parse request body to AddInOutRequestDto
            AddInOutRequestDto requestDto = gson.fromJson(req.body(), AddInOutRequestDto.class);
            String createdBy = req.attribute("email");

            if (createdBy == null) {
                res.status(401);
                return responseBody.error(401, "Unauthorized", null);
            }

            requestDto.setCreatedBy(createdBy);
            requestDto.setType(type);
            System.out.println(requestDto.getType());
            System.out.println(requestDto.getQuantity());
            
            System.out.println(requestDto.getRecordDate());
            System.out.println(requestDto.getProductId());

            // Validate request body
            if (requestDto.getProductId() == null || requestDto.getProductId().isEmpty()) {
                return responseBody.error(400, "Bad Request: 'productId' is required", null);
            }
            if (requestDto.getQuantity() <= 0) {
                return responseBody.error(400, "Bad Request: 'quantity' is required", null);
            }
            InOutRecordService.addRecord(requestDto);

            return responseBody.success(201, "Product berhasil ditambahkan", null);

        } catch (AppException e) {
            return responseBody.error(400, "Bad Request: " + e.getMessage(), null);

        } catch (Exception e) {
            return responseBody.error(500, "Internal Server Error:" + e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto updateRecord(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            // Parse request path param (id)
            String recordId = req.params("id");
            if (recordId == null || recordId.isEmpty()) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'id' is required", null);
            }

            // Parse request body
            UpdateInOutRequestDto requestDto = gson.fromJson(req.body(), UpdateInOutRequestDto.class);

            requestDto.setId(Integer.parseInt(recordId));
            // Validate request body
            if (requestDto.getCurrentProductId() == null || requestDto.getCurrentProductId().isEmpty()) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'productId' is required", null);
            }
            if (requestDto.getQuantity() <= 0) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'quantity' must be greater than 0", null);
            }
            
            // Call service to update record
            InOutRecordService.updateRecord(requestDto);

            return responseBody.success(200, "Record updated successfully", null);

        } catch (AppException e) {
            return responseBody.error(400, "Bad Request: " + e.getMessage(), null);
        } catch (Exception e) {
            return responseBody.error(500, "Internal Server Error: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto deleteRecord(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            // Parse request path param (id)
            String recordId = req.params(":id");
            if (recordId == null || recordId.isEmpty()) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'id' is required", null);
            }

            // Call service to delete record
            InOutRecordService.deleteRecord(recordId);

            // Return success response
            res.status(200);
            return responseBody.success(200, "Record deleted successfully", null);

        } catch (IllegalArgumentException e) {
            res.status(400);
            return responseBody.error(400, "Bad Request: " + e.getMessage(), null);
        } catch (Exception e) {
            res.status(500);
            return responseBody.error(500, "Internal Server Error: " + e.getMessage(), null);
        }
    }
}

package com.pbo.warehouse.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pbo.warehouse.api.controllers.interfaces.InOutRecordControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.AddInOutRequestDto;
import com.pbo.warehouse.api.dto.request.AddProductRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateInOutRequestDto;
import com.pbo.warehouse.api.services.InOutRecordService;
import com.pbo.warehouse.api.services.ProductService;

import spark.Request;
import spark.Response;

public class InOutRecordController implements InOutRecordControllerIf {
    private final InOutRecordService InOutRecordService = new InOutRecordService();
    @Override
    public ResponseBodyDto getAllRecords(Request req, Response res, String type) {
        /*
         * TODO: implement this logics
         * - get request query params (page, limit, sort, order, category, startDate, endDate)
         * - validate query params
         * -    page, limit, sort, order: integer (optional)
         * -    category: string (optional) includes only 'electronic', 'cosmetic', 'fnb'
         * -    startDate, endDate: date format (yyyy-MM-dd) (optional)
         * - call Record service method to get all Records
         * - return responses
         * -    200: success
         * -    400: bad request (invalid query params)
         * -    500: internal server error (exception handling)
         * - response body: must include array of json (id, productId, skuCode, productName, category, quantity, recordDate)
         */

        throw new UnsupportedOperationException("Unimplemented method 'getRecords'");
    }

    @Override
    public ResponseBodyDto getRecordById(Request req, Response res) {
        /*
         * TODO: implement this logics
         * - get request path params (id) (req.params("id"))
         * - validate path params (id cannot be null)
         * - call Record service method to get Record by id
         * - return responses
         * -    200: success
         * -    400: bad request (invalid path params)
         * -    404: not found (Record not found)
         * -    500: internal server error (exception handling)
         * - response body: must include json (id, productId, skuCode, productName, category, quantity, recordDate, stock, maxStock, createdBy, details)
         */
        
        throw new UnsupportedOperationException("Unimplemented method 'getRecordById'");
    }

    @Override
    public ResponseBodyDto addRecord(Request req, Response res, String type) {
        /*
         * TODO: implement this logics
         * - get request body (productId, quantity, entryDate)
         * - validate request body
         * -    productId: string (required)
         * -    quantity: integer (required)
         * -    entryDate: date format (yyyy-MM-dd) (required)
         * - call Record service method to add Record
         * - return responses
         * -    201: created
         * -    400: bad request (invalid request body)
         * -    500: internal server error (exception handling)
         */
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
            if ("out".equalsIgnoreCase(requestDto.getType())) {
                int quantityrequest = -Math.abs(requestDto.getQuantity());
                requestDto.setQuantity(quantityrequest);
            }
            System.out.println(requestDto.getRecordDate());

            // Validate request body
            if (requestDto.getProductId() == null || requestDto.getProductId().isEmpty()) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'productId' is required", null);
            }
            if (requestDto.getQuantity() <= 0) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'productId' is required", null);
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date recordDate = sdf.parse(requestDto.getRecordDate());
            } catch (ParseException e) {
                return responseBody.error(400, "Format tanggal expiredDate tidak sesuai yyyy-MM-dd", e.getMessage());
            }

            InOutRecordService.addRecord(requestDto);

            // Return success response
            res.status(201);
            return responseBody.success(201, "Product berhasil ditambahkan", null);

        } catch (IllegalArgumentException e) {
            // Handle invalid input
            res.status(400);
            return responseBody.error(400, "Bad Request: " + e.getMessage(), null);

        } catch (Exception e) {
            // Handle unexpected errors
            res.status(500);
            return responseBody.error(500, "Internal Server Error:" + e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto updateRecord(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            // Parse request path param (id)
            String recordId = req.params(":id");
            if (recordId == null || recordId.isEmpty()) {
                res.status(400);
                return responseBody.error(400, "Bad Request: 'id' is required", null);
            }

            // Parse request body
            UpdateInOutRequestDto requestDto = gson.fromJson(req.body(), UpdateInOutRequestDto.class);

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

            // Return success response
            res.status(200);
            return responseBody.success(200, "Record updated successfully", null);

        } catch (IllegalArgumentException e) {
            res.status(400);
            return responseBody.error(400, "Bad Request: " + e.getMessage(), null);
        } catch (Exception e) {
            res.status(500);
            return responseBody.error(500, "Internal Server Error: " + e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto deleteRecord(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecord'");
    }


    // --------function bantuan------------
    private boolean isValidDate(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    try {
        sdf.parse(date);
        return true;
    } catch (ParseException e) {
        return false;
    }
}
}

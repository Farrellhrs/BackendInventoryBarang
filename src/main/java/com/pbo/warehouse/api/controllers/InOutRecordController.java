package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.InOutRecordControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

import spark.Request;
import spark.Response;

public class InOutRecordController implements InOutRecordControllerIf {

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
        throw new UnsupportedOperationException("Unimplemented method 'addRecord'");
    }

    @Override
    public ResponseBodyDto updateRecord(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRecord'");
    }

    @Override
    public ResponseBodyDto deleteRecord(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecord'");
    }

}

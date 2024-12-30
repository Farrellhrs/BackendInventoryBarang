package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.InOutRecordControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

import spark.Request;
import spark.Response;

public class InOutRecordController implements InOutRecordControllerIf {

    @Override
    public ResponseBodyDto getAllInbounds(Request req, Response res) {
        /*
         * TODO: implement this logics
         * - get request query params (page, limit, sort, order, category, startDate, endDate)
         * - validate query params
         * -    page, limit, sort, order: integer (optional)
         * -    category: string (optional) includes only 'electronic', 'cosmetic', 'fnb'
         * -    startDate, endDate: date format (yyyy-MM-dd) (optional)
         * - call inbound service method to get all inbounds
         * - return responses
         * -    200: success
         * -    400: bad request (invalid query params)
         * -    500: internal server error (exception handling)
         * - response body: must include array of json (id, productId, skuCode, productName, category, quantity, recordDate)
         */

        throw new UnsupportedOperationException("Unimplemented method 'getInbounds'");
    }

    @Override
    public ResponseBodyDto getInboundById(Request req, Response res) {
        /*
         * TODO: implement this logics
         * - get request path params (id) (req.params("id"))
         * - validate path params (id cannot be null)
         * - call inbound service method to get inbound by id
         * - return responses
         * -    200: success
         * -    400: bad request (invalid path params)
         * -    404: not found (inbound not found)
         * -    500: internal server error (exception handling)
         * - response body: must include json (id, productId, skuCode, productName, category, quantity, recordDate, stock, maxStock, createdBy, details)
         */
        
        throw new UnsupportedOperationException("Unimplemented method 'getInboundById'");
    }

    @Override
    public ResponseBodyDto addInbound(Request req, Response res) {
        /*
         * TODO: implement this logics
         * - get request body (productId, quantity, entryDate)
         * - validate request body
         * -    productId: string (required)
         * -    quantity: integer (required)
         * -    entryDate: date format (yyyy-MM-dd) (required)
         * - call inbound service method to add inbound
         * - return responses
         * -    201: created
         * -    400: bad request (invalid request body)
         * -    500: internal server error (exception handling)
         */
        throw new UnsupportedOperationException("Unimplemented method 'addInbound'");
    }

    @Override
    public ResponseBodyDto updateInbound(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInbound'");
    }

    @Override
    public ResponseBodyDto deleteInbound(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInbound'");
    }

}

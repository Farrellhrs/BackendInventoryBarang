package com.pbo.warehouse.api.controllers;

import com.pbo.warehouse.api.controllers.interfaces.InboundControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

import spark.Request;
import spark.Response;

public class InboundController implements InboundControllerIf {

    @Override
    public ResponseBodyDto getAllInbounds(Request req, Response res) {
        /*
         * TODO: implement this logics
         * - get request query params (page, limit, sort, order, category, startDate, endDate)
         * - validate query params
         * - 
         */

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInbounds'");
    }

    @Override
    public ResponseBodyDto getInboundById(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInboundById'");
    }

    @Override
    public ResponseBodyDto addInbound(Request req, Response res) {
        // TODO Auto-generated method stub
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

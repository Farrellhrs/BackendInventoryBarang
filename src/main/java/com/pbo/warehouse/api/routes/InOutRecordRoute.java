package com.pbo.warehouse.api.routes;

import com.pbo.warehouse.api.controllers.InOutRecordController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

import static spark.Spark.*;

import com.pbo.warehouse.api.middleware.AuthMiddleware;

public class InOutRecordRoute {
    private static InOutRecordController inOutRecordController = new InOutRecordController();

    public static void init() {
        /*
         * Find all inbound stock
         */
        get("/api/stock/find/inbound", (req, res) -> {
            ResponseBodyDto response = inOutRecordController.getAllRecords(req, res, "out");

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        /*
         * Find all outbound stock
         */
        get("/api/stock/find/outbound", (req, res) -> {
            ResponseBodyDto response = inOutRecordController.getAllRecords(req, res, "in");

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        get("/api/stock/find/:id", (req, res) -> {
            ResponseBodyDto response = inOutRecordController.getRecordById(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        /*
         * Add new inbound stock
         */
        before("/api/stock/add/inbound", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        post("/api/stock/add/inbound", (req, res) -> {
            // TODO: implement this
            ResponseBodyDto response = inOutRecordController.addRecord(req, res, "in");

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        /*
         * Add new outbound stock
         */
        post("/api/stock/add/outbound", (req, res) -> {
            // TODO: implement this
            ResponseBodyDto response = inOutRecordController.addRecord(req, res, "out");

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        before("/api/stock/update/:id", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        put("/api/stock/update/:id", (req, res) -> {
            ResponseBodyDto response = inOutRecordController.updateRecord(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        before("/api/stock/delete/:id", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        delete("/api/stock/delete/:id", (req, res) -> {
            ResponseBodyDto response = inOutRecordController.deleteRecord(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });
    }
}

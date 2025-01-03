package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

import com.pbo.warehouse.api.controllers.InOutRecordController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.middleware.AuthMiddleware;

public class InOutRecordRoute {
    private static InOutRecordController InOutRecordController = new InOutRecordController();
    public static void init() {
        /*
         * Find all inbound stock
         */
        get("/api/stock/find/inbound", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        /*
         * Find all outbound stock
         */
        get("/api/stock/find/outbound", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/stock/find/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        /*
         * Add new inbound stock
         */
        before("/api/stock/add/inbound", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        post("/api/stock/add/inbound", (req, res) -> {
            // TODO: implement this
            ResponseBodyDto response = InOutRecordController.addRecord(req, res, "in");

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        /*
         * Add new outbound stock
         */
        post("/api/stock/add/outbound", (req, res) -> {
            // TODO: implement this
            ResponseBodyDto response = InOutRecordController.addRecord(req, res, "out");

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        before("/api/stock/update/:id", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        put("/api/stock/update/:id", (req, res) -> {
            ResponseBodyDto response = InOutRecordController.updateRecord(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        before("/api/stock/delete/:id", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        delete("/api/stock/delete/:id", (req, res) -> {
            ResponseBodyDto response = InOutRecordController.deleteRecord(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });
    }
}

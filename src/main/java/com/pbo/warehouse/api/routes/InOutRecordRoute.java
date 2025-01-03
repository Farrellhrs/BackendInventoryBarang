package com.pbo.warehouse.api.routes;

import com.pbo.warehouse.api.controllers.InOutRecordController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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
        post("/api/stock/add/inbound", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        /*
         * Add new outbound stock
         */
        post("/api/stock/add/outbound", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        put("/api/stock/update/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        delete("/api/stock/delete/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });
    }
}

package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

public class InOutRecordRoute {
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

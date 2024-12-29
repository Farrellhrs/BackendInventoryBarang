package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

public class InboundRoute {
    public static void init() {
        post("/api/inbound/add", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/inbound/find", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/inbound/find/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        put("/api/inbound/update/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        delete("/api/inbound/delete/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });
    }
}

package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

public class InOutRoute {
    public static void init() {
        post("/api/inout/add", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/inout/find", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/inout/find/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        put("/api/inout/update/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        delete("/api/inout/delete/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });
    }
}

package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

public class DashboardRoute {
    public static void init() {
        get("/api/dashboard/history", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/dashboard/summary", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });
    }
}

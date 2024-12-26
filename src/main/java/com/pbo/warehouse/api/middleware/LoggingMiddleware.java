package com.pbo.warehouse.api.middleware;

import static spark.Spark.*;

import java.util.Date;

public class LoggingMiddleware {
    public static void apply() {
        // Middleware sebelum request diproses
        before((req, res) -> {
            System.out.println("[ " + new Date() + " ]");
            System.out.println("=== Incoming Request ===");
            System.out.println("Method: " + req.requestMethod());
            System.out.println("URL: " + req.url());
            System.out.println("Headers: " + req.headers());
            System.out.println("Body: " + req.body());
            System.out.println("========================");
        });

        // Middleware setelah request selesai diproses
        after((req, res) -> {
            System.out.println("=== Response Info ===");
            System.out.println("Status Code: " + res.status());
            System.out.println("Content Type: " + res.type());
            System.out.println("Body: " + res.body());
            System.out.println("=====================");
        });
    }
}

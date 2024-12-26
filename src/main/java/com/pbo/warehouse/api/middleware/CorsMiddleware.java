package com.pbo.warehouse.api.middleware;

import java.util.HashMap;

import spark.Spark;

public class CorsMiddleware {
    private static final HashMap<String, String> corsHeaders = new HashMap<>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    }

    public static void apply() {
        // Handle OPTIONS requests (preflight requests)
        Spark.options("/*", (request, response) -> {
            response.header("Access-Control-Max-Age", "3600");
            return "OK";
        });

        // Add CORS headers to all other responses
        Spark.after((request, response) -> {
            corsHeaders.forEach(response::header);
        });
    }
}

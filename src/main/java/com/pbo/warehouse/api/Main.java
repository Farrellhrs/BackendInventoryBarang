/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pbo.warehouse.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.pbo.warehouse.api.middleware.CorsMiddleware;
import com.pbo.warehouse.api.middleware.LoggingMiddleware;
import com.pbo.warehouse.api.routes.AuthRoute;
import com.pbo.warehouse.api.routes.DashboardRoute;
import com.pbo.warehouse.api.routes.ProductRoute;

import spark.Spark;

/**
 *
 * @author dika-mac
 */
public class Main {

    public static void main(String[] args) {
        final int PORT = 8090;
        port(PORT);

        CorsMiddleware.apply();
        LoggingMiddleware.apply();

        // Register a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            Spark.stop(); // Stops the Spark server
        }));

        get("/api/hello-world", (req, res) -> {
            res.type("application/json");
            return new Gson().toJson("Hello World");
        });

        // Initialize routes
        AuthRoute.init();
        ProductRoute.init();
        DashboardRoute.init();

        System.out.println("Server started on port " + PORT);
    }
}

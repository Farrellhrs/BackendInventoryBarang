/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pbo.warehouse.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.pbo.warehouse.api.routes.AuthRoute;

import spark.Spark;

/**
 *
 * @author dika-mac
 */
public class Main {

    public static void main(String[] args) {
        final int PORT = 8090;

        port(PORT);

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

        System.out.println("Server started on port " + PORT);
    }
}

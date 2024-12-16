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

        get("/api/test", (req, res) -> {
            res.type("application/json");

            JsonResponse response = new JsonResponse(true, "This is a message", "Sample data 2");
            return new Gson().toJson(response);
        });

        // Initialize routes
        AuthRoute.init();

        System.out.println("Server started on port " + PORT);
    }

    public static class JsonResponse {
        public boolean success;
        public String message;
        public Object data;

        public JsonResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}

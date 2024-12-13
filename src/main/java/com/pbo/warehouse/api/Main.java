/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pbo.warehouse.api;

import static spark.Spark.*;
import com.google.gson.Gson;

/**
 *
 * @author dika-mac
 */
public class Main {

    public static void main(String[] args) {
        port(8080);
        
        get("/api/test", (req, res) -> {
            res.type("application/json");
            
            JsonResponse response = new JsonResponse(true, "This is a message", "Sample data");
            return new Gson().toJson(response);
        });
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

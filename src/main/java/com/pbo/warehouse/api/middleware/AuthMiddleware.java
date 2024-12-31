package com.pbo.warehouse.api.middleware;

import static spark.Spark.*;

import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.utils.JwtUtil;

public class AuthMiddleware {
    public static void authenticate(spark.Request req, spark.Response res) {
        // Skip authentication for OPTIONS requests (preflight)
        if ("OPTIONS".equalsIgnoreCase(req.requestMethod())) {
            return; // Allow OPTIONS requests to pass through
        }

        String authHeader = req.headers("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            haltWithJson(res, 401, "Unauthorized: Missing or invalid token");
        }

        String token = authHeader.substring(7);
        if (!JwtUtil.validateToken(token)) {
            haltWithJson(res, 401, "Unauthorized: Invalid or expired token");
        }

        String email = JwtUtil.getClaims(token).getSubject();
        if (email == null) {
            haltWithJson(res, 401, "Unauthorized: Invalid token");
        }

        req.attribute("email", email);
    }

    private static void haltWithJson(spark.Response res, int statusCode, String message) {
        res.type("application/json");
        res.status(statusCode);
        halt(statusCode, new ResponseBodyDto().error(statusCode, message, null).toJson());
    }
}

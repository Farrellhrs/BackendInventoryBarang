package com.pbo.warehouse.api.middleware;

import static spark.Spark.*;

import com.pbo.warehouse.api.utils.JwtUtil;

public class AuthMiddleware {
    public static void apply() {
        before((req, res) -> {
            String authHeader = req.headers("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                halt(401, "Unauthorized: Missing or invalid token");
            }

            String token = authHeader.substring(7);
            if (!JwtUtil.validateToken(token)) {
                halt(401, "Unauthorized: Invalid or expired token");
            }

            String username = JwtUtil.getClaims(token).getSubject();
            req.attribute("username", username);
        });
    }
}

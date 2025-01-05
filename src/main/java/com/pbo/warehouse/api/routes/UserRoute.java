package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

import com.pbo.warehouse.api.controllers.UserController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.middleware.AuthMiddleware;

public class UserRoute {
    private static UserController controller = new UserController();

    public static void init() {
        before("/api/profile/me", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        get("/api/profile/me", (req, res) -> {
            ResponseBodyDto response = controller.getUserProfile(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        before("/api/profile/update", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        put("/api/profile/update", (req, res) -> {
            ResponseBodyDto response = controller.updateUserProfile(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });
    }
}

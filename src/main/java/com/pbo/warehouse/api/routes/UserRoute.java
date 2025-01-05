package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

import com.pbo.warehouse.api.controllers.UserController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

public class UserRoute {
    private static UserController controller = new UserController();

    public static void init() {
        get("/api/profile/me", (req, res) -> {
            ResponseBodyDto response = controller.getUserProfile(req, res);

            return "Hello World";
        });

        put("/api/profile/update", (req, res) -> {
            ResponseBodyDto response = controller.updateUserProfile(req, res);

            return "Hello World";
        });
    }
}

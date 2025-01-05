package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

import com.pbo.warehouse.api.controllers.DashboardController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;

public class DashboardRoute {
    private static DashboardController controller = new DashboardController();

    public static void init() {
        get("/api/dashboard/chart", (req, res) -> {
            ResponseBodyDto response = controller.getChart(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        get("/api/dashboard/summary", (req, res) -> {
            ResponseBodyDto response = controller.getSummary(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });
    }
}

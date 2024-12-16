package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

public class ProductRoute {
    // private static AuthController authController = new AuthController();

    public static void init() {
        post("/api/product/add", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        /*
         * get all products
         * query params:
         * - page: int
         * - limit: int
         * - name: string
         * - category: string
         * endpoint:
         * /api/product/find?page=1&limit=10&name=product_name&category=product_category
         */
        get("/api/product/find", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        get("/api/product/find/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        put("/api/product/update/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });

        delete("/api/product/delete/:id", (req, res) -> {
            // TODO: implement this
            return "Hello World";
        });
    }
}

package com.pbo.warehouse.api.routes;

import static spark.Spark.*;

import com.pbo.warehouse.api.controllers.ProductController;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.middleware.AuthMiddleware;

public class ProductRoute {
    private static ProductController productController = new ProductController();

    public static void init() {
        before("/api/product/add", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        post("/api/product/add", (req, res) -> {
            ResponseBodyDto response = productController.addProduct(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        /*
         * get all products
         * protected route
         * query params:
         * - page: int
         * - limit: int
         * - name: string
         * - category: string
         * endpoint:
         * /api/product/find?page=1&limit=10&name=product_name&category=product_category
         */
        before("/api/product/find", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        get("/api/product/find", (req, res) -> {
            ResponseBodyDto response = productController.getProducts(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        before("/api/product/find/:id", (req, res) -> {
            AuthMiddleware.authenticate(req, res);
        });
        get("/api/product/find/:id", (req, res) -> {
            ResponseBodyDto response = productController.getProductById(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        put("/api/product/update/:id", (req, res) -> {
            // TODO: implement this
            ResponseBodyDto response = productController.updateProduct(req, res);

            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });

        delete("/api/product/delete/:id", (req, res) -> {
            // TODO: implement this
            AuthMiddleware.authenticate(req, res);
            ResponseBodyDto response = productController.deleteProduct(req, res);
            res.type("application/json");
            res.status(response.getStatusCode());
            return response.toJson();
        });
    }
}

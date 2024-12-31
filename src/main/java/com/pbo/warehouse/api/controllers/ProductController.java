package com.pbo.warehouse.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pbo.warehouse.api.controllers.interfaces.ProductControllerIf;
import com.pbo.warehouse.api.dto.ResponseBodyDto;
import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateProductRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductsResponseDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;
import com.pbo.warehouse.api.services.ProductService;
import com.pbo.warehouse.api.dto.request.AddProductRequestDto;

import spark.Request;
import spark.Response;

public class ProductController implements ProductControllerIf {
    private final ProductService productService = new ProductService();

    @Override
    public ResponseBodyDto getProducts(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();

        try {
            String page = req.queryParams("page");
            String limit = req.queryParams("limit");
            String category = req.queryParams("category");
            String name = req.queryParams("name");
            String sort = req.queryParams("sort");
            String sortByDetail = req.queryParams("sortByDetail");
            String order = req.queryParams("order");

            // Validasi category
            if (category == null) {
                return responseBody.error(400, "Kategori tidak boleh kosong", null);
            }

            // Validasi kategori
            List<String> validCategories = new ArrayList<>();
            validCategories.add("electronic");
            validCategories.add("cosmetic");
            validCategories.add("fnb");

            if (!validCategories.contains(category)) {
                return responseBody.error(400, "Kategori tidak valid", null);
            }

            if (sort != null && sortByDetail != null) {
                return responseBody.error(400, "Hanya boleh memilih salah satu sort", null);
            }

            // Validasi sort (kolom)
            if (sort != null && !Product.toColumns().contains(sort)) {
                return responseBody.error(400, "Kolom sort tidak valid", null);
            }

            // Validasi sort by detail (kolom)
            List<String> validColumns = new ArrayList<>();
            switch (category) {
                case "electronic":
                    validColumns = ProductElectronic.toColumns();
                    break;
                case "cosmetic":
                    validColumns = ProductCosmetic.toColumns();
                    break;
                case "fnb":
                    validColumns = ProductFnb.toColumns();
                    break;
                default:
                    break;
            }

            if (sortByDetail != null && !validColumns.contains(sortByDetail)) {
                return responseBody.error(400, "Kolom sort by detail tidak valid", null);
            }

            // Validasi order
            if (order != null && !"asc".equals(order) && !"desc".equals(order)) {
                return responseBody.error(400, "Order tidak valid", null);
            }

            GetProductsRequestDto params = new GetProductsRequestDto(
                    page != null ? Integer.parseInt(page) : 1,
                    limit != null ? Integer.parseInt(limit) : 10,
                    category,
                    name, sort, sortByDetail, order);

            GetProductsResponseDto response = productService.getProducts(params);

            return responseBody.successWithPagination(
                    200,
                    "Berhasil",
                    gson.toJson(response.getData()),
                    gson.toJson(response.getPagination()));
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto getProductById(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();

        try {
            String id = req.params("id");

            if (id == null) {
                return responseBody.error(400, "ID tidak boleh kosong", null);
            }

            GetProductResponseDto response = productService.getProductById(id);

            return responseBody.success(
                    200,
                    "Berhasil",
                    gson.toJson(response));
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            return responseBody.error(500, e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto addProduct(Request req, Response res) {
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            AddProductRequestDto reqBody = gson.fromJson(req.body(), AddProductRequestDto.class);

            // Get createdBy from request attribute
            String createdBy = req.attribute("email");
            reqBody.setCreatedBy(createdBy);

            String category = reqBody.getCategory();

            if (category == null || reqBody.getName() == null || reqBody.getSkuCode() == null
                    || reqBody.getMaxStock() == 0 || reqBody.getCreatedBy() == null
                    || reqBody.getDetails() == null) {
                return responseBody.error(400, "Semua field wajib diisi", null);
            }

            // Validasi kategori
            List<String> validCategories = List.of("electronic", "cosmetic", "fnb");
            if (!validCategories.contains(category)) {
                return responseBody.error(400, "Kategori tidak valid", null);
            }

            AddProductRequestDto.ProductDetails details = reqBody.getDetails();
            // Validasi details
            if (category.equals("electronic")) {
                if (details.getType() == null) {
                    return responseBody.error(400, "Field detail type wajib diisi", null);
                }
            } else if (category.equals("cosmetic") || category.equals("fnb")) {
                if (details.getExpireDate() == null) {
                    return responseBody.error(400, "Field detail expiredDate wajib diisi", null);
                }

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date expiredDate = sdf.parse(details.getExpireDate());
                    details.setExpireDate(sdf.format(expiredDate));
                } catch (ParseException e) {
                    return responseBody.error(400, "Format tanggal expiredDate tidak yyyy-MM-dd", e.getMessage());
                }
            }

            // Menyimpan produk ke database menggunakan service
            productService.addProduct(reqBody);

            return responseBody.success(201, "Produk berhasil ditambahkan", null);
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return responseBody.error(500, e.getMessage(), null);
        }
    }

    @Override
    public ResponseBodyDto updateProduct(Request req, Response res) {
        // TODO Auto-generated method stub
        final ResponseBodyDto responseBody = new ResponseBodyDto();
        try {
            UpdateProductRequestDto reqBody = gson.fromJson(req.body(), UpdateProductRequestDto.class);

            UpdateProductRequestDto.ProductDetails details = reqBody.getDetails();

            // Validate details based on category
            if (reqBody.getCategory().equals("electronic")) {
                if (details.getType() == null) {
                    return responseBody.error(400, "Field detail type wajib diisi untuk kategori electronic", null);
                }
            } else if (reqBody.getCategory().equals("cosmetic") || reqBody.getCategory().equals("fnb")) {
                if (details.getExpireDate() == null) {
                    return responseBody.error(400, "Field detail expiredDate wajib diisi untuk kategori ini", null);
                }

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date expireDate = sdf.parse(details.getExpireDate());
                    details.setExpireDate(sdf.format(expireDate));
                } catch (ParseException e) {
                    return responseBody.error(400, "Format tanggal expiredDate tidak sesuai yyyy-MM-dd", e.getMessage());
                }
            }

            // Update product in the database via service
            productService.updateProduct(reqBody);

            return responseBody.success(200, "Produk berhasil diperbarui", null);
        } catch (AppException e) {
            return responseBody.error(e.getStatusCode(), e.getMessage(), null);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return responseBody.error(500, "Terjadi kesalahan pada server", null);
        }
    }


    @Override
    public ResponseBodyDto deleteProduct(Request req, Response res) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

}

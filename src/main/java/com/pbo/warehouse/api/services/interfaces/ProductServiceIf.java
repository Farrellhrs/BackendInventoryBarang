package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductsResponseDto;
import com.pbo.warehouse.api.models.Product;

public interface ProductServiceIf {
    GetProductsResponseDto getProducts(GetProductsRequestDto params);

    GetProductResponseDto getProductById(String id);

    boolean addproduct(Product product);
}
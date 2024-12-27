package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductsResponseDto;

public interface ProductServiceIf {
    GetProductsResponseDto getProducts(GetProductsRequestDto params);

    GetProductsResponseDto getProductById(String id);
}
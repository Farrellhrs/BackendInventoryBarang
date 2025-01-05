package com.pbo.warehouse.api.services.interfaces;

import com.pbo.warehouse.api.dto.request.AddProductRequestDto;
import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateProductRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetAllProductsResponseDto;

public interface ProductServiceIf {
    GetAllProductsResponseDto getProducts(GetProductsRequestDto params);

    GetProductResponseDto getProductById(String id);

    void addProduct(AddProductRequestDto product);

    void updateProduct(UpdateProductRequestDto product);
  
    void deleteProduct(String id);
}
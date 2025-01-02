package com.pbo.warehouse.api.repositories.interfaces;

import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;

public interface ProductRepositoryIf {
    int getTotalData(String productCategory);

    List<ProductElectronic> getAllProductElectronics(GetProductsRequestDto params);

    List<ProductCosmetic> getAllProductCosmetics(GetProductsRequestDto params);

    List<ProductFnb> getAllProductFnbs(GetProductsRequestDto params);

    GetProductResponseDto getProductById(String id);

    void insertProductElectronic(ProductElectronic product);

    void insertProductCosmetic(ProductCosmetic product);

    void insertProductFnb(ProductFnb product);

    boolean deleteProduct(int id);
    void updateProductElectronic(ProductElectronic product);
    void updateProductFnB(ProductFnb product);
    void updateProductCosmetic(ProductCosmetic product);
    void updateProduct(Product product);
}

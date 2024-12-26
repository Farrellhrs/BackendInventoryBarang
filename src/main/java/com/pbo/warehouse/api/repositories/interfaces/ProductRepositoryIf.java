package com.pbo.warehouse.api.repositories.interfaces;

import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;

public interface ProductRepositoryIf {
    int getTotalData(String productCategory);

    List<ProductElectronic> getAllProductElectronics(GetProductsRequestDto params);

    List<ProductCosmetic> getAllProductCosmetics(GetProductsRequestDto params);

    List<ProductFnb> getAllProductFnbs(GetProductsRequestDto params);

    Product getProductById(int id);

    boolean insertProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int id);
}

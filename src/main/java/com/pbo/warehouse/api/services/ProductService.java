package com.pbo.warehouse.api.services;

import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductsResponseDto;
import com.pbo.warehouse.api.dto.response.PaginationResponse;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;
import com.pbo.warehouse.api.repositories.ProductRepository;
import com.pbo.warehouse.api.services.interfaces.ProductServiceIf;
import com.pbo.warehouse.api.utils.PaginationUtil;

public class ProductService implements ProductServiceIf {
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    public GetProductsResponseDto getProducts(GetProductsRequestDto params) {
        List<GetProductResponseDto> products = new ArrayList<>();
        List<ProductElectronic> productElectronics = new ArrayList<>();
        List<ProductCosmetic> productCosmetics = new ArrayList<>();
        List<ProductFnb> productFnbs = new ArrayList<>();
        int totalData = 0;

        if (params.getCategory() != null) {
            switch (params.getCategory()) {
                case "electronic":
                    productElectronics = productRepository.getAllProductElectronics(params);
                    break;
                case "cosmetic":
                    productCosmetics = productRepository.getAllProductCosmetics(params);
                    break;
                case "fnb":
                    productFnbs = productRepository.getAllProductFnbs(params);
                    break;
                default:
                    break;
            }
        }

        for (ProductElectronic product : productElectronics) {
            products.add(GetProductResponseDto.fromEntityElectronic(product));
        }

        for (ProductCosmetic product : productCosmetics) {
            products.add(GetProductResponseDto.fromEntityCosmetic(product));
        }

        for (ProductFnb product : productFnbs) {
            products.add(GetProductResponseDto.fromEntityFnb(product));
        }

        totalData = productRepository.getTotalData(params.getCategory());
        PaginationResponse pagination = PaginationUtil.getPagination(params.getPage(), params.getLimit(), totalData);

        GetProductsResponseDto response = new GetProductsResponseDto(
                products, pagination);

        return response;
    }

    @Override
    public GetProductResponseDto getProductById(String id) {
        GetProductResponseDto product = productRepository.getProductById(id);
        return product;
    }

    @Override
    public boolean addproduct(Product product) {
        return productRepository.insertProduct(product);
    }

}

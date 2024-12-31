package com.pbo.warehouse.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pbo.warehouse.api.dto.request.AddProductRequestDto;
import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.request.UpdateProductRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductsResponseDto;
import com.pbo.warehouse.api.dto.response.PaginationResponse;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.ProductRepository;
import com.pbo.warehouse.api.repositories.UserRepository;
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
    public void addProduct(AddProductRequestDto product) {
        UserRepository userRepository = new UserRepository();
        User currentUser = userRepository.getUserByEmail(product.getCreatedBy());
        if (currentUser == null) {
            throw new AppException(400, "User tidak ditemukan");
        }

        // Generate id product
        product.setId(UUID.randomUUID().toString());

        Date expDate = new Date();
        try {
            if (product.getCategory().equals("cosmetic") || product.getCategory().equals("fnb")) {
                expDate = new SimpleDateFormat("yyyy-MM-dd").parse(product.getDetails().getExpireDate());
            }
        } catch (ParseException e) {
            throw new AppException(400, "Format tanggal kadaluarsa salah");
        }

        // Insert product sesuai category
        switch (product.getCategory()) {
            case "electronic":
                ProductElectronic productElectronic = new ProductElectronic();
                productElectronic.setId(product.getId());
                productElectronic.setName(product.getName());
                productElectronic.setSkuCode(product.getSkuCode());
                productElectronic.setCategory(product.getCategory());
                productElectronic.setMaxStock(product.getMaxStock());
                productElectronic.setCreatedBy(currentUser.getId());
                productElectronic.setType(product.getDetails().getType());

                productRepository.insertProductElectronic(productElectronic);
                break;
            case "cosmetic":
                ProductCosmetic productCosmetic = new ProductCosmetic();
                productCosmetic.setId(product.getId());
                productCosmetic.setName(product.getName());
                productCosmetic.setSkuCode(product.getSkuCode());
                productCosmetic.setCategory(product.getCategory());
                productCosmetic.setMaxStock(product.getMaxStock());
                productCosmetic.setCreatedBy(currentUser.getId());
                productCosmetic.setExpireDate(new java.sql.Date(expDate.getTime()));

                productRepository.insertProductCosmetic(productCosmetic);
                break;
            case "fnb":
                ProductFnb productFnb = new ProductFnb();
                productFnb.setId(product.getId());
                productFnb.setName(product.getName());
                productFnb.setSkuCode(product.getSkuCode());
                productFnb.setCategory(product.getCategory());
                productFnb.setMaxStock(product.getMaxStock());
                productFnb.setCreatedBy(currentUser.getId());
                productFnb.setExpireDate(new java.sql.Date(expDate.getTime()));

                productRepository.insertProductFnb(productFnb);
                break;
            default:
                break;
        }
    }
    public void updateProduct(UpdateProductRequestDto product){
            GetProductResponseDto existingProduct = productRepository.getProductById(product.getId());
            if (existingProduct == null) {
                throw new AppException(404, "Produk tidak ditemukan");
            }
    
            // Update fields
            existingProduct.setName(product.getName());
            existingProduct.setSkuCode(product.getSkuCode());
            existingProduct.setMaxStock(product.getMaxStock());
    
            // Update based on category
            switch (existingProduct.getCategory()) {
                case "electronic":
                    ProductElectronic updatedElectronic = new ProductElectronic();
                    updatedElectronic.setId(existingProduct.getId());
                    updatedElectronic.setName(existingProduct.getProductName());
                    updatedElectronic.setSkuCode(existingProduct.getSkuCode());
                    updatedElectronic.setCategory(existingProduct.getCategory());
                    updatedElectronic.setMaxStock(existingProduct.getMaxStock());
                    updatedElectronic.setType(existingProduct.getDetails().getType());
                    productRepository.updateProductElectronic(updatedElectronic);
                    break;
                case "cosmetic":
                    ProductCosmetic updatedCosmetic = new ProductCosmetic();
                    updatedCosmetic.setId(product.getId());
                    updatedCosmetic.setName(product.getName());
                    updatedCosmetic.setSkuCode(product.getSkuCode());
                    updatedCosmetic.setCategory(product.getCategory());
                    updatedCosmetic.setMaxStock(product.getMaxStock());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date utilDate;
                    try {
                        utilDate = sdf.parse(product.getDetails().getExpireDate());
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
                        updatedCosmetic.setExpireDate(sqlDate); // Pass the java.sql.Date
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } // java.util.Date
                    productRepository.updateProductCosmetic(updatedCosmetic);
                    break;
                case "fnb":
                    ProductFnb updatedFnb = new ProductFnb();
                    updatedFnb.setId(product.getId());
                    updatedFnb.setName(product.getName());
                    updatedFnb.setSkuCode(product.getSkuCode());
                    updatedFnb.setCategory(product.getCategory());
                    updatedFnb.setMaxStock(product.getMaxStock());
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date utilDate2;
                    try {
                        utilDate2 = sdf2.parse(product.getDetails().getExpireDate());
                        java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime()); // Convert to java.sql.Date
                        updatedFnb.setExpireDate(sqlDate2); // Pass the java.sql.Date
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } // java.util.Date
                    productRepository.updateProductFnB(updatedFnb);
                    break;
                default:
                    throw new AppException(400, "Kategori produk tidak valid");
            }
        }
}

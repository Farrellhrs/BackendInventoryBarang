package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.jbdc.DatabaseConnection;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;
import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.repositories.interfaces.ProductRepositoryIf;

public class ProductRepository implements ProductRepositoryIf {

    @Override
    public List<ProductElectronic> getAllProductElectronics(GetProductsRequestDto params) {
        List<ProductElectronic> products = new ArrayList<>();

        String sortAndOrderQuery = "";
        if (params.getSort() != null) {
            if ("stock".equals(params.getSort())) {
                sortAndOrderQuery = "ORDER BY ls.stock " + params.getOrder();
            } else {
                sortAndOrderQuery = "ORDER BY p." + params.getSort() + " " + params.getOrder();
            }
        }
        if (params.getSortByDetail() != null) {
            sortAndOrderQuery = "ORDER BY pe." + params.getSortByDetail() + " " + params.getOrder();
        }

        String searchQuery = "";
        if (params.getName() != null) {
            searchQuery = "AND p.name LIKE ? ";
        }

        String query = "WITH LatestStock AS (" +
                "    SELECT sr.product_id, sr.stock, sr.created_at " +
                "    FROM " + new StockRecord().getTableName() + " sr " +
                "    WHERE sr.product_id IN (SELECT id FROM " + new ProductElectronic().getTableName() +
                "                              WHERE category = 'electronic') " +
                "      AND sr.created_at = (SELECT MAX(sr2.created_at) " +
                "                          FROM " + new StockRecord().getTableName() + " sr2 " +
                "                          WHERE sr2.product_id = sr.product_id)" +
                ") " +
                "SELECT p.id, p.sku_code, p.name, p.category, p.max_stock, p.created_at, p.updated_at, " +
                "       pe.type, ls.stock " +
                "FROM " + new ProductElectronic().getTableName() + " p " +
                "LEFT JOIN " + new ProductElectronic().getSubTableName() + " pe ON pe.product_id = p.id " +
                "LEFT JOIN LatestStock ls ON ls.product_id = p.id " +
                "WHERE p.category = 'electronic' " +
                searchQuery + " " +
                sortAndOrderQuery +
                " LIMIT ? OFFSET ?;";

        System.out.println(query);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            if (params.getName() != null) {
                stmt.setString(1, "%" + params.getName() + "%");
                stmt.setInt(2, params.getLimit());
                stmt.setInt(3, params.getOffset());
            } else {
                stmt.setInt(1, params.getLimit());
                stmt.setInt(2, params.getOffset());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductElectronic product = new ProductElectronic();

                    product.setId(rs.getString("id"));
                    product.setSkuCode(rs.getString("sku_code"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setMaxStock(rs.getInt("max_stock"));
                    product.setStock(rs.getInt("stock"));
                    product.setType(rs.getString("type"));
                    product.setCreatedAt(rs.getDate("created_at"));
                    product.setUpdatedAt(rs.getDate("updated_at"));

                    products.add(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return products;
    }

    @Override
    public List<ProductCosmetic> getAllProductCosmetics(GetProductsRequestDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProductCosmetics'");
    }

    @Override
    public List<ProductFnb> getAllProductFnbs(GetProductsRequestDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProductFnbs'");
    }

    @Override
    public Product getProductById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
    }

    @Override
    public boolean insertProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertProduct'");
    }

    @Override
    public boolean updateProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public boolean deleteProduct(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

    @Override
    public int getTotalData(String productCategory) {
        int totalData = 0;
        String tableName = "";

        switch (productCategory) {
            case "electronic":
                tableName = new ProductElectronic().getTableName();
                break;
            case "cosmetic":
                tableName = new ProductCosmetic().getTableName();
                break;
            case "fnb":
                tableName = new ProductFnb().getTableName();
                break;
            default:
                break;
        }

        String query = "SELECT COUNT(*) AS total_data FROM " + tableName + " WHERE category = ?";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, productCategory);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalData = rs.getInt("total_data");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return totalData;
    }

}

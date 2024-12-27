package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto.ProductDetails;
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
        List<ProductCosmetic> products = new ArrayList<>();

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
                "    WHERE sr.product_id IN (SELECT id FROM " + new ProductCosmetic().getTableName() +
                "                              WHERE category = 'cosmetic') " +
                "      AND sr.created_at = (SELECT MAX(sr2.created_at) " +
                "                          FROM " + new StockRecord().getTableName() + " sr2 " +
                "                          WHERE sr2.product_id = sr.product_id)" +
                ") " +
                "SELECT p.id, p.sku_code, p.name, p.category, p.max_stock, p.created_at, p.updated_at, " +
                "       pc.expire_date, ls.stock " +
                "FROM " + new ProductCosmetic().getTableName() + " p " +
                "LEFT JOIN " + new ProductCosmetic().getSubTableName() + " pc ON pc.product_id = p.id " +
                "LEFT JOIN LatestStock ls ON ls.product_id = p.id " +
                "WHERE p.category = 'cosmetic' " +
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
                    ProductCosmetic product = new ProductCosmetic();

                    product.setId(rs.getString("id"));
                    product.setSkuCode(rs.getString("sku_code"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setMaxStock(rs.getInt("max_stock"));
                    product.setStock(rs.getInt("stock"));
                    product.setExpireDate(rs.getDate("expire_date"));
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
    public List<ProductFnb> getAllProductFnbs(GetProductsRequestDto params) {
        List<ProductFnb> products = new ArrayList<>();

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
                "    WHERE sr.product_id IN (SELECT id FROM " + new ProductFnb().getTableName() +
                "                              WHERE category = 'fnb') " +
                "      AND sr.created_at = (SELECT MAX(sr2.created_at) " +
                "                          FROM " + new StockRecord().getTableName() + " sr2 " +
                "                          WHERE sr2.product_id = sr.product_id)" +
                ") " +
                "SELECT p.id, p.sku_code, p.name, p.category, p.max_stock, p.created_at, p.updated_at, " +
                "       pc.expire_date, ls.stock " +
                "FROM " + new ProductFnb().getTableName() + " p " +
                "LEFT JOIN " + new ProductFnb().getSubTableName() + " pc ON pc.product_id = p.id " +
                "LEFT JOIN LatestStock ls ON ls.product_id = p.id " +
                "WHERE p.category = 'fnb' " +
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
                    ProductFnb product = new ProductFnb();

                    product.setId(rs.getString("id"));
                    product.setSkuCode(rs.getString("sku_code"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setMaxStock(rs.getInt("max_stock"));
                    product.setStock(rs.getInt("stock"));
                    product.setExpireDate(rs.getDate("expire_date"));
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
    public GetProductResponseDto getProductById(String id) {
        GetProductResponseDto products = new GetProductResponseDto();
        String query1 = 
                "WITH LatestStock AS (" +
                "    SELECT sr.product_id, sr.stock, sr.created_at " +
                "    FROM " + new StockRecord().getTableName() + " sr " +
                "    WHERE sr.product_id IN (SELECT id FROM ? WHERE category = 'fnb') " +
                "      AND sr.created_at = (SELECT MAX(sr2.created_at) " +
                "                          FROM " + new StockRecord().getTableName() + " sr2 " +
                "                          WHERE sr2.product_id = sr.product_id)" +
                ") " +
                "SELECT p.id, p.sku_code, p.name, p.category, p.max_stock, p.created_at, p.updated_at, " +
                "       pc.expire_date, ls.stock " +
                "FROM ? p " +
                "LEFT JOIN ? pc ON pc.product_id = p.id " +
                "LEFT JOIN LatestStock ls ON ls.product_id = p.id " +
                "WHERE p.category = 'fnb' " +
                " LIMIT 1;";
        String query2 = 
                "SELECT category" +
                "FROM " + new ProductFnb().getTableName() +
                "WHERE id = " + id +
                " LIMIT 1;";

        System.out.println(query1);
        System.out.println(query2);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query2)) {


            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String category = rs.getString("category");
                    try (Connection connections = DatabaseConnection.connect();
                        PreparedStatement stmts = connections.prepareStatement(query1)) {
            
                    if ("electronic".equals(category)) {
                        stmts.setString(1, new ProductElectronic().getTableName());
                        stmts.setString(2, new ProductElectronic().getTableName());
                        stmts.setString(3, new ProductElectronic().getSubTableName());
                    } else if ("cosmetic".equals(category)){
                        stmts.setString(1, new ProductCosmetic().getTableName());
                        stmts.setString(2, new ProductCosmetic().getTableName());
                        stmts.setString(3, new ProductCosmetic().getSubTableName());
                    } else {
                        stmts.setString(1, new ProductFnb().getTableName());
                        stmts.setString(2, new ProductFnb().getTableName());
                        stmts.setString(3, new ProductFnb().getSubTableName());
                    }

                    try (ResultSet rss = stmts.executeQuery()) {
                        while (rss.next()) {
                            if ("electronic".equals(category)) {
                                products =  new GetProductResponseDto(
                                    rss.getString("id"),
                                    rss.getString("sku_code"),
                                    rss.getString("name"),
                                    rss.getString("category"),
                                    rss.getDate("created_at"),
                                    rss.getInt("stock"),
                                    rss.getInt("max_stock"),
                                    new ProductDetails(null, rs.getDate("expire_date")));
                            } else if ("cosmetic".equals(category)){
                                products =  new GetProductResponseDto(
                                    rss.getString("id"),
                                    rss.getString("sku_code"),
                                    rss.getString("name"),
                                    rss.getString("category"),
                                    rss.getDate("created_at"),
                                    rss.getInt("stock"),
                                    rss.getInt("max_stock"),
                                    new ProductDetails(null, rs.getDate("expire_date")));
                            } else {
                                products =  new GetProductResponseDto(
                                    rss.getString("id"),
                                    rss.getString("sku_code"),
                                    rss.getString("name"),
                                    rss.getString("category"),
                                    rss.getDate("created_at"),
                                    rss.getInt("stock"),
                                    rss.getInt("max_stock"),
                                    new ProductDetails(rss.getString("type"), null));
                            }
                        }
                    }

                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Error(e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return products;
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

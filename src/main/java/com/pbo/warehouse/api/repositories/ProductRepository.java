package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetProductsRequestDto;
import com.pbo.warehouse.api.dto.response.CreatorResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto;
import com.pbo.warehouse.api.dto.response.GetProductResponseDto.ProductDetails;
import com.pbo.warehouse.api.exceptions.AppException;
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
        GetProductResponseDto product = new GetProductResponseDto();

        String query = "SELECT p.*, sr.stock, u.name AS user_name, u.email FROM products p JOIN users u ON p.created_by = u.id LEFT JOIN (SELECT product_id, stock, created_at FROM stock_records WHERE product_id = ? ORDER BY created_at DESC LIMIT 1) sr ON sr.product_id = p.id WHERE p.id = ?;";

        System.out.println(query);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, id);
            stmt.setString(2, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    product = new GetProductResponseDto(
                            rs.getString("id"),
                            rs.getString("sku_code"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDate("created_at"),
                            rs.getInt("stock"),
                            rs.getInt("max_stock"),
                            null);

                    CreatorResponseDto user = new CreatorResponseDto(
                            rs.getString("user_name"),
                            rs.getString("email"));

                    product.setCreatedBy(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        String category = product.getCategory();
        String queryDetails = "";

        switch (category) {
            case "electronic":
                queryDetails = "SELECT * FROM " + new ProductElectronic().getSubTableName() + " WHERE product_id = ?;";
                break;
            case "cosmetic":
                queryDetails = "SELECT * FROM " + new ProductCosmetic().getSubTableName() + " WHERE product_id = ?;";
                break;
            case "fnb":
                queryDetails = "SELECT * FROM " + new ProductFnb().getSubTableName() + " WHERE product_id = ?;";
                break;
            default:
                break;
        }

        System.out.println(queryDetails);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(queryDetails)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDetails details = new ProductDetails();

                    switch (category) {
                        case "electronic":
                            details.setType(rs.getString("type"));
                            break;
                        case "cosmetic":
                            details.setExpireDate(rs.getDate("expire_date"));
                            break;
                        case "fnb":
                            details.setExpireDate(rs.getDate("expire_date"));
                            break;
                        default:
                            break;
                    }

                    product.setDetails(details);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return product;
    }

    @Override
    public void insertProductElectronic(ProductElectronic product) {
        // Insert ke parent product
        this.insertProduct(product);

        // Insert ke child product
        String query = "INSERT INTO " + product.getSubTableName()
                + " (product_id, type) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, product.getId());
            stmt.setString(2, product.getType());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal insert product");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public void insertProductCosmetic(ProductCosmetic product) {
        // Insert ke parent product
        this.insertProduct(product);

        // Insert ke child product
        String query = "INSERT INTO " + product.getSubTableName()
                + " (product_id, expire_date) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, product.getId());
            stmt.setDate(2, product.getExpireDate());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal insert product");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public void insertProductFnb(ProductFnb product) {
        // Insert ke parent product
        this.insertProduct(product);

        // Insert ke child product
        String query = "INSERT INTO " + product.getSubTableName()
                + " (product_id, expire_date) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, product.getId());
            stmt.setDate(2, product.getExpireDate());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal insert product");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
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

    private void insertProduct(Product product) {
        String sql = "INSERT INTO products (id, sku_code, name, category, max_stock, created_by) VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("user id:" + product.getCreatedBy());

        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, product.getId());
            stmt.setString(2, product.getSkuCode());
            stmt.setString(3, product.getName());
            stmt.setString(4, product.getCategory());
            stmt.setInt(5, product.getMaxStock());
            stmt.setString(6, product.getCreatedBy());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal insert product");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }
}

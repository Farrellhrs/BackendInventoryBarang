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
import com.pbo.warehouse.api.models.interfaces.ProductExpireable;
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
    public void updateProductElectronic(ProductElectronic product) {

        // product.getSubTableName()
        String query = "UPDATE product_electronics SET type = ? WHERE product_id = ?;";
        System.out.println(query);
        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            System.out.println(product.getType() + " " + product.getId());
            stmt.setString(1, product.getType());
            stmt.setString(2, product.getId());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("execute query");
            while (rowsAffected == 0) {
                query = "INSERT INTO product_electronics (product_id, type) VALUES (?, ?);";
                System.out.println(query);

                try (Connection connection2 = DatabaseConnection.connect();
                        PreparedStatement stmt2 = connection2.prepareStatement(query)) {
                    stmt2.setString(1, product.getId());
                    stmt2.setString(2, product.getType());
                    rowsAffected = stmt2.executeUpdate();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    throw new AppException(500, e.getMessage());
                }
            }
            System.out.println("finish");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public void updateProductFnB(ProductFnb product) {
        String query = "UPDATE product_fnbs SET expire_date = ? WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDate(1, product.getExpireDate());
            stmt.setString(2, product.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal memperbarui produk elektronik");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public void updateProductCosmetic(ProductCosmetic product) {
        String query = "UPDATE product_cosmetics SET expire_date = ? WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDate(1, product.getExpireDate());
            stmt.setString(2, product.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal memperbarui produk elektronik");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public void updateProduct(Product product) {
        System.out.println("start update product parent");
        String query = "UPDATE products SET name = ?, max_stock = ? WHERE id = ?;";
        System.out.println(query);
        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getMaxStock());
            stmt.setString(3, product.getId());
            System.out.println(product.getId());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("execute products");
            if (rowsAffected == 0) {
                throw new AppException(500, "Gagal memperbarui produk elektronik");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new AppException(500, e.getMessage());
        }
    }

    @Override
    public boolean deleteProduct(String productId, String category) {
        String deleteCategoryQuery;
        switch (category.toLowerCase()) {
            case "fnb":
                deleteCategoryQuery = "DELETE FROM " + new ProductFnb().getSubTableName() + " WHERE product_id = ?";
                break;
            case "cosmetic":
                deleteCategoryQuery = "DELETE FROM " + new ProductCosmetic().getSubTableName()
                        + " WHERE product_id = ?";
                break;
            case "electronic":
                deleteCategoryQuery = "DELETE FROM " + new ProductElectronic().getSubTableName()
                        + " WHERE product_id = ?";
                break;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }

        String deleteProductQuery = "DELETE FROM products WHERE id = ?";

        String deleteInOutRecordQuery = "DELETE FROM in_out_records WHERE product_id = ?";

        String deleteStockRecordQuery = "DELETE FROM stock_records WHERE product_id = ?";

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement categoryStmt = conn.prepareStatement(deleteCategoryQuery);
                PreparedStatement productStmt = conn.prepareStatement(deleteProductQuery);
                PreparedStatement inOutRecordStmt = conn.prepareStatement(deleteInOutRecordQuery);
                PreparedStatement stockRecordStmt = conn.prepareStatement(deleteStockRecordQuery)) {

            conn.setAutoCommit(false);

            inOutRecordStmt.setString(1, productId);
            inOutRecordStmt.executeUpdate();

            stockRecordStmt.setString(1, productId);
            stockRecordStmt.executeUpdate();

            categoryStmt.setString(1, productId);
            categoryStmt.executeUpdate();

            productStmt.setString(1, productId);
            int productRows = productStmt.executeUpdate();

            if (productRows > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getTotalData(String productCategory) {
        int totalData = 0;

        String query = "SELECT COUNT(*) AS total_data FROM products WHERE category = ?";

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

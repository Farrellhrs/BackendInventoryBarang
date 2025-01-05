package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.jbdc.DatabaseConnection;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;

import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.interfaces.InOutRecordRepositoryIf;

public class InOutRecordRepository implements InOutRecordRepositoryIf {

    @Override
    public int getTotalData(GetAllInOutRequestDto params) {
        int totalData = 0;

        StringBuilder queryBuilder = new StringBuilder(
                "SELECT COUNT(*) FROM in_out_records io JOIN products p ON p.id = io.product_id WHERE io.type = ?");

        // Add category condition if specified
        if (params.getCategory() != null && !params.getCategory().isEmpty()) {
            queryBuilder.append(" AND p.category = ?");
        }

        // Add date condition if start or end dates are provided
        if (params.getStartDate() != null && params.getEndDate() != null) {
            queryBuilder.append(" AND io.record_date BETWEEN ? AND ?");
        } else if (params.getStartDate() != null) {
            queryBuilder.append(" AND io.record_date >= ?");
        } else if (params.getEndDate() != null) {
            queryBuilder.append(" AND io.record_date <= ?");
        }

        queryBuilder.append(" LIMIT ? OFFSET ?;");

        String query = queryBuilder.toString();
        System.out.println(query);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            int index = 1;
            stmt.setString(index++, params.getType()); // Set the type parameter

            // If category is provided, set it as a parameter
            if (params.getCategory() != null && !params.getCategory().isEmpty()) {
                stmt.setString(index++, params.getCategory());
            }

            // Set start and end dates as needed
            if (params.getStartDate() != null && params.getEndDate() != null) {
                stmt.setDate(index++, new java.sql.Date(params.getStartDate().getTime()));
                stmt.setDate(index++, new java.sql.Date(params.getEndDate().getTime()));
            } else if (params.getStartDate() != null) {
                stmt.setDate(index++, new java.sql.Date(params.getStartDate().getTime()));
            } else if (params.getEndDate() != null) {
                stmt.setDate(index++, new java.sql.Date(params.getEndDate().getTime()));
            }

            stmt.setInt(index++, params.getLimit()); // Set limit
            stmt.setInt(index, params.getOffset()); // Set offset

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalData = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return totalData;
    }

    @Override
    public List<InOutRecord> getAllRecords(GetAllInOutRequestDto params) {
        List<InOutRecord> inouts = new ArrayList<>();

        // Dynamically adjust the query based on the presence of start and end dates
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT io.*, p.name, p.sku_code, p.category " +
                        "FROM in_out_records io " +
                        "JOIN products p ON p.id = io.product_id " +
                        "WHERE io.type = ?");

        // Add category condition if specified
        if (params.getCategory() != null && !params.getCategory().isEmpty()) {
            queryBuilder.append(" AND p.category = ?");
        }

        // Add date condition if start or end dates are provided
        if (params.getStartDate() != null && params.getEndDate() != null) {
            queryBuilder.append(" AND io.record_date BETWEEN ? AND ?");
        } else if (params.getStartDate() != null) {
            queryBuilder.append(" AND io.record_date >= ?");
        } else if (params.getEndDate() != null) {
            queryBuilder.append(" AND io.record_date <= ?");
        }

        // Add sorting and pagination
        List<String> inOutColumns = List.of("quantity", "record_date");
        if (inOutColumns.contains(params.getSort())) {
            queryBuilder.append(" ORDER BY io.");
        } else {
            queryBuilder.append(" ORDER BY p.");
        }

        queryBuilder.append(params.getSort()).append(" ").append(params.getOrder())
                .append(" LIMIT ? OFFSET ?;");

        String query = queryBuilder.toString();
        System.out.println(query);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            int index = 1;
            stmt.setString(index++, params.getType()); // Set the type parameter

            // If category is provided, set it as a parameter
            if (params.getCategory() != null && !params.getCategory().isEmpty()) {
                stmt.setString(index++, params.getCategory());
            }

            // Set start and end dates as needed
            if (params.getStartDate() != null && params.getEndDate() != null) {
                stmt.setDate(index++, new java.sql.Date(params.getStartDate().getTime()));
                stmt.setDate(index++, new java.sql.Date(params.getEndDate().getTime()));
            } else if (params.getStartDate() != null) {
                stmt.setDate(index++, new java.sql.Date(params.getStartDate().getTime()));
            } else if (params.getEndDate() != null) {
                stmt.setDate(index++, new java.sql.Date(params.getEndDate().getTime()));
            }

            stmt.setInt(index++, params.getLimit()); // Set limit
            stmt.setInt(index, params.getOffset()); // Set offset

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InOutRecord inout = new InOutRecord();

                    inout.setId(rs.getInt("id"));
                    inout.setProductId(rs.getString("product_id"));
                    inout.setType(rs.getString("type"));
                    inout.setQuantity(rs.getInt("quantity"));
                    inout.setRecordDate(rs.getDate("record_date"));

                    // Handle different categories dynamically
                    String categoryResult = rs.getString("category");
                    switch (categoryResult) {
                        case "electronic":
                            ProductElectronic elec = new ProductElectronic();
                            elec.setSkuCode(rs.getString("sku_code"));
                            elec.setName(rs.getString("name"));
                            elec.setCategory(categoryResult);
                            inout.setProductElectronic(elec);
                            break;
                        case "cosmetic":
                            ProductCosmetic cosm = new ProductCosmetic();
                            cosm.setSkuCode(rs.getString("sku_code"));
                            cosm.setName(rs.getString("name"));
                            cosm.setCategory(categoryResult);
                            inout.setProductCosmetic(cosm);
                            break;
                        case "fnb":
                            ProductFnb fnb = new ProductFnb();
                            fnb.setSkuCode(rs.getString("sku_code"));
                            fnb.setName(rs.getString("name"));
                            fnb.setCategory(categoryResult);
                            inout.setProductFnb(fnb);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid product category");
                    }

                    inouts.add(inout);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return inouts;
    }

    @Override
    public InOutRecord getRecordById(int id) {
        /*
         * TODO: implement this logics
         * - query hint: join in_out_records, products, stock_records, and users table
         * - return InOutRecord
         */
        InOutRecord inout = new InOutRecord();
        ProductElectronic productElectronic = new ProductElectronic();
        ProductCosmetic productCosmetic = new ProductCosmetic();
        ProductFnb productFnb = new ProductFnb();
        int lastStock = 0;

        String query = "SELECT io.id, io.product_id, io.quantity, io.type, io.record_date, io.created_by, p.sku_code, p.name, p.category, p.max_stock, p.created_at, u.name AS user_name, u.email FROM in_out_records io JOIN products p ON p.id = io.product_id JOIN users u ON p.created_by = u.id WHERE io.id = ?;";

        System.out.println(query);

        String productCategory = "";
        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inout = new InOutRecord();

                    User creator = new User();
                    creator.setId(rs.getString("created_by"));
                    creator.setName(rs.getString("user_name"));
                    creator.setEmail(rs.getString("email"));

                    inout.setCreatedBy(rs.getString("created_by"));
                    inout.setCreator(creator);

                    inout.setId(rs.getInt("id"));
                    inout.setProductId(rs.getString("product_id"));
                    inout.setQuantity(rs.getInt("quantity"));
                    inout.setType(rs.getString("type"));
                    inout.setRecordDate(rs.getDate("record_date"));

                    productCategory = rs.getString("category");

                    String queryLastStock = "SELECT stock FROM stock_records WHERE product_id = ? ORDER BY record_date DESC LIMIT 1;";

                    try (PreparedStatement stmtLs = connection.prepareStatement(queryLastStock)) {
                        stmtLs.setString(1, inout.getProductId());

                        try (ResultSet rsLs = stmtLs.executeQuery()) {
                            if (rsLs.next()) {
                                lastStock = rsLs.getInt("stock");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new Error(e.getMessage());
                    }

                    String queryDetails = "";

                    switch (productCategory) {
                        case "electronic":
                            queryDetails = "SELECT * FROM " + new ProductElectronic().getSubTableName()
                                    + " WHERE product_id = ?";
                            break;
                        case "cosmetic":
                            queryDetails = "SELECT * FROM " + new ProductCosmetic().getSubTableName()
                                    + " WHERE product_id = ?";
                            break;
                        case "fnb":
                            queryDetails = "SELECT * FROM " + new ProductFnb().getSubTableName()
                                    + " WHERE product_id = ?";
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid product category");
                    }

                    System.out.println(queryDetails);

                    try (PreparedStatement stmt2 = connection.prepareStatement(queryDetails)) {

                        stmt2.setString(1, rs.getString("product_id"));

                        try (ResultSet rs2 = stmt2.executeQuery()) {
                            while (rs2.next()) {
                                switch (productCategory) {
                                    case "electronic":
                                        productElectronic.setType(rs2.getString("type"));
                                        break;
                                    case "cosmetic":
                                        productCosmetic.setExpireDate(rs2.getDate("expire_date"));
                                        break;
                                    case "fnb":
                                        productFnb.setExpireDate(rs2.getDate("expire_date"));
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Error(e.getMessage());
                    }

                    switch (productCategory) {
                        case "electronic":
                            productElectronic.setSkuCode(rs.getString("sku_code"));
                            productElectronic.setName(rs.getString("name"));
                            productElectronic.setCategory(rs.getString("category"));
                            productElectronic.setMaxStock(rs.getInt("max_stock"));
                            productElectronic.setStock(lastStock);

                            inout.setProductElectronic(productElectronic);
                            break;
                        case "cosmetic":
                            productCosmetic.setSkuCode(rs.getString("sku_code"));
                            productCosmetic.setName(rs.getString("name"));
                            productCosmetic.setCategory(rs.getString("category"));
                            productCosmetic.setMaxStock(rs.getInt("max_stock"));
                            productCosmetic.setStock(lastStock);

                            inout.setProductCosmetic(productCosmetic);
                            break;
                        case "fnb":
                            productFnb.setSkuCode(rs.getString("sku_code"));
                            productFnb.setName(rs.getString("name"));
                            productFnb.setCategory(rs.getString("category"));
                            productFnb.setMaxStock(rs.getInt("max_stock"));
                            productFnb.setStock(lastStock);

                            inout.setProductFnb(productFnb);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return inout;
    }

    @Override
    public void insertRecord(InOutRecord record) {
        String sql = "INSERT INTO in_out_records (product_id, quantity, record_date, type) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, record.getProductId());
            stmt.setInt(2, record.getQuantity());
            stmt.setDate(3, new java.sql.Date(record.getRecordDate().getTime())); // Convert java.util.Date to
                                                                                  // java.sql.Date
            stmt.setString(4, record.getType());

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
    public void updateRecord(InOutRecord record) {
        String sql = "UPDATE in_out_records SET product_id = ?, quantity = ?, record_date = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, record.getProductId());
            stmt.setInt(2, record.getQuantity());
            stmt.setDate(3, record.getRecordDate());
            stmt.setInt(4, record.getId());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Check if any rows were updated
            if (rowsAffected == 0) {
                throw new AppException(404, "Failed to update record: Record not found");
            }
        } catch (SQLException e) {
            // Handle SQL exception and throw a more specific exception
            e.printStackTrace();
            throw new RuntimeException("Error updating record in database", e);
        }
    }

    @Override
    public boolean deleteRecord(int id) {
        String sql = "DELETE FROM in_out_records WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // If rows are affected, the deletion was successful
        } catch (SQLException e) {
            throw new AppException(500, "Error deleting record: " + e.getMessage());
        }
    }

    // ------------buat table stock_record------------------

    @Override
    public StockRecord getStockRecordByDateAndProductId(Date recordDate, String productId) {
        String sql = "SELECT * FROM stock_records WHERE record_date = ? AND product_id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(recordDate.getTime()));
            stmt.setString(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new StockRecord(
                            rs.getInt("id"),
                            rs.getString("product_id"),
                            rs.getInt("stock"),
                            rs.getDate("record_date"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching stock record", e);
        }
        return null; // Jika tidak ditemukan
    }

    @Override
    public void insertStockRecord(StockRecord stockRecord) {
        String sql = "INSERT INTO stock_records (product_id, stock, record_date) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, stockRecord.getProductId());
            stmt.setInt(2, stockRecord.getStock());
            stmt.setDate(3, new java.sql.Date(stockRecord.getRecordDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting stock record", e);
        }
    }

    @Override
    public void updateStockRecord(int stock, Date recordDate, String productId) {
        String sql = "UPDATE stock_records SET stock = ? " +
                "WHERE record_date = ? AND product_id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, stock);
            stmt.setDate(2, new java.sql.Date(recordDate.getTime()));
            stmt.setString(3, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating stock record", e);
        }
    }

    @Override
    public List<InOutRecord> getRecordsByPeriod(int year, int month) {
        List<InOutRecord> results = new ArrayList<>();
        String query = "SELECT io.*, p.category FROM in_out_records io JOIN products p ON p.id = io.product_id WHERE YEAR(record_date) = ? AND MONTH(record_date) = ?";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InOutRecord record = new InOutRecord();
                    record.setId(rs.getInt("id"));
                    record.setProductId(rs.getString("product_id"));
                    record.setQuantity(rs.getInt("quantity"));
                    record.setType(rs.getString("type"));
                    record.setRecordDate(rs.getDate("record_date"));

                    Product product = new Product() {
                        {
                            setId(rs.getString("product_id"));
                            setCategory(rs.getString("category"));
                        }
                    };
                    record.setProduct(product);

                    results.add(record);
                }
            }

            return results;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }
    }
}

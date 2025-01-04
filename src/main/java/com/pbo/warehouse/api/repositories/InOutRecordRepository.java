package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.dto.response.CreatorResponseDto;
import com.pbo.warehouse.api.dto.response.GetInOutResponseDto;
import com.pbo.warehouse.api.dto.response.ProductDetailsResponseDto;
import com.pbo.warehouse.api.jbdc.DatabaseConnection;
import com.pbo.warehouse.api.models.InOutRecord;
import com.pbo.warehouse.api.models.ProductCosmetic;
import com.pbo.warehouse.api.models.ProductElectronic;
import com.pbo.warehouse.api.models.ProductFnb;

import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.repositories.interfaces.InOutRecordRepositoryIf;

public class InOutRecordRepository implements InOutRecordRepositoryIf {

    @Override
    public int getTotalData(String productCategory) {
        /*
         * TODO: implement this logics
         * - query hint: use count, join in_out with product table, and group by
         * - if productCategory is null, return total data of all products
         * - if productCategory is either 'electronic', 'cosmetic', or 'fnb', return total data of products with the specified category
         * - if productCategory is not null and not 'electronic', 'cosmetic', or 'fnb', return 0
         * - if there is an exception, return -1
         */

        int totalData = 0;
        String query = "SELECT COUNT(*) AS total_data FROM in_out_records";

        if (productCategory != null){
            query = query + " WHERE category = ?";
        }
        

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            if (productCategory != null){
                stmt.setString(1, productCategory);
            }

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

    @Override
    public List<InOutRecord> getAllRecords(GetAllInOutRequestDto params) {
        List<InOutRecord> inouts = new ArrayList<>();

        String typeInOut = "";
        if (params.getSort() != null) {
            typeInOut = "io.type = " + params.getType();
        }

        String sortAndOrderQuery = "";
        if (params.getSort() != null) {
            if ("stock".equals(params.getSort())) {
                sortAndOrderQuery = "ORDER BY ls.stock " + params.getOrder();
            } else {
                sortAndOrderQuery = "ORDER BY p." + params.getSort() + " " + params.getOrder();
            }
        }

        String filterDate = "";
        if (params.getStartDate() != null && params.getEndDate() != null){
            filterDate = "record_date BETWEEN " + params.getStartDate() + " AND " + params.getEndDate();
        }

        String filterCategory = "";
        if (params.getCategory() != null) {
            filterCategory = "p.category = " + params.getCategory();
        }

        String query = "WITH LatestStock AS (" +
                "    SELECT sr.product_id, sr.stock, sr.created_at " +
                "    FROM " + new StockRecord().getTableName() + " sr " +
                "    WHERE sr.product_id IN (SELECT id FROM products" +
                "      AND sr.created_at = (SELECT MAX(sr2.created_at) " +
                "                          FROM " + new StockRecord().getTableName() + " sr2 " +
                "                          WHERE sr2.product_id = sr.product_id)" +
                ") " +
                "SELECT io.id, io.quantity, io.type, io.record_date p.id as pId, p.sku_code, p.name, p.category, p.max_stock, p.created_at, p.updated_at, ls.stock " +
                "c.expire_date as cexpire_date, f.expire_date as fexpire_date, e.type as eType " + 
                "FROM in_out_records io " + 
                "LEFT JOIN products p ON p.id = io.product_id " +
                "LEFT JOIN " + new ProductCosmetic().getSubTableName() + " c ON c.product_id = p.id " +
                "LEFT JOIN " + new ProductElectronic().getSubTableName() + " e ON e.product_id = p.id " +
                "LEFT JOIN " + new ProductFnb().getSubTableName() + " f ON f.product_id = p.id " +
                "LEFT JOIN LatestStock ls ON ls.product_id = p.id " +
                "WHERE " + 
                typeInOut + " " +
                filterCategory + " " +
                filterDate + " " +
                sortAndOrderQuery +
                " LIMIT ? OFFSET ?;";

        System.out.println(query);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, params.getLimit());
            stmt.setInt(2, params.getOffset());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InOutRecord inout = new InOutRecord();
            
                    inout.setId(rs.getInt("id"));
                    inout.setProductId(rs.getString("pId"));
                    inout.setType(rs.getString("type"));
                    inout.setQuantity(rs.getInt("quantity"));
                    inout.setRecordDate(rs.getDate("record_date"));

                    switch (rs.getString("category")) {
                        case "electronic":
                            ProductElectronic elec = new ProductElectronic();
                            elec.setSkuCode(rs.getString("sku_code"));
                            elec.setName(rs.getString("name"));
                            elec.setCategory(rs.getString("category"));
                            elec.setMaxStock(rs.getInt("max_stock"));
                            elec.setStock(rs.getInt("stock"));
                            elec.setType(rs.getString("type"));
                            elec.setCreatedAt(rs.getDate("created_at"));
                            elec.setUpdatedAt(rs.getDate("updated_at"));
                            inout.setProductElectronic(elec);
                            break;
                        case "cosmetic":
                            ProductCosmetic cosm = new ProductCosmetic();
                            cosm.setSkuCode(rs.getString("sku_code"));
                            cosm.setName(rs.getString("name"));
                            cosm.setCategory(rs.getString("category"));
                            cosm.setMaxStock(rs.getInt("max_stock"));
                            cosm.setStock(rs.getInt("stock"));
                            cosm.setExpireDate(rs.getDate("cexpire_date"));
                            cosm.setCreatedAt(rs.getDate("created_at"));
                            cosm.setUpdatedAt(rs.getDate("updated_at"));
                            inout.setProductCosmetic(cosm);
                            break;
                        case "fnb":
                            ProductFnb fnb = new ProductFnb();
                            fnb.setSkuCode(rs.getString("sku_code"));
                            fnb.setName(rs.getString("name"));
                            fnb.setCategory(rs.getString("category"));
                            fnb.setMaxStock(rs.getInt("max_stock"));
                            fnb.setStock(rs.getInt("stock"));
                            fnb.setExpireDate(rs.getDate("fexpire_date"));
                            fnb.setCreatedAt(rs.getDate("created_at"));
                            fnb.setUpdatedAt(rs.getDate("updated_at"));
                            inout.setProductFnb(fnb);
                            break;
                        default:
                            break;
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
    public GetInOutResponseDto getRecordById(int id) {
        /*
         * TODO: implement this logics
         * - query hint: join in_out_records, products, stock_records, and users table
         * - return InOutRecord
         */
        GetInOutResponseDto inout = new GetInOutResponseDto();

        String query =
        "SELECT io.id, io.quantity, io.type, io.record_date p.id as pId, p.sku_code, p.name, p.category, p.max_stock, p.created_at, p.updated_at, sr.stock " +
                "FROM in_out_records io " + 
                "LEFT JOIN products p ON p.id = io.product_id " +
                "JOIN users u ON p.created_by = u.id " +
                "LEFT JOIN stock_records sr ON sr.product_id = p.id WHERE p.id = ?;";
                
        System.out.println(query);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setInt(2, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inout = new GetInOutResponseDto(
                            rs.getInt("id"),
                            rs.getString("pId"),
                            rs.getString("name"),
                            rs.getString("sku_code"),
                            rs.getString("category"),
                            rs.getInt("quantity"),
                            rs.getInt("stock"),
                            rs.getInt("max_stock"),
                            rs.getDate("record_date"),
                            null);

                    CreatorResponseDto user = new CreatorResponseDto(
                            rs.getString("user_name"),
                            rs.getString("email"));

                    inout.setCreatedBy(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        String category = inout.getCategory();
        String queryDetails = "";

        switch (category) {
            case "electronic":
                queryDetails = "SELECT * FROM " + new ProductElectronic().getSubTableName() + " WHERE product_id = " + inout.getProductId() + ";";
                break;
            case "cosmetic":
                queryDetails = "SELECT * FROM " + new ProductCosmetic().getSubTableName() + " WHERE product_id = " + inout.getProductId() + ";";
                break;
            case "fnb":
                queryDetails = "SELECT * FROM " + new ProductFnb().getSubTableName() + " WHERE product_id = " + inout.getProductId() + ";";
                break;
            default:
                break;
        }

        System.out.println(queryDetails);

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(queryDetails)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductDetailsResponseDto details = new ProductDetailsResponseDto();

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

                    inout.setDetails(details);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return inout;
    }

    @Override
    public InOutRecord getRecordByDateAndProductId(Date date, String productId) {
        /*
         * TODO: implement this logics
         * - query hint: select * from in_out_records where record_date = ? and product_id = ?
         * - return InOutRecord
         */
        throw new UnsupportedOperationException("Unimplemented method 'getRecordByDateAndProductId'");
    }

    @Override
    public void insertRecord(InOutRecord record) {
        String sql = "INSERT INTO in_out_records (product_id, quantity, record_date, type) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, record.getProductId());
            stmt.setInt(2, record.getQuantity());
            stmt.setDate(3, new java.sql.Date(record.getRecordDate().getTime())); // Convert java.util.Date to java.sql.Date
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
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, record.getProductId());
            stmt.setInt(2, record.getQuantity());
            stmt.setDate(3, new java.sql.Date(record.getRecordDate().getTime()));
            stmt.setInt(4, record.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AppException(404, "Failed to update record: Record not found");
            }
        } catch (SQLException e) {
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

    //------------buat table stock_record------------------

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
                        rs.getDate("record_date")
                    );
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
        String sql = "UPDATE stock_records SET stock = stock + ? " +
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
    public void updateCumulativeStocks(String productId, Date recordDate, int delta) {
        String sql = "UPDATE stock_records SET stock = stock + ? " +
                     "WHERE product_id = ? AND record_date > ?";
        try (Connection connection = DatabaseConnection.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, delta);
            stmt.setString(2, productId);
            stmt.setDate(3, new java.sql.Date(recordDate.getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating cumulative stocks", e);
        }
    }
    
    // ------------b

}

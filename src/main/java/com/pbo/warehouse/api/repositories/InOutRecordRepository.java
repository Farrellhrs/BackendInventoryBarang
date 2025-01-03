package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.pbo.warehouse.api.dto.request.GetAllInOutRequestDto;
import com.pbo.warehouse.api.exceptions.AppException;
import com.pbo.warehouse.api.jbdc.DatabaseConnection;
import com.pbo.warehouse.api.models.InOutRecord;
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

        throw new UnsupportedOperationException("Unimplemented method 'getTotalData'");
    }

    @Override
    public List<InOutRecord> getAllRecords(GetAllInOutRequestDto params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRecords'");
    }

    @Override
    public InOutRecord getRecordById(int id) {
        /*
         * TODO: implement this logics
         * - query hint: join in_out_records, products, stock_records, and users table
         * - return InOutRecord
         */
        throw new UnsupportedOperationException("Unimplemented method 'getRecordById'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRecord'");
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

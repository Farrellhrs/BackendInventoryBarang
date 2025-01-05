package com.pbo.warehouse.api.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pbo.warehouse.api.jbdc.DatabaseConnection;
import com.pbo.warehouse.api.models.Product;
import com.pbo.warehouse.api.models.StockRecord;
import com.pbo.warehouse.api.repositories.interfaces.StockRecordRepositoryIf;

public class StockRecordRepository implements StockRecordRepositoryIf {

    @Override
    public List<StockRecord> getRecordByPeriod(int year, int month) {
        List<StockRecord> results = new ArrayList<>();

        String query = "SELECT sr.*, p.category FROM stock_records sr JOIN products p ON p.id = sr.product_id WHERE YEAR(record_date) = ? AND MONTH(record_date) = ? ORDER BY record_date ASC";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StockRecord record = new StockRecord();
                    record.setId(rs.getInt("id"));
                    record.setProductId(rs.getString("product_id"));
                    record.setStock(rs.getInt("stock"));
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

    @Override
    public StockRecord getRecordBeforePeriod(int year, int month, String productCategory) {
        StockRecord result = null;

        String query = "SELECT sr.*, p.category FROM stock_records sr JOIN products p ON p.id = sr.product_id WHERE record_date < ? AND p.category = ? ORDER BY record_date DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(year + "-" + month + "-01"));
            stmt.setString(2, productCategory);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = new StockRecord();
                    result.setId(rs.getInt("id"));
                    result.setProductId(rs.getString("product_id"));
                    result.setStock(rs.getInt("stock"));
                    result.setRecordDate(rs.getDate("record_date"));

                    Product product = new Product() {
                        {
                            setId(rs.getString("product_id"));
                            setCategory(rs.getString("category"));
                        }
                    };

                    result.setProduct(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return result;
    }

    @Override
    public StockRecord getLastRecordByCategory(String productCategory) {
        StockRecord result = null;

        String query = "SELECT sr.*, p.category FROM stock_records sr JOIN products p ON p.id = sr.product_id WHERE p.category = ? ORDER BY record_date DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, productCategory);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = new StockRecord();
                    result.setId(rs.getInt("id"));
                    result.setProductId(rs.getString("product_id"));
                    result.setStock(rs.getInt("stock"));
                    result.setRecordDate(rs.getDate("record_date"));

                    Product product = new Product() {
                        {
                            setId(rs.getString("product_id"));
                            setCategory(rs.getString("category"));
                        }
                    };

                    result.setProduct(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return result;
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

    @Override
    public StockRecord getLastRecordBeforeDate(String productId, Date recordDate) {
        StockRecord result = null;

        String query = "SELECT * FROM stock_records WHERE product_id = ? AND record_date < ? ORDER BY record_date DESC LIMIT 1";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, productId);
            stmt.setDate(2, recordDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = new StockRecord();
                    result.setId(rs.getInt("id"));
                    result.setProductId(rs.getString("product_id"));
                    result.setStock(rs.getInt("stock"));
                    result.setRecordDate(rs.getDate("record_date"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return result;
    }
}

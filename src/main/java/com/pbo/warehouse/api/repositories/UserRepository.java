/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.warehouse.api.repositories;

import com.pbo.warehouse.api.jbdc.DatabaseConnection;
import com.pbo.warehouse.api.models.User;
import com.pbo.warehouse.api.repositories.interfaces.UserRepositoryIf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author dika-mac
 */

public class UserRepository implements UserRepositoryIf {
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user = new User();

        String query = "SELECT * FROM " + user.getTableName();

        // try-with-resources automatically close database connection when done
        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                user = new User(
                        rs.getString("name"),
                        rs.getString("email"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = new User();

        String query = "SELECT id, name, email, password FROM " + user.getTableName() + " WHERE email = ?";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"));
                } else {
                    user = null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        return user;
    }

    @Override
    public boolean addUser(User user) {
        String query = "INSERT INTO " + user.getTableName() + " (id, name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE " + user.getTableName() + " SET name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.connect();
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getId());

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

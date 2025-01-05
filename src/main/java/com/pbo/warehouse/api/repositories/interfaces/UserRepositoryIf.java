package com.pbo.warehouse.api.repositories.interfaces;

import java.util.List;

import com.pbo.warehouse.api.models.User;

public interface UserRepositoryIf {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    boolean addUser(User user);

    boolean updateUser(User user);
}
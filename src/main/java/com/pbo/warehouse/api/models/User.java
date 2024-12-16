/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.warehouse.api.models;

/**
 *
 * @author dika-mac
 */
public class User extends Model {
    private String id;
    private String name;
    private String email;
    private String password;

    public User() {
        super("users");
    }

    public User(String name, String email) {
        super("users");
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String password) {
        super("users");
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    public void setId() {
        // generate uuid
        this.id = java.util.UUID.randomUUID().toString();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param email the email to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

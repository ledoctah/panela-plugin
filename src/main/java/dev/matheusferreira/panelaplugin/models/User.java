package dev.matheusferreira.panelaplugin.models;

import java.sql.Date;

public class User {
    int id;

    Date createdAt;

    String name;

    String password;

    public User(int id, Date createdAt, String name, String password) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

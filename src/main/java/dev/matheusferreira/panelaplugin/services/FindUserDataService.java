package dev.matheusferreira.panelaplugin.services;

import dev.matheusferreira.panelaplugin.database.DatabaseConnection;
import dev.matheusferreira.panelaplugin.models.User;

import java.io.IOException;
import java.sql.*;

public class FindUserDataService {
    DatabaseConnection databaseConnection;

    public FindUserDataService() {
        this.databaseConnection = new DatabaseConnection();
    }

    public User execute(String playerName) {
        try {
            Connection connection = this.databaseConnection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
            "SELECT id, created_at, name, password FROM users_login WHERE name = ?"
            );

            preparedStatement.setString(1, playerName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }

            int id = resultSet.getInt(1);
            Date createdAt = resultSet.getDate(2);
            String name = resultSet.getString(3);
            String password = resultSet.getString(4);

            return new User(id, createdAt, name, password);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package dev.matheusferreira.panelaplugin.services;

import dev.matheusferreira.panelaplugin.database.DatabaseConnection;
import dev.matheusferreira.panelaplugin.utils.HashProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticateUserService {
    DatabaseConnection databaseConnection;

    public AuthenticateUserService() {
        this.databaseConnection = new DatabaseConnection();
    }

    public void execute(String name, String password) throws SQLException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Connection connection = this.databaseConnection.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, password FROM users_login WHERE name = ?");

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(!resultSet.next()) {
            throw new Error("User not registered");
        }

        HashProvider hashProvider = new HashProvider();

        boolean isPasswordCorrect = hashProvider.compareHash(resultSet.getString("password"), password);

        if(!isPasswordCorrect) {
            throw new Error("Wrong password");
        }
    }
}

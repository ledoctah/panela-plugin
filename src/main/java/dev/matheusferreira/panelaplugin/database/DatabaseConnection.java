package dev.matheusferreira.panelaplugin.database;

import dev.matheusferreira.panelaplugin.utils.PropertiesParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() throws SQLException, IOException {
        PropertiesParser propertiesParser = new PropertiesParser();

        String connectionUrl = propertiesParser.getProperty("database-url");

        return DriverManager.getConnection(connectionUrl);
    }

    public void closeConnection() throws SQLException, IOException {
        this.getConnection().close();
    }
}

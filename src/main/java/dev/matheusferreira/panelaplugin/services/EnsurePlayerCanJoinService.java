package dev.matheusferreira.panelaplugin.services;

import dev.matheusferreira.panelaplugin.database.DatabaseConnection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import static org.bukkit.Bukkit.getLogger;

public class EnsurePlayerCanJoinService {
    DatabaseConnection databaseConnection;

    public EnsurePlayerCanJoinService() {
        this.databaseConnection = new DatabaseConnection();
    }

    public boolean execute(String playerName) {
        try {
            Connection connection = this.databaseConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM allowed_users WHERE name = ?");

            statement.setString(1, playerName);

            ResultSet result = statement.executeQuery();

            return result.next();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

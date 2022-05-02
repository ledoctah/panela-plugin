package dev.matheusferreira.panelaplugin;

import dev.matheusferreira.panelaplugin.database.DatabaseConnection;
import dev.matheusferreira.panelaplugin.listeners.JoinListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public final class PanelaPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            DatabaseConnection connection = new DatabaseConnection();

            // testing connection to the database
            connection.getConnection();
            connection.closeConnection();
            getLogger().info("Successfully connected to the database");

            getServer().getPluginManager().registerEvents(new JoinListener(), this);
            getLogger().info("Registered events");
        } catch (SQLException e) {
            getLogger().severe("Could not connect to the database. Check your \"panela.properties\" file.");
            e.printStackTrace();
        } catch (IOException e) {
            getLogger().severe("Could not read \"panela.properties\" file.");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        System.out.println("PanelaPlugin is disabled successfully!");
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        getLogger().info(
            String.format(
                "Player %s invoked command %s with args %s",
                    sender.getName(),
                cmd.getName(),
                Arrays.toString(args)
            )
        );

        if(cmd.getName().equalsIgnoreCase("test")) {
            sender.sendMessage("You cannot move anymore!");

            Player player = getServer().getPlayer(sender.getName());

            if(player != null) {
                player.setWalkSpeed(0);
            }

            return true;
        }

        if(cmd.getName().equalsIgnoreCase("release")) {
            String password = args[0];

            if (!password.equals("pass")) {
                sender.sendMessage("Wrong password");
                return true;
            }

            sender.sendMessage("Now you can move");

            Player player = getServer().getPlayer(sender.getName());

            if(player != null) {
                player.setWalkSpeed(0.2f);
            }

            return true;
        }


        sender.sendMessage("Invalid command");
        return false;
    }
}

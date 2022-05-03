package dev.matheusferreira.panelaplugin;

import dev.matheusferreira.panelaplugin.commands.PluginCommands;
import dev.matheusferreira.panelaplugin.database.DatabaseConnection;
import dev.matheusferreira.panelaplugin.listeners.JoinListener;

import dev.matheusferreira.panelaplugin.listeners.MoveListener;
import dev.matheusferreira.panelaplugin.models.FrozenPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PanelaPlugin extends JavaPlugin {
    public static List<FrozenPlayer> frozenPlayersList = new ArrayList<>();

    @Override
    public void onEnable() {
        try {
            DatabaseConnection connection = new DatabaseConnection();

            // testing connection to the database
            connection.getConnection();
            connection.closeConnection();
            getLogger().info("Successfully connected to the database");

            getServer().getPluginManager().registerEvents(new JoinListener(), this);
            getServer().getPluginManager().registerEvents(new MoveListener(), this);
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
        getLogger().severe("PanelaPlugin is disabled successfully!");
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String[] commands = {"register", "login"};

        String command = cmd.getName();

        boolean isCommandValid = Arrays.asList(commands).contains(command);

        if (isCommandValid) {
            PluginCommands commandCaller = new PluginCommands();

            try {
                Method method =
                    commandCaller.getClass().getMethod(command, CommandSender.class, Command.class, String.class, String[].class);

                method.invoke(commandCaller, sender, cmd, label, args);

                return true;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                sender.sendMessage("Invalid command");
                return false;
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        sender.sendMessage("Invalid command");
        return false;
    }



    public static void addFrozenPlayer(FrozenPlayer frozenPlayer) {
        frozenPlayersList.add(frozenPlayer);
    }

    public static void removeFrozenPlayer(String playerName) {
        int index = -1;

        for (FrozenPlayer frozenPlayer : frozenPlayersList) {
            if(frozenPlayer.getPlayer().getName().equals(playerName)) {
                index = frozenPlayersList.indexOf(frozenPlayer);
                break;
            }
        }

        if(index >= 0) {
            frozenPlayersList.remove(index);
        }
    }
}

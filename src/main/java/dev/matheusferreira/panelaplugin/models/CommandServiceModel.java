package dev.matheusferreira.panelaplugin.models;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandServiceModel {
    boolean execute(CommandSender sender, Command cmd, String label, String[] args);
}

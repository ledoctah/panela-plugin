package dev.matheusferreira.panelaplugin.services;

import org.bukkit.entity.Player;

public class WelcomePlayerService {
    public void execute(Player player) {
        String nickname = player.getName();

        String message = String.format("Bem-vindo à panela, %s!", nickname);

        player.sendMessage(message);
    }
}

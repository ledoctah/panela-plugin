package dev.matheusferreira.panelaplugin.listeners;

import dev.matheusferreira.panelaplugin.services.EnsurePlayerCanJoinService;
import dev.matheusferreira.panelaplugin.services.WelcomePlayerService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent ;

public class JoinListener implements Listener {
    WelcomePlayerService welcomePlayerService;
    EnsurePlayerCanJoinService ensurePlayerCanJoinService;

    public JoinListener() {
        this.welcomePlayerService = new WelcomePlayerService();
        this.ensurePlayerCanJoinService = new EnsurePlayerCanJoinService();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean userCanPlay = this.ensurePlayerCanJoinService.execute(player.getName());

        if (!userCanPlay) {
            player.kickPlayer("You do not have permission to play on this server.");

            return;
        }

        this.welcomePlayerService.execute(player);
    }
}

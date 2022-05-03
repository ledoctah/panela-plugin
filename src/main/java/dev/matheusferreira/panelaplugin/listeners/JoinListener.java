package dev.matheusferreira.panelaplugin.listeners;

import dev.matheusferreira.panelaplugin.PanelaPlugin;
import dev.matheusferreira.panelaplugin.models.FrozenPlayer;
import dev.matheusferreira.panelaplugin.models.User;
import dev.matheusferreira.panelaplugin.services.EnsurePlayerCanJoinService;
import dev.matheusferreira.panelaplugin.services.FindUserDataService;
import dev.matheusferreira.panelaplugin.services.WelcomePlayerService;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent ;

import java.util.ArrayList;
import java.util.List;

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

        String playerName = player.getName();

        boolean userCanPlay = this.ensurePlayerCanJoinService.execute(playerName);

        if (!userCanPlay) {
            player.kickPlayer("You do not have permission to play on this server.");

            return;
        }

        this.welcomePlayerService.execute(player);

        FindUserDataService findUserDataService = new FindUserDataService();

        User user = findUserDataService.execute(playerName);

        if(user == null) {
            player.sendMessage("\n\n\n\n\nVocê ainda não efetuou seu registro");
            player.sendMessage("Para fazer o registro use o comando /register <senha>");
        } else {
            player.sendMessage("\n\n\n\n\nVocê precisa fazer o login para poder jogar.");
            player.sendMessage("Para fazer o login use o comando /login <senha>");
        }

        player.setGameMode(GameMode.SPECTATOR);
        FrozenPlayer frozenPlayer = new FrozenPlayer(player, player.getLocation());

        PanelaPlugin.addFrozenPlayer(frozenPlayer);
    }
}

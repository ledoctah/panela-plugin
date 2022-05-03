package dev.matheusferreira.panelaplugin.listeners;

import dev.matheusferreira.panelaplugin.models.FrozenPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static dev.matheusferreira.panelaplugin.PanelaPlugin.frozenPlayersList;

public class MoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        FrozenPlayer foundFrozenPlayer = null;

        if(frozenPlayersList.toArray().length == 0) {
            return;
        }

        for (FrozenPlayer frozenPlayer : frozenPlayersList) {
            if(frozenPlayer.getPlayer().getName().equals(player.getName())) {
                foundFrozenPlayer = frozenPlayer;
                break;
            }
        }

        if(foundFrozenPlayer != null) {
            player.teleport(foundFrozenPlayer.getInitialLocation());
        }
    }
}

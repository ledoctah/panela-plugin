package dev.matheusferreira.panelaplugin.models;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FrozenPlayer {
    Player player;

    Location initialLocation;

    public FrozenPlayer(Player player, Location initialLocation) {
        this.player = player;
        this.initialLocation = initialLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getInitialLocation() {
        return initialLocation;
    }

    public void setInitialLocation(Location initialLocation) {
        this.initialLocation = initialLocation;
    }
}

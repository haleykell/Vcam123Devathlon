package com.vcam123.devathlon.events;

import com.vcam123.devathlon.DevAthlon;
import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class JoinEvent implements Listener {

    private Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            Events.setDefaultConfig(player);
            plugin.saveConfig();
            player.setFlying(false);
            player.setAllowFlight(false);
            return;
        }
        TwoWayMirror mirror = new TwoWayMirror();
        mirror.getInstructions(player);
    }
}

package com.vcam123.devathlon.events;

import com.vcam123.devathlon.DevAthlon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class LogOutEvent implements Listener {

    private Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    @EventHandler
    public void onLogOut(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Events.setDefaultConfig(player);
        plugin.saveConfig();
        player.setFlying(false);
        player.setAllowFlight(false);
    }
}

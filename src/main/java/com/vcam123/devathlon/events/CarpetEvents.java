package com.vcam123.devathlon.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class CarpetEvents implements Listener {

    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!event.isSneaking()) return;
        event.setCancelled(true);
        player.setFlying(true);
        player.sendMessage(ChatColor.AQUA + "You are flying!");
    }

    public void onPlayerStop(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (event.getPlayer().isFlying() && event.getMessage().equalsIgnoreCase("stop")) {
            player.setFlying(false);
            player.sendMessage(ChatColor.AQUA + "You have stopped flying.");
        }
    }
}

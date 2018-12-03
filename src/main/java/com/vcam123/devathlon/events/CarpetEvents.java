package com.vcam123.devathlon.events;

import com.vcam123.devathlon.flyingCarpet.FlyingCarpet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class CarpetEvents implements Listener {

    public void onPlayerFly(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FlyingCarpet carpet = new FlyingCarpet();
        if (!player.isFlying() && event.getMessage().equalsIgnoreCase("fly")
                && player.getInventory().contains(carpet.getCarpet())) {
            if (!player.getInventory().getItemInMainHand().equals(carpet.getCarpet())) {
                player.sendMessage(ChatColor.GOLD + "FLYING CARPET: " + ChatColor.AQUA
                        + "Hold your carpet in your main hand and try again!");
                return;
            }
            player.setFlying(true);
            player.sendMessage(ChatColor.GOLD + "FLYING CARPET: " + ChatColor.AQUA + "You are flying!");
        }
        else if (player.isFlying() && event.getMessage().equalsIgnoreCase("stop")) {
            player.setFlying(false);
            player.sendMessage(ChatColor.GOLD + "FLYING CARPET: " + ChatColor.AQUA + "You have stopped flying.");
        }
    }
}

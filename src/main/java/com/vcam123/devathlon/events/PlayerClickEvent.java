package com.vcam123.devathlon.events;

import com.vcam123.devathlon.DevAthlon;
import com.vcam123.devathlon.flyingCarpet.FlyingCarpet;
import com.vcam123.devathlon.mirror.MirrorMessage;
import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PlayerClickEvent implements Listener {

    private MirrorMessage messages = new MirrorMessage();
    private Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        FlyingCarpet carpet = new FlyingCarpet();

        if (action.equals(Action.LEFT_CLICK_AIR) && event.hasItem()) {
            if (!player.isFlying()) {
                if (!event.getItem().equals(carpet.getCarpet())) {
                    player.sendMessage((ChatColor.GOLD + "FLYING CARPET: " + ChatColor.AQUA
                            + "You need to hold your carpet in your main hand! Try again!"));
                    return;
                }
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(ChatColor.GOLD + "FLYING CARPET: " + ChatColor.AQUA + "You are flying!");
                return;
            }
            else {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.GOLD + "FLYING CARPET: " + ChatColor.AQUA + "You have stopped flying.");
                return;
            }
        }

        DevAthlon.setDefaultConfig(player);
        TwoWayMirror mirror = new TwoWayMirror();

        if (!action.equals(Action.RIGHT_CLICK_AIR)) return;
        if (!event.hasItem() || (event.hasItem() && !event.getItem().equals(mirror.getMirror()))) return;

        messages.setActive(player, true);
        messages.setInitiated(player, player);
        messages.setReceiving(player, null);
        plugin.saveConfig();

        player.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                ChatColor.BLUE + "Who would you like to talk to using the two way mirror?");
    }
}

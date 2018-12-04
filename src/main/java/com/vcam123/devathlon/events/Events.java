package com.vcam123.devathlon.events;

import com.vcam123.devathlon.DevAthlon;
import com.vcam123.devathlon.flyingCarpet.FlyingCarpet;
import com.vcam123.devathlon.mirror.MirrorMessage;
import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Events implements Listener {

    // TODO: Cancelling communication after a certain amount of time
    // TODO: Setting allow flight to false if you're on the ground
    // TODO: Particle trail for flying carpet when you're flying

    private static MirrorMessage messages = new MirrorMessage();
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

        setDefaultConfig(player);
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

    // OKAY I MADE IT A LITTLE LESS UGLY BUT ITS STILL KINDA UGLY OH WELL
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (messages.isNotActive(player) && messages.getInitiated(player) == null)  return;
        event.setCancelled(true);
        String message = event.getMessage();

        Player receiving = messages.getReceiving(player);
        String receivingUUID;
        if (receiving == null) receivingUUID = "no one yet";
        else receivingUUID = receiving.getUniqueId().toString();

        // NULL POINTER EXCEPTION FIX THIS
        if (messages.getInitiated(player).getUniqueId().toString().equals(player.getUniqueId().toString())
                && receivingUUID.equalsIgnoreCase("no one yet")) {
            TwoWayMirror mirror = new TwoWayMirror();

            receiving = Bukkit.getPlayer(message);
            if (receiving == null) {
                setDefaultConfig(player);
                plugin.saveConfig();
                player.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "Player is not online or doesn't exist.");
                return;
            }

            receiving.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                    ChatColor.BLUE + player.getPlayerListName() + " would like to message you.");
            if (!receiving.getInventory().contains(mirror.getMirror())) {
                receiving.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "You need to be holding a two way mirror!");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " + ChatColor.BLUE +
                        receiving.getPlayerListName() + " was not holding a two way mirror! Try again.");
                setDefaultConfig(player);
                plugin.saveConfig();
                return;
            }

            messages.setReceiving(player, receiving);
            messages.setInitiated(receiving, player);
            messages.setReceiving(receiving, player);
            plugin.saveConfig();

            receiving.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                    ChatColor.BLUE + "To confirm messaging, say the other person's name in chat.");
        }
        else if (messages.isNotActive(player)) {
            if (message.equalsIgnoreCase("no")) {
                UUID initiated = UUID.fromString(messages.getInitiated(player).getUniqueId().toString());
                Player init = Bukkit.getPlayer(initiated);
                setDefaultConfig(player);
                init.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "Player denied communication.");
                setDefaultConfig(init);
                plugin.saveConfig();
            }
            else if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(message))){
                messages.setActive(player, true);
                Player initiated = Bukkit.getPlayer(message);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "Communication has started.\n");
                initiated.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "Player has accepted. Communication has started.\n");
            }
            else {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "Something went wrong. Cancelling communication.");
                UUID initiated = UUID.fromString(messages.getInitiated(player).getUniqueId().toString());
                Player init = Bukkit.getPlayer(initiated);
                setDefaultConfig(player);
                init.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "Something went wrong. Cancelling communication.");
                setDefaultConfig(init);
                plugin.saveConfig();
            }
        }
        else {
            if (message.equalsIgnoreCase("goodbye")) {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + "You said goodbye. Communication ending.");
                messages.getReceiving(player).sendMessage(ChatColor.LIGHT_PURPLE + "MIRROR: " +
                        ChatColor.BLUE + player.getPlayerListName() + " said goodbye. Communication ending.");
                setDefaultConfig(messages.getReceiving(player));
                setDefaultConfig(player);
                plugin.saveConfig();
            }
            else {
                messages.sendMessage(player, message);
            }
        }
    }

    public static void setDefaultConfig(Player player) {
        messages.setActive(player, false);
        messages.setReceiving(player, null);
        messages.setInitiated(player, null);
    }
}

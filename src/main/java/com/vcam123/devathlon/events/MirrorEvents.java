package com.vcam123.devathlon.events;

import com.vcam123.devathlon.DevAthlon;
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

public class MirrorEvents implements Listener {

    private MirrorMessage messages;
    private static Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        setDefaultConfig(player);
        TwoWayMirror mirror = new TwoWayMirror();

        if (!action.equals(Action.RIGHT_CLICK_AIR)) return;
        if (!event.hasItem() || (event.hasItem() && !event.getItem().equals(mirror.getMirror()))) return;

        messages = new MirrorMessage();
        // Maybe save this to a config instead
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.active", true);
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.initiated", player.getUniqueId().toString());
        // This will be who they are messaging
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.receiving", "no one yet");
        plugin.saveConfig();
        player.sendMessage(ChatColor.BLUE + "Who would you like to talk to using the two way mirror?");
    }

    // I KNOW THIS IS SUPER FUCKING UGLY MAYBE I'LL HAVE TIME TO DETERMINE AN EASIER WAY TO DO IT
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (plugin.getConfig().get("Users." + player.getUniqueId() + ".Messages.active").equals(true)
                && ((String) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.initiated")).equalsIgnoreCase(player.getUniqueId().toString())
                && ((String)plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.receiving")).equalsIgnoreCase("no one yet")) {
            TwoWayMirror mirror = new TwoWayMirror();
            Player receiving = Bukkit.getPlayer(message);
            if (receiving == null) {
                setDefaultConfig(player);
                plugin.saveConfig();
                player.sendMessage(ChatColor.BLUE + "Player is not online or doesn't exist.");
                return;
            }
            if (!receiving.getInventory().contains(mirror.getMirror())) {
                receiving.sendMessage(ChatColor.BLUE + "You need to be holding a two way mirror!");
                setDefaultConfig(player);
                plugin.saveConfig();
                return;
            }
            plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.receiving", receiving.getUniqueId().toString());
            plugin.getConfig().set("Users." + receiving.getUniqueId().toString() + ".Messages.receiving", player.getUniqueId().toString());
            plugin.getConfig().set("Users." + receiving.getUniqueId().toString() + ".Messages.initiated", player.getUniqueId().toString());
            receiving.sendMessage(ChatColor.BLUE + player.getPlayerListName() + "would like to message you.");
            receiving.sendMessage(ChatColor.BLUE + "To confirm messaging, say the other person's name in chat.");
            plugin.saveConfig();
        }
        else if (plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.active").equals(false)) {
            if (message.equalsIgnoreCase("no")) {
                UUID initiated = UUID.fromString((String) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.initiated"));
                Player init = Bukkit.getPlayer(initiated);
                setDefaultConfig(player);
                init.sendMessage(ChatColor.BLUE + "Player denied communication.");
                setDefaultConfig(init);
                plugin.saveConfig();
            }
            else if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(message))){
                plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.active", true);
                Player initiated = Bukkit.getPlayer(message);
                player.sendMessage(ChatColor.BLUE + "Communication has started.\n");
                initiated.sendMessage(ChatColor.BLUE + "Player has accepted. Communication has started.\n");
            }
            else {
                player.sendMessage(ChatColor.BLUE + "Something went wrong. Cancelling communication.");
                UUID initiated = UUID.fromString((String) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.initiated"));
                Player init = Bukkit.getPlayer(initiated);
                setDefaultConfig(player);
                init.sendMessage(ChatColor.BLUE + "Something went wrong. Cancelling communication.");
                setDefaultConfig(init);
                plugin.saveConfig();
            }
        }
        else {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "\ntest4\n");
            if (message.equalsIgnoreCase("goodbye")) {
                setDefaultConfig(player);
                player.sendMessage(ChatColor.BLUE + "Communication ending.");
                // TODO: FIX THIS
                // This should check the other person and reset their config and send them the communication ending message
                // Also need this to send regular messages back and forth
                // How to get Player from UUID cause this doesn't seem to be working correctly
                UUID other = UUID.fromString((String) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.initiated"));
                if (((String) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.initiated")).equalsIgnoreCase(player.getUniqueId().toString())) {
                    other = UUID.fromString((String) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.receiving"));
                }
                Player player2 = Bukkit.getPlayer(other);
                player2.sendMessage(ChatColor.BLUE + "Communication ending.");
                setDefaultConfig(player2);
                plugin.saveConfig();
            }
        }
    }

    public static void setDefaultConfig(Player player) {
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.active", false);
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.initiated", "no one yet");
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.receiving", "no one yet");
    }
}

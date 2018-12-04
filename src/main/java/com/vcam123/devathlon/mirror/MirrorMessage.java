package com.vcam123.devathlon.mirror;

import com.vcam123.devathlon.DevAthlon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class MirrorMessage {

    private Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    public MirrorMessage() {}

    public void sendMessage(Player player, String message) {
        getReceiving(player).sendMessage(player.getPlayerListName() + " says: " + message);
    }

    public Player getInitiated(Player player) {
        String uuid = (String) plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.initiated");
        if (uuid.equalsIgnoreCase("no one yet")) return null;
        UUID initiated = UUID.fromString(uuid);
        return Bukkit.getPlayer(initiated);
    }

    public Player getReceiving(Player player) {
        String uuid = (String) plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.receiving");
        if (uuid.equalsIgnoreCase("no one yet")) return null;
        UUID receiving = UUID.fromString(uuid);
        return Bukkit.getPlayer(receiving);
    }

    public boolean isNotActive(Player player) {
        return !Boolean.parseBoolean(plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.active").toString());
    }

    public void setInitiated(Player player, Player initiated) {
        if (initiated == null) plugin.getConfig().set("Users." +
                player.getUniqueId().toString() + ".Messages.initiated", "no one yet");
        else plugin.getConfig().set("Users." + player.getUniqueId().toString()
                + ".Messages.initiated", initiated.getUniqueId().toString());
    }

    public void setReceiving(Player player, Player receiving) {
        if (receiving == null) plugin.getConfig().set("Users." +
                player.getUniqueId().toString() + ".Messages.receiving", "no one yet");
        else plugin.getConfig().set("Users." + player.getUniqueId().toString()
                + ".Messages.receiving", receiving.getUniqueId().toString());
    }

    public void setActive(Player player, boolean active) {
        plugin.getConfig().set("Users." + player.getUniqueId().toString() + ".Messages.active", active);
    }
}

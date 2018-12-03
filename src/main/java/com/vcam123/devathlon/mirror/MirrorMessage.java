package com.vcam123.devathlon.mirror;

import com.vcam123.devathlon.DevAthlon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MirrorMessage {

    private Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    public MirrorMessage() {}

    public void sendMessage(Player player, String message) {
        getReceiving(player).sendMessage(player.getPlayerListName() + "says: " + message);
    }

    public Player getInitiated(Player player) {
        return Bukkit.getPlayer((String) plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.initiated"));
    }

    public Player getReceiving(Player player) {
        return Bukkit.getPlayer((String) plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.receiving"));
    }

    public boolean isActive(Player player) {
        return (boolean) plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.active");
    }

    public void setInitiated(Player player, Player initiated) {
        if (initiated == null) plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.initiated", "no one yet");
        else plugin.getConfig().get("Users." + player.getUniqueId().toString()
                + ".Messages.initiated", initiated.getUniqueId().toString());
    }

    public void setReceiving(Player player, Player receiving) {
        if (receiving == null) plugin.getConfig().get("Users." +
                player.getUniqueId().toString() + ".Messages.receiving", "no one yet");
        else plugin.getConfig().get("Users." + player.getUniqueId().toString()
                + ".Messages.receiving", receiving.getUniqueId().toString());
    }

    public void setActive(Player player, boolean active) {
        plugin.getConfig().get("Users." + player.getUniqueId().toString() + ".Messages.active", active);
    }
}

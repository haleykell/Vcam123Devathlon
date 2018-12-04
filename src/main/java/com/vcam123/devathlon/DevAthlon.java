package com.vcam123.devathlon;

import com.vcam123.devathlon.commands.CarpetCommand;
import com.vcam123.devathlon.commands.MirrorCommand;
import com.vcam123.devathlon.commands.InstructionsCommand;
import com.vcam123.devathlon.events.JoinEvent;
import com.vcam123.devathlon.events.LogOutEvent;
import com.vcam123.devathlon.events.PlayerChatEvent;
import com.vcam123.devathlon.events.PlayerClickEvent;
import com.vcam123.devathlon.flyingCarpet.FlyingCarpet;
import com.vcam123.devathlon.mirror.MirrorMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class DevAthlon extends JavaPlugin implements Listener {

    private MirrorCommand mirror = new MirrorCommand();
    private InstructionsCommand instructions = new InstructionsCommand();
    private CarpetCommand carpet = new CarpetCommand();

    @Override
    public void onEnable() {
        getCommand(mirror.admin).setExecutor(mirror);
        getCommand(instructions.instructions).setExecutor(instructions);
        getCommand(carpet.cmd).setExecutor(carpet);

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new LogOutEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickEvent(), this);

        FlyingCarpet flyingCarpet = new FlyingCarpet();
        flyingCarpet.customRecipe();

        loadConfig();
        getServer().getConsoleSender().sendMessage("\nPlugin enabled\n");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\nPlugin disabled\n");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static void setDefaultConfig(Player player) {
        MirrorMessage messages = new MirrorMessage();
        messages.setActive(player, false);
        messages.setReceiving(player, null);
        messages.setInitiated(player, null);
    }
}

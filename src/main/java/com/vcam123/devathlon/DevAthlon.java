package com.vcam123.devathlon;

import com.vcam123.devathlon.commands.CarpetCommand;
import com.vcam123.devathlon.commands.MirrorCommand;
import com.vcam123.devathlon.commands.InstructionsCommand;
import com.vcam123.devathlon.events.JoinEvent;
import com.vcam123.devathlon.events.LogOutEvent;
import com.vcam123.devathlon.events.Events;
import com.vcam123.devathlon.flyingCarpet.FlyingCarpet;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class DevAthlon extends JavaPlugin implements Listener {

    private MirrorCommand mirror = new MirrorCommand();
    private InstructionsCommand instructions = new InstructionsCommand();
    private CarpetCommand carpet = new CarpetCommand();

    @Override
    public void onEnable() {
        // MY IDEAS ONLY: NO ONE STEAL, I'LL KNOW IF YOU DO, LOVE YOU ALL
        // ALSO DON'T JUDGE ME IT'S A WORK IN PROGRESS I HAVE RESEARCH TO DO SHHHHH
            // Two way mirrors
                // right click then say a person's ign while holding a glass pane item with specific lore
                // If they're online AND they have a glass pane with lore anywhere in their inventory
                // send them a message saying "ign would like to talk! hold your mirror to talk!"
                // then they can send messages to each other

        // HERE ARE MY PROBLEMS AND STUFF TO LOOK INTO:
            // runnables and listeners?
                // right click listener then check what item?

        // YAY LETS GET PREPARED! HERE ARE MY NEXT STEPS TO TAKE:
            // Set up messaging logic
            // Set up events
                // PlayerChatEvent
                // PlayerMirrorMessageEvent?
                // Player right click event
        getCommand(mirror.admin).setExecutor(mirror);
        getCommand(instructions.instructions).setExecutor(instructions);
        getCommand(carpet.cmd).setExecutor(carpet);

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new LogOutEvent(), this);
        getServer().getPluginManager().registerEvents(new Events(), this);

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
}

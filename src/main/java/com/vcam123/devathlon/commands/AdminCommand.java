package com.vcam123.devathlon.commands;

import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class AdminCommand implements Listener, CommandExecutor {

    public String admin = "mirror";
    public String instructions = "instructions";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            TwoWayMirror mirror = new TwoWayMirror();
            if (command.getName().equalsIgnoreCase(admin)) {
                mirror.giveMirror((Player) commandSender);
                return true;
            }
            else if (command.getName().equalsIgnoreCase(instructions)) {
                Player player = (Player) commandSender;
                mirror.getInstructions(player);
            }
        }
        else {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        return false;
    }
}

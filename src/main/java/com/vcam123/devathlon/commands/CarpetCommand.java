package com.vcam123.devathlon.commands;

import com.vcam123.devathlon.flyingCarpet.FlyingCarpet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CarpetCommand implements Listener, CommandExecutor {

    public String cmd = "carpet";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (command.getName().equalsIgnoreCase(cmd)) {
                FlyingCarpet carpet = new FlyingCarpet();
                Player player = (Player) commandSender;
                carpet.giveCarpet(player);
                return true;
            }
        }
        else {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        return false;
    }
}

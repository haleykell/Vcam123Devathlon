package com.vcam123.devathlon.commands;

import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class InstructionsCommand implements Listener, CommandExecutor {

    public String instructions = "instructions";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (command.getName().equalsIgnoreCase(instructions)) {
                TwoWayMirror mirror = new TwoWayMirror();
                Player player = (Player) commandSender;
                mirror.getInstructions(player);
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
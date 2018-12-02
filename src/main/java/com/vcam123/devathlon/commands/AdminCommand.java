package com.vcam123.devathlon.commands;

import com.vcam123.devathlon.items.TwoWayMirror;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class AdminCommand implements Listener, CommandExecutor {

    public String admincmd = "givemirror";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (command.getName().equalsIgnoreCase(admincmd)) {
                TwoWayMirror mirror = new TwoWayMirror();
                mirror.giveMirror((Player) commandSender);
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

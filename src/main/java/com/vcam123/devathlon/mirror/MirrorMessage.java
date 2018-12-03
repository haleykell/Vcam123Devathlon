package com.vcam123.devathlon.mirror;

import org.bukkit.entity.Player;

public class MirrorMessage {

    public MirrorMessage() {}

    // Not sure if I like this but meh for now
    public void sendMessage(Player sending, Player receiving, String message) {
            receiving.sendMessage(sending.getPlayerListName() + "says: " + message);
    }
}

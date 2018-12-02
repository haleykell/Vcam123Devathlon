package com.vcam123.devathlon.mirror;

import org.bukkit.entity.Player;

public class MirrorMessage {

    // Player who initialized communication
    private Player sendingPlayer = null;
    // Player who DID NOT initialize communication
    private Player receivingPlayer = null;
    private boolean isActive = false;

    public MirrorMessage(Player send, Player receive) {
        this.sendingPlayer = send;
        this.receivingPlayer = receive;
        this.isActive = true;
    }

    public MirrorMessage() {}

    public void setSending(Player player) {
        sendingPlayer = player;
        this.isActive = true;
    }

    public void setReceiving(Player player) { receivingPlayer = player; }

    public Player getSending() { return sendingPlayer; }

    public Player getReceiving() { return receivingPlayer; }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) { isActive = active; }

    // Not sure if I like this but meh for now
    public void sendMessage(Player player, String message) {
        if (player == sendingPlayer) {
            receivingPlayer.sendMessage(sendingPlayer.getPlayerListName() + "says: " + message);
        }
        else if (player == receivingPlayer) {
            sendingPlayer.sendMessage(receivingPlayer.getPlayerListName() + "says: " + message);
        }
    }
}

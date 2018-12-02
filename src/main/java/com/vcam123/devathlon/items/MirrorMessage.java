package com.vcam123.devathlon.items;

import org.bukkit.entity.Player;

public class MirrorMessage {

    // Player who initialized communication
    private Player sendingPlayer;
    // Player who DID NOT initialize communication
    private Player receivingPlayer;

    public MirrorMessage(Player send, Player receive) {
        this.sendingPlayer = send;
        this.receivingPlayer = receive;
    }

    public MirrorMessage() {}

    public void setSending(Player player) { sendingPlayer = player; }

    public void setReciving(Player player) { receivingPlayer = player; }

    public Player getSending() { return sendingPlayer; }

    public Player getReceiving() { return receivingPlayer; }

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

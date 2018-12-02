package com.vcam123.devathlon.events;

import com.vcam123.devathlon.mirror.MirrorMessage;
import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

// I DON'T KNOW IF I'M DOING THESE EVENTS RIGHT SO I HAVE TO CHECK THIS LATER
public class Events implements Listener {

    private MirrorMessage messages;

    // I DON'T KNOW IF I'M DOING THESE EVENTS RIGHT SO I HAVE TO CHECK THIS LATER
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        TwoWayMirror mirror = new TwoWayMirror();

        if (!action.equals(Action.RIGHT_CLICK_AIR)) return;
        if (!event.hasItem() || (event.hasItem() && !event.getItem().equals(mirror.getMirror()))) return;

        messages = new MirrorMessage();
        messages.setSending(player);
        player.sendMessage("Who would you like to talk to using the two way mirror?");
    }

    // I DON'T KNOW IF I'M DOING THESE EVENTS RIGHT SO I HAVE TO CHECK THIS LATER
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!messages.isActive()) return;
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (messages.getReceiving() == null) {
            // TODO: If message is the ign of someone online and that person has a mirror in their inventory
            // TODO: set that player to receiving and send them a message if they want to be messaged
            // TODO: If they want to be messaged, they should say the ign of the other person in chat to confirm
            // TODO: If they say "no", setActive(false) and return
            return;
        }
        else {
            if (message.equalsIgnoreCase("goodbye")) {
                messages.setActive(false);
                return;
            }
            if (player.equals(messages.getReceiving())) {
                messages.sendMessage(messages.getSending(), message);
            }
            else if (player.equals(messages.getSending())) {
                messages.sendMessage(messages.getReceiving(), message);
            }
        }
    }
}

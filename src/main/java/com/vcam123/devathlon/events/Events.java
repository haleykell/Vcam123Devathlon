package com.vcam123.devathlon.events;

import com.vcam123.devathlon.mirror.MirrorMessage;
import com.vcam123.devathlon.mirror.TwoWayMirror;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

// I DON'T KNOW IF I'M DOING THESE EVENTS RIGHT SO I HAVE TO CHECK THIS LATER
public class Events implements Listener {

    private MirrorMessage messages;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) return;
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Two Way Mirror Instructions");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("A two way mirror allows you to communicate with");
        lore.add("someone who also has a two way mirror!");
        lore.add("To use: right click while holding the mirror");
        lore.add("Then say the ign of the person you want to talk to in chat");
        lore.add("To accept communication: confirm the person's ign in chat");
        lore.add("or say no to decline. Make sure to hold your mirror!");
        lore.add("To end communication: either party can say goodbye in chat");
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }

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

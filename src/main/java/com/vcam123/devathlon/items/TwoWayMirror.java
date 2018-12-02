package com.vcam123.devathlon.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TwoWayMirror {

    private ItemStack mirror = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);

    public TwoWayMirror() {
        ItemMeta meta = mirror.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        meta.setDisplayName("Two Way Mirror");
        lore.add("A two way mirror allows you to communicate with someone who also has a two way mirror!");
        lore.add("To use: right click while holding the item");
        lore.add("Then say the ign of the person you want to talk to in chat");
        lore.add("To accept communication: confirm the person's ign in chat while holding your mirror");
        lore.add("To end communication: either party can say goodbye in chat");
        meta.setLore(lore);
        mirror.setItemMeta(meta);
    }

    public void giveMirror(Player player) {
        player.getInventory().addItem(mirror);
    }

    public ItemStack getMirror() {
        return mirror;
    }
}

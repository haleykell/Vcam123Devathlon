package com.vcam123.devathlon.mirror;

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
        lore.add("Instructions for using the mirror were given to you");
        lore.add("after joining the server for the first time");
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

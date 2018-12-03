package com.vcam123.devathlon.mirror;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TwoWayMirror {

    private ItemStack mirror = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);
    private ItemStack instructions = new ItemStack(Material.PAPER, 1);

    public TwoWayMirror() {
        ItemMeta meta = mirror.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        meta.setDisplayName("Two Way Mirror");
        lore.add("Instructions for using the mirror were given to you");
        lore.add("after joining the server for the first time");
        meta.setLore(lore);
        mirror.setItemMeta(meta);
        ItemMeta instructionsMeta = instructions.getItemMeta();
        instructionsMeta.setDisplayName("Two Way Mirror Instructions");
        ArrayList<String> instructionsLore = new ArrayList<>();
        instructionsLore.add("A two way mirror allows you to communicate with");
        instructionsLore.add("someone who also has a two way mirror!");
        instructionsLore.add("To use: right click while holding the mirror");
        instructionsLore.add("Then say the ign of the person you want to talk to in chat");
        instructionsLore.add("To accept communication: confirm the person's ign in chat");
        instructionsLore.add("or say no to decline. Make sure to hold your mirror!");
        instructionsLore.add("To end communication: either party can say goodbye in chat");
        instructionsMeta.setLore(instructionsLore);
        instructions.setItemMeta(instructionsMeta);
    }

    public void giveMirror(Player player) {
        player.getInventory().addItem(mirror);
    }

    public ItemStack getMirror() {
        return mirror;
    }

    public void getInstructions(Player player) { player.getInventory().addItem(instructions); }
}

package com.vcam123.devathlon.flyingCarpet;

import com.vcam123.devathlon.DevAthlon;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class FlyingCarpet {

    ItemStack carpet = new ItemStack(Material.CARPET, 1, (byte) 14);
    private Plugin plugin = DevAthlon.getPlugin(DevAthlon.class);

    public FlyingCarpet() {
        ItemMeta meta = carpet.getItemMeta();
        meta.setDisplayName("Magical Flying Carpet");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Left click while holding the carpet to start flying!");
        lore.add("Left click again to stop flying!");
        lore.add("You don't have to keep holding the carpet to keep flying!");
        meta.setLore(lore);
        carpet.setItemMeta(meta);
    }

    public void giveCarpet(Player player) { player.getInventory().addItem(carpet); }

    public ItemStack getCarpet() { return carpet; }

    public void customRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "flying_carpet"), carpet);

        recipe.shape("   ","@ @","###");
        recipe.setIngredient('#', Material.CARPET);
        recipe.setIngredient('@', Material.FENCE);

        plugin.getServer().addRecipe(recipe);
    }

}

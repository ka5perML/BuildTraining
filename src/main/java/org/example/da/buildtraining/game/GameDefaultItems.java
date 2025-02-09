package org.example.da.buildtraining.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GameDefaultItems {
    public void giveDefaultItem(Player player) {
        ItemStack sandstone = new ItemStack(Material.SANDSTONE, 64);
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < 36; i++) {
            inventory.setItem(i, sandstone);
        }
    }
}

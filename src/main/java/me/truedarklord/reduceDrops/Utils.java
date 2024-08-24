package me.truedarklord.reduceDrops;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static void giveOrDropItems(Player player, ItemStack item) {
        player.getInventory().addItem(item).values().forEach(left -> player.getWorld().dropItem(player.getLocation(), left));
    }

    public static void giveXp(Player player, int amount) {
        player.giveExp(amount); //TODO add mending logic.
    }

}

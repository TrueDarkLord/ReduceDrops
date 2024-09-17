package me.truedarklord.reduceDrops;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

    public static void giveOrDropItems(Player player, ItemStack item) {
        player.getInventory().addItem(item).values().forEach(left -> player.getWorld().dropItem(player.getLocation(), left));
    }

    /**
     * Gives the the specified amount of Exp,
     * Taking mending into account.
     *
     * @param player The {@link Player} that is receiving the Exp.
     * @param amount The Amount of Exp to give.
     */
    public static void giveXp(Player player, int amount) {
        amount -= mend(player.getInventory().getItemInMainHand(), amount);
        amount -= mend(player.getInventory().getItemInOffHand(), amount);

        for (ItemStack armor : player.getInventory().getArmorContents()) {
            amount -= mend(armor, amount);
        }

        if (amount < 1) return; // No clue how performant #giveExp is.
        player.giveExp(amount);
    }

    /**
     * Mimics the description of how mending works
     * and returns the amount of Exp used to mend the item.
     *
     * @param item The {@link ItemStack} to mend.
     * @param expAmount Amount of Exp to mend the item with.
     * @return The amount of Exp used to mend the item.
     */
    private static int mend(ItemStack item, int expAmount) {
        if (expAmount < 1 || item == null || !item.hasItemMeta()) return 0;

        ItemMeta meta = item.getItemMeta();

        if (!(meta instanceof Damageable) || !meta.hasEnchant(Enchantment.MENDING)) return 0;

        int maxRepairAmount = expAmount * 2;
        int currentDamage = ((Damageable) meta).getDamage();

        if (currentDamage == 0) return 0;

        if (maxRepairAmount >= currentDamage) {
            ((Damageable) meta).setDamage(0);
            item.setItemMeta(meta);
            return currentDamage / 2;
        }

        ((Damageable) meta).setDamage(currentDamage - maxRepairAmount);
        item.setItemMeta(meta);
        return expAmount;
    }

}

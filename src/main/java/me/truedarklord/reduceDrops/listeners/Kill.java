package me.truedarklord.reduceDrops.listeners;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kill implements Listener {

    private static final ReduceDrops plugin = ReduceDrops.getPlugin(ReduceDrops.class);
    private static List<String> watchlist = plugin.getConfig().getStringList("Kill.Items");
    private static Boolean useWhitelist = plugin.getConfig().getBoolean("Kill.Whitelist", false);

    public Kill(ReduceDrops plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Gives loot an xp to the player that killed the entity or other player.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        List<ItemStack> items = event.getDrops();
        List<ItemStack> toRemove = new ArrayList<>(items.size());

        for (ItemStack itemStack : items) {
            if (!canGive(itemStack.getType().toString())) continue;

            toRemove.add(itemStack);
            Utils.giveOrDropItems(killer, itemStack);
        }
        toRemove.forEach(items::remove);

        // XP Section for killing.
        if (!(plugin.getConfig().getBoolean("Kill.Handle-XP", true))) {
            Utils.giveXp(killer, event.getDroppedExp());
            event.setDroppedExp(0);
        }

    }

    private boolean canGive(String itemType) {
        return useWhitelist == (watchlist.contains(itemType));
    }

    public static void reload() {
        watchlist = plugin.getConfig().getStringList("Kill.Items");
        useWhitelist = plugin.getConfig().getBoolean("Kill.Whitelist", false);
    }

}

package me.truedarklord.reduceDrops.listeners;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockDropItems implements Listener {

    private static final ReduceDrops plugin = ReduceDrops.getPlugin(ReduceDrops.class);
    private static List<String> watchlist = plugin.getConfig().getStringList("BlockBreak.Items");
    private static Boolean useWhitelist = plugin.getConfig().getBoolean("BlockBreak.Whitelist", false);

    public BlockDropItems(ReduceDrops plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Handle drops from all blocks that are broken.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockDropItems(BlockDropItemEvent event) {
        Player player = event.getPlayer();

        List<Item> items = event.getItems();
        List<Item> toRemove = new ArrayList<>();

        for (Item item : items) {
            ItemStack itemStack = item.getItemStack();

            if (!canGive(itemStack.getType().toString())) continue;

            item.remove();
            toRemove.add(item);
            Utils.giveOrDropItems(player, itemStack);
        }
        toRemove.forEach(items::remove);

    }

    // Give xp from block break.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!(plugin.getConfig().getBoolean("BlockBreak.Handle-XP", true))) return;

        Player player = event.getPlayer();

        Utils.giveXp(player, event.getExpToDrop());

        event.setExpToDrop(0);
    }

    private boolean canGive(String itemType) {
        return useWhitelist == (watchlist.contains(itemType.toLowerCase()));
    }

    public static void reload() {
        watchlist = plugin.getConfig().getStringList("BlockBreak.Items");
        useWhitelist = plugin.getConfig().getBoolean("BlockBreak.Whitelist", false);
    }

}

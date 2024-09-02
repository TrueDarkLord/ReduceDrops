package me.truedarklord.reduceDrops.listeners;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;

public class BlockDropItems implements Listener {

    private static final ReduceDrops plugin = ReduceDrops.getPlugin(ReduceDrops.class);

    public BlockDropItems(ReduceDrops plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Handle drops from all blocks that are broken.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockDropItems(BlockDropItemEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);

        event.getItems().forEach(item -> Utils.giveOrDropItems(player, item.getItemStack()));
    }

    // Give xp from block break.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!(plugin.getConfig().getBoolean("BlockBreak.Handle-XP", true))) return;

        Player player = event.getPlayer();

        Utils.giveXp(player, event.getExpToDrop());

        event.setExpToDrop(0);
    }

}

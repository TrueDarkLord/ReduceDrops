package me.truedarklord.reduceDrops.listeners;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class Fishing implements Listener {

    private final ReduceDrops plugin = ReduceDrops.getPlugin(ReduceDrops.class);

    public Fishing() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (plugin.getConfig().getBoolean("Fishing", false)) return;

        Item drop = ((Item) event.getCaught());

        Utils.giveOrDropItems(event.getPlayer(), drop.getItemStack());

        drop.remove();

    }
}

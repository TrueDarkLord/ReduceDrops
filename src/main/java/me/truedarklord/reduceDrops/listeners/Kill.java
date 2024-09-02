package me.truedarklord.reduceDrops.listeners;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Kill implements Listener {

    private static final ReduceDrops plugin = ReduceDrops.getPlugin(ReduceDrops.class);

    public Kill(ReduceDrops plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // Gives loot an xp to the player that killed the entity or other player.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        event.getDrops().forEach(drop -> Utils.giveOrDropItems(killer, drop));
        event.getDrops().clear();

        if (!(plugin.getConfig().getBoolean("Kill.Handle-XP", true))) {
            Utils.giveXp(killer, event.getDroppedExp());
            event.setDroppedExp(0);
        }

    }

}

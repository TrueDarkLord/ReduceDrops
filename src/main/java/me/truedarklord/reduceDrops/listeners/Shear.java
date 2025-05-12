package me.truedarklord.reduceDrops.listeners;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Shear implements Listener {

    private final ReduceDrops plugin = ReduceDrops.getPlugin(ReduceDrops.class);
    private final Map<UUID, UUID> shearMap = new HashMap<>();

    public Shear() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onShear(PlayerShearEntityEvent event) {
        if (!plugin.getConfig().getBoolean("Shearing.Enabled", false)) return;

        UUID entityId = event.getEntity().getUniqueId();

        shearMap.put(entityId, event.getPlayer().getUniqueId());
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> shearMap.remove(entityId), 1);

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void dropItems(EntityDropItemEvent event) {
        UUID entityId =  event.getEntity().getUniqueId();
        if (!shearMap.containsKey(entityId)) return;

        Player player = plugin.getServer().getPlayer(shearMap.get(entityId));

        Utils.giveOrDropItems(player, event.getItemDrop().getItemStack());

        event.getItemDrop().remove();
    }

}

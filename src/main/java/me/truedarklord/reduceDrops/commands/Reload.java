package me.truedarklord.reduceDrops.commands;

import me.truedarklord.reduceDrops.ReduceDrops;
import me.truedarklord.reduceDrops.listeners.BlockDropItems;
import me.truedarklord.reduceDrops.listeners.Kill;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reload implements CommandExecutor {

    private final ReduceDrops plugin;

    public Reload(ReduceDrops plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.getCommand("reload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        // Reload plugin specific data.
        BlockDropItems.reload();
        Kill.reload();

        sender.sendMessage("[Reduced Drops] Has been reloaded.");
        return true;
    }
}

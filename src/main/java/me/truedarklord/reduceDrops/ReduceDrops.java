package me.truedarklord.reduceDrops;

import me.truedarklord.reduceDrops.commands.Reload;
import me.truedarklord.reduceDrops.listeners.BlockDropItems;
import me.truedarklord.reduceDrops.listeners.Kill;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReduceDrops extends JavaPlugin {

    @Override
    public void onEnable() {

        new Reload(this);

        new BlockDropItems(this);
        new Kill(this);
    }

}

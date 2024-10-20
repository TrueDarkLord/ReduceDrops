package me.truedarklord.reduceDrops;

import me.truedarklord.reduceDrops.commands.Reload;
import me.truedarklord.reduceDrops.listeners.BlockDropItems;
import me.truedarklord.reduceDrops.listeners.Kill;
import org.bukkit.plugin.java.JavaPlugin;



public final class ReduceDrops extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(
                """
  
 §#00AA00================================§#ee2222
  _____          _                
 |  __ \\        | |               
 | |__) |___  __| |_   _  ___ ___ 
 |  _  // _ \\/ _` | | | |/ __/ _ \\
 | | \\ \\  __/ (_| | |_| | (_|  __/
 |_|__\\_\\___|\\__,_|\\__,_|\\___\\___|
 |  __ \\                          
 | |  | |_ __ ___  _ __  ___      
 | |  | | '__/ _ \\| '_ \\/ __|     
 | |__| | | | (_) | |_) \\__ \\     
 |_____/|_|  \\___/| .__/|___/     
                  | |             
                  |_|             
§#f5da2aBy TrueDarkLord.
§#00AA00================================
§#f5da2aFeel free to buy me a coffee:  §#00AA00|
§bhttps://ko-fi.com/truedarklord §#00AA00|
§#00AA00================================
                        """
        );

        new Reload(this);

        new BlockDropItems(this);
        new Kill(this);
    }

}

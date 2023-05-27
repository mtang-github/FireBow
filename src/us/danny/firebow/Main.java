package us.danny.firebow;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    public Main() {
    	//do nothing
    }
    
    @Override
    public void onEnable() {        
    	FireArrowDatabase arrowDatabase = new FireArrowDatabase(
    		Config.FIRE_TICKS, 
    		Config.TICK_MOD
    	);
    	
        PluginManager pluginManager = getServer().getPluginManager();
        
        BowShootListener bowListener = new BowShootListener(
        	arrowDatabase, 
        	Config.LORE
        );
        pluginManager.registerEvents(bowListener, this);
        
        new FireArrowUpdater(
        	arrowDatabase, 
        	Config.SPEED
        ).runTaskTimer(this, 0, 1);
        
        System.out.println(Config.NAME + " enabled");
    }
    
    @Override
    public void onDisable() {
    	System.out.println(Config.NAME + " disabled");
    }
}

package us.danny.firebow;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BowShootListener implements Listener
{
	private final FireArrowDatabase arrowDatabase;
    private final String lore;
    
    public BowShootListener(FireArrowDatabase arrowDatabase, String lore) {
    	this.arrowDatabase = arrowDatabase;
        this.lore = lore;
    }
    
    @EventHandler
    public void onShoot(final EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            ItemStack bow = event.getBow();
            
            if (isFireBow(bow)) {
            	Arrow arrow = (Arrow)event.getProjectile();
            	attachDummy(arrow);
            	arrowDatabase.addArrow(arrow);
            }
        }
    }
    
    //use a dummy looking down to spawn fireballs -> don't curve
    private void attachDummy(Arrow arrow) {
    	Location location = arrow.getLocation().clone();
    	location.setDirection(new Vector(0.0d, -1.0d, 0.0d));
    	ArmorStand dummy = arrow.getWorld().spawn(
    		location, ArmorStand.class
    	);
    	dummy.setVisible(false);
    	dummy.setSmall(true);
    	dummy.setMarker(true);
    	dummy.setGravity(false);
    	arrow.setPassenger(dummy);
    }
    
    private boolean isFireBow(ItemStack toTest) {
    	if(toTest.hasItemMeta()) {
    		ItemMeta itemMeta = toTest.getItemMeta();
    		if(itemMeta.hasLore()) {
    			List<String> loreList = itemMeta.getLore();
    			return loreList.contains(lore);
    		}
    	}
    	return false;
    }
}
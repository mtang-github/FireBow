package us.danny.firebow;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.SmallFireball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FireArrowUpdater extends BukkitRunnable {
	
	private final FireArrowDatabase arrowDatabase;
	private final double speed;
	
	public FireArrowUpdater(
		FireArrowDatabase arrowDatabase,
		double speed
	) {
		this.arrowDatabase = arrowDatabase;
		this.speed = speed;
	}

	@Override
	public void run() {
		for(Arrow arrow : arrowDatabase.getEligibleArrowsAndUpdate()) {
			shootFlame(arrow);
		}
	}
	
	private void shootFlame(Arrow arrow) {
		ArmorStand dummy = (ArmorStand)arrow.getPassenger();
		
		Vector velocity = dummy.getLocation().getDirection().clone();
		velocity.multiply(speed);
		
		SmallFireball fireball = dummy.launchProjectile(
			SmallFireball.class, 
			velocity
		);
		
		fireball.setOp(false);
		fireball.setBounce(false);
		fireball.setFireTicks(Integer.MAX_VALUE);
	}
}

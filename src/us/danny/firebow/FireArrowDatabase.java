package us.danny.firebow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Arrow;

public class FireArrowDatabase {
	
	private class ArrowEntry {
		private final Arrow arrow;
		private int ticksLeft;
		
		public ArrowEntry(Arrow arrow, int ticksLeft) {
			this.arrow = arrow;
			this.ticksLeft = ticksLeft;
		}
		
		//returns true if there are no ticks left
		public boolean tickDown() {
			return --ticksLeft <= 0;
		}
		
		public Arrow getArrow() {
			return arrow;
		}
		
		public boolean tickMod(int mod) {
			return ticksLeft % mod == 0;
		}
	}
	
	private final List<ArrowEntry> arrowEntryList;
	private final int maxTicks;
	private final int tickMod;
	
	public FireArrowDatabase(int maxTicks, int tickMod) {
		arrowEntryList = new ArrayList<>();
		this.maxTicks = maxTicks;
		this.tickMod = tickMod;
	}
	
	public void addArrow(Arrow arrow) {
		arrowEntryList.add(new ArrowEntry(arrow, maxTicks));
	}
	
	public List<Arrow> getEligibleArrowsAndUpdate() {
		List<Arrow> toRet = new ArrayList<>();
		Iterator<ArrowEntry> itr = arrowEntryList.iterator();
		while(itr.hasNext()) {
			ArrowEntry entry = itr.next();
			if(entry.tickMod(tickMod)) {
				if(entry.getArrow().isDead()) {
					itr.remove();
				}
				else {
					toRet.add(entry.getArrow());
				}
			}
			if(entry.tickDown()) {
				itr.remove();
			}
		}
		return toRet;
	}
}

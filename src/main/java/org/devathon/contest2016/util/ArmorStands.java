package org.devathon.contest2016.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public final class ArmorStands {

	public static ArmorStand createStand(Location location) {
		ArmorStand stand = location.getWorld().spawn(location, ArmorStand.class);
		return stand;
	}

	public static void createHologram(Location location, String name) {
		ArmorStand stand = createStand(location);
		stand.setVisible(false);
		stand.setBasePlate(false);
		stand.setGravity(false);
		stand.setAI(false);
		stand.setCustomName(name);
		stand.setCustomNameVisible(true);
	}

}

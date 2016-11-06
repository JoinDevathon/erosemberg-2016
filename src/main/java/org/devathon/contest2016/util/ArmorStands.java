package org.devathon.contest2016.util;

import net.minecraft.server.v1_10_R1.Vector3f;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

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

	public static void setRightArmPose(ArmorStand stand, float x, float y, float z) {
		Vector3f real = new Vector3f(x, y, y);

		((CraftArmorStand) stand).getHandle().setRightArmPose(real);
	}

}

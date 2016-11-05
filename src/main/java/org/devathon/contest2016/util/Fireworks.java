package org.devathon.contest2016.util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public final class Fireworks {

	public static void launchFireworks(Location location, int amount) {
		for (int i = 0; i < amount; i++) {
			Firework firework = location.getWorld().spawn(location, Firework.class);
			FireworkMeta meta = firework.getFireworkMeta();
			meta.addEffect(FireworkEffect.builder().withColor(Color.GREEN).trail(true).with(FireworkEffect.Type.BALL_LARGE).build());
			meta.setPower(10);
			firework.setFireworkMeta(meta);
		}
	}

}

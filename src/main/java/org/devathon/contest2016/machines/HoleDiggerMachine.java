package org.devathon.contest2016.machines;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.mechanics.Machine;
import org.devathon.contest2016.mechanics.MachineManager;
import org.devathon.contest2016.util.ArmorStands;

import java.util.ArrayList;
import java.util.List;

public class HoleDiggerMachine extends Machine {

	private boolean digging = false;

	public HoleDiggerMachine(Player owner) {
		super(owner);
	}

	@Override
	public void spawnIn(Location location) {
		digging = true;
		ArmorStand animated = createAnimatedStand(location.add(0, 1, 0));
		List<Location> previous = new ArrayList<>();
		new BukkitRunnable() {
			float angle = 360;
			float min = 270;

			boolean opposite = false;
			@Override
			public void run() {
				if (opposite) {
					angle++;
					if (angle >= 360) {
						opposite = true;
					}
				} else {
					angle--;
					if (angle <= min) {
						opposite = false;
					}
				}

				previous.forEach(location1 -> {
					location1.getBlock().setType(Material.AIR);
				});
				previous.clear();
				animated.setRightArmPose(new EulerAngle(angle, 0, 0));
				animated.teleport(animated.getLocation().subtract(0, 1, 0));
				animated.getWorld().playEffect(animated.getLocation(), Effect.SMOKE, 3);
				getOwner().playSound(animated.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1F, 0F);
				int half = (3 - 1) / 2;
				Location base = animated.getLocation().clone().subtract(half, 0, half);
				if (base.getBlock().getRelative(BlockFace.DOWN).getType() == Material.BEDROCK) {
					animated.remove();
					previous.forEach(location1 -> {
						location1.getBlock().setType(Material.AIR);
					});
					previous.clear();

					cancel();
					digging = false;
					MachineManager.getInstance().unregisterMachine(getOwner());
					return;
				}
				for (int x = 0; x < 3; x++) {
					for (int z = 0; z < 3; z++) {
						base.add(x, 0, z);
						base.getBlock().setType(Material.IRON_BLOCK);
						previous.add(base.getBlock().getLocation());
						base.subtract(x, 0, z);
					}
				}
			}
		}.runTaskTimer(DevathonPlugin.get(), 0L, 10L);
	}

	@Override
	public String getName() {
		return "Hole Digger";
	}

	@Override
	public String getState() {
		return (digging ? "Digging" : "Waiting...");
	}

	@Override
	public boolean shouldRemove() {
		return false;
	}

	private ArmorStand createAnimatedStand(Location location) {
		ArmorStand stand = ArmorStands.createStand(location);
		stand.setSmall(true);
		stand.setItemInHand(new ItemStack(Material.IRON_SPADE));
		stand.setVisible(false);
		stand.setAI(false);
		stand.setGravity(false);
		stand.setCustomName(ChatColor.YELLOW + ChatColor.BOLD.toString() + getName());
		stand.setCustomNameVisible(true);
		return stand;
	}

}

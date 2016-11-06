package org.devathon.contest2016.machines;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.mechanics.Machine;
import org.devathon.contest2016.mechanics.MachineManager;
import org.devathon.contest2016.util.ArmorStands;

import java.util.ArrayList;
import java.util.List;

public class WoodCutterMachine extends Machine {

	private boolean remove = false;

	private List<Block> blocks = new ArrayList<>();
	private MachineState state = MachineState.WAITING;
	private Block block;
	private ArmorStand stand;

	public WoodCutterMachine(Player owner) {
		super(owner);
	}

	@Override
	public void spawnIn(Location location) {
		buildMachine(location);
		state = MachineState.FINDING;
		for (int minX = -50; minX < 50; minX++) {
			for (int minZ = -50; minZ < 50; minZ++) {
				for (int minY = -50; minY < 50; minY++) {
					location.add(minX, minY, minZ);

					Block block = location.getBlock();
					if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
						blocks.add(block);
					}

					location.subtract(minX, minY, minZ);
				}
			}
		}

		if (blocks.size() <= 0) {
			getOwner().sendMessage(ChatColor.RED + "There were no blocks within reach. :(");
			remove = true;

			stand.remove();
			block.setType(Material.AIR);
			return;
		}

		state = MachineState.CUTTING;
		cut();
	}

	private void buildMachine(Location location) {
		Block block = location.getBlock();
		block.setType(Material.IRON_BLOCK);
		this.block = block;

		ArmorStand stand = ArmorStands.createStand(block.getLocation().add(0.5, -0.40, 0));
		stand.setArms(true);
		stand.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
		stand.setRightArmPose(new EulerAngle(4.642575810304916, 0.0, 1.5882496193148399)); //Precise as fuck 10/10
		stand.setVisible(false);
		stand.setGravity(false);
		stand.setBasePlate(false);
		this.stand = stand;
	}

	private void cut() {
		new BukkitRunnable() {
			@Override
			public void run() {
				//Cut 5 blocks every seconds.

				for (int i = 1; i < 6; i++) {
					if (blocks.size() == 0 || i > blocks.size()) {
						MachineManager.getInstance().unregisterMachine(getOwner());
						state = MachineState.FINISHED;
						cancel();

						block.setType(Material.AIR);
						stand.remove();
						Firework firework = block.getWorld().spawn(block.getLocation(), Firework.class);
						FireworkMeta meta = firework.getFireworkMeta();
						meta.addEffect(FireworkEffect.builder().withColor(Color.BLUE).build());
						firework.setFireworkMeta(meta);
						firework.detonate();

						break;
					}
					Block block = blocks.remove(i - 1);
					if (block == null || block.getType() == Material.AIR) {
						continue;
					}

					block.getWorld().playEffect(WoodCutterMachine.this.block.getLocation(), Effect.SMOKE, 3);
					getOwner().playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 1F, 0F);
					block.setType(Material.AIR);
					getOwner().getInventory().addItem(new ItemStack(Material.WOOD, 5, block.getData()));
				}
			}
		}.runTaskTimer(DevathonPlugin.get(), 0L, 20L);
	}

	@Override
	public String getName() {
		return "Wood Cutter";
	}

	@Override
	public String getState() {
		return WordUtils.capitalize(state.name().toLowerCase());
	}

	@Override
	public boolean shouldRemove() {
		return remove;
	}

	enum MachineState {
		WAITING,
		FINDING,
		CUTTING,
		FINISHED
	}
}

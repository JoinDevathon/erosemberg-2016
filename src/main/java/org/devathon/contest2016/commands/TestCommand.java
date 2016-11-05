package org.devathon.contest2016.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.devathon.contest2016.machines.HoleDiggerMachine;

public class TestCommand extends BaseCommand {
	public TestCommand() {
		super("");
	}

	@Override
	public void execute(Player player, String[] args) {
		HoleDiggerMachine machine = new HoleDiggerMachine();
		machine.spawnIn(player.getLocation());
		player.sendMessage(ChatColor.GREEN + "Spawned in " + machine.getName());
	}
}

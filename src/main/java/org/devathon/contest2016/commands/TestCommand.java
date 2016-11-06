package org.devathon.contest2016.commands;

import org.bukkit.entity.Player;
import org.devathon.contest2016.machines.HoleDiggerMachine;
import org.devathon.contest2016.machines.WoodCutterMachine;
import org.devathon.contest2016.mechanics.Machine;
import org.devathon.contest2016.mechanics.MachineManager;

public class TestCommand extends BaseCommand {
	public TestCommand() {
		super("");
	}

	@Override
	public void execute(Player player, String[] args) {
		Machine machine = new HoleDiggerMachine(player);
		machine.spawnIn(player.getLocation());

		MachineManager.getInstance().registerMachine(player, machine);
	}
}

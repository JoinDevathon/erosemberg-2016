package org.devathon.contest2016.mechanics;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Machine {

	private Player owner;

	public Machine(Player owner) {
		this.owner = owner;
	}

	public abstract void spawnIn(Location location);

	public abstract String getName();

	public abstract String getState();

	public abstract boolean shouldRemove();

	public Player getOwner() {
		return owner;
	}
}

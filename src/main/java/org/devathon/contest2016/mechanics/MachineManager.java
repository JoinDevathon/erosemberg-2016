package org.devathon.contest2016.mechanics;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MachineManager {

	private Map<UUID, Machine> activeMachines = new ConcurrentHashMap<>();
	private static MachineManager instance = new MachineManager();

	private int globalColorSwapIndex = 0;

	private MachineManager() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(DevathonPlugin.get(), () -> activeMachines.keySet().stream().map(Bukkit::getPlayer).forEach(player -> {
			Machine machine = activeMachines.get(player.getUniqueId());
			if (machine.shouldRemove()) {
				activeMachines.remove(player.getUniqueId());
				return;
			}

			String message = getMessageForPlayer(player);
			if (message == null) {
				return;
			}

			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
		}), 0L, 5L);
	}

	public void unregisterMachine(Player player) {
		activeMachines.remove(player.getUniqueId());
		System.out.println("activeMachines = " + activeMachines);
	}

	public void registerMachine(Player player, Machine machine) {
		activeMachines.putIfAbsent(player.getUniqueId(), machine);
	}

	public boolean hasActiveMachine(Player player) {
		return activeMachines.containsKey(player.getUniqueId());
	}

	public Map<UUID, Machine> getActiveMachines() {
		return activeMachines;
	}

	public static MachineManager getInstance() {
		return instance;
	}

	public String getMessageForPlayer(Player player) {
		if (!hasActiveMachine(player)) {
			return null;
		}

		Machine machine = activeMachines.get(player.getUniqueId());
		return ChatColor.YELLOW + machine.getName() + ChatColor.WHITE + ", State: " + (globalColorSwapIndex++ % 5 == 0 ? ChatColor.GREEN : ChatColor.WHITE) + machine.getState();
	}
}

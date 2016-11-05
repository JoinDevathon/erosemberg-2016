package org.devathon.contest2016.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand implements CommandExecutor {

	private String permission;

	public BaseCommand(String permission) {
		this.permission = permission;
	}

	public abstract void execute(Player player, String[] args);

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (!player.hasPermission(permission)) {
				player.sendMessage(ChatColor.RED + "You don't have enough permissions to execute this command!");
				return true;
			}

			execute(player, strings);
			return true;
		}
		return false;
	}
}

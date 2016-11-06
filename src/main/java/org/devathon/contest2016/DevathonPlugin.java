package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.commands.TestCommand;

public class DevathonPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
    }

    public static World getWorld() {
        return Bukkit.getServer().getWorlds().get(0);
    }

    public static DevathonPlugin get() {
        return JavaPlugin.getPlugin(DevathonPlugin.class);
    }
}


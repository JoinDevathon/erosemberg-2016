package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.commands.TestCommand;

import java.util.HashSet;
import java.util.Set;

public class DevathonPlugin extends JavaPlugin {

    private Set<ArmorStand> stands = new HashSet<>();

    @Override
    public void onEnable() {
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        stands.forEach(ArmorStand::remove);
    }

    public static World getWorld() {
        return Bukkit.getServer().getWorlds().get(0);
    }

    public Set<ArmorStand> getStands() {
        return stands;
    }

    public static DevathonPlugin get() {
        return JavaPlugin.getPlugin(DevathonPlugin.class);
    }
}


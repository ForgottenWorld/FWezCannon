package me.architett.fwezcannon;

import me.architett.fwezcannon.listeners.CannonListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class FWezcannon extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable(){
        // Plugin startup logic

        plugin = this;

        this.getServer().getPluginManager().registerEvents(new CannonListener(),this);

    }

    @Override
    public void onDisable(){
        // Plugin shutdown logic
    }
}

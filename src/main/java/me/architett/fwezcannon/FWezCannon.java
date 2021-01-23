package me.architett.fwezcannon;

import me.architett.fwezcannon.commands.ReloadCommand;
import me.architett.fwezcannon.listeners.CannonListener;
import me.architett.fwezcannon.listeners.ExtraListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class FWezCannon extends JavaPlugin {

    public static FileConfiguration fileConfiguration;

    @Override
    public void onEnable(){

        loadConfiguration();

        loadListeners();

        loadCommands();

    }

    @Override
    public void onDisable(){
        // Plugin shutdown logic
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        fileConfiguration = getConfig();
    }

    private void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new CannonListener(),this);

        if (fileConfiguration.getBoolean("enable_defuser"))
            this.getServer().getPluginManager().registerEvents(new ExtraListener(),this);

    }

    private void loadCommands() {
        this.getCommand("cannonreload").setExecutor(new ReloadCommand());
    }

    public static FileConfiguration getDefaultConfig() {
        return fileConfiguration;
    }

    public void reloadDefaultConfig() {
        reloadConfig();
        fileConfiguration = getConfig();
    }
}

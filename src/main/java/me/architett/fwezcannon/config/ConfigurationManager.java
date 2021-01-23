package me.architett.fwezcannon.config;

import me.architett.fwezcannon.FWezCannon;

public class ConfigurationManager {

    private static ConfigurationManager configInstance;
    private FWezCannon plugin;

    private ConfigurationManager(FWezCannon plugin) {
        if (configInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.plugin = plugin;
    }

    public static ConfigurationManager getInstance() {
        if(configInstance == null) {
            configInstance = new ConfigurationManager(FWezCannon.getPlugin(FWezCannon.class));
        }
        return configInstance;
    }

    public void reloadDefaultConfig() {
        plugin.reloadDefaultConfig();
    }

}

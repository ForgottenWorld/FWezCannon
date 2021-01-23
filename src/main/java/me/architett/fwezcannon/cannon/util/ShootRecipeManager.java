package me.architett.fwezcannon.cannon.util;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.ball.ShootType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShootRecipeManager {

    private static ShootRecipeManager shootRecipeManager;

    private HashMap<List<Material>, ShootType> shootTypeContainer;

    private ShootRecipeManager() {
        if(shootRecipeManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.shootTypeContainer = new HashMap<>();
        buildContainer();

    }

    public static ShootRecipeManager getInstance() {
        if(shootRecipeManager == null) {
            shootRecipeManager = new ShootRecipeManager();
        }
        return shootRecipeManager;
    }

    public void buildContainer() {

        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();

        if (fileConfiguration.getBoolean("slow_shoot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB,
                Material.HONEYCOMB,Material.TNT,Material.HONEYCOMB,
                Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB),ShootType.SLOW);

        if (fileConfiguration.getBoolean("poison_shoot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,
                Material.POISONOUS_POTATO,Material.TNT,Material.POISONOUS_POTATO,
                Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,Material.POISONOUS_POTATO),ShootType.POISON);

        if (fileConfiguration.getBoolean("blind_shoot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                Material.INK_SAC,Material.INK_SAC,Material.INK_SAC,
                Material.INK_SAC,Material.TNT,Material.INK_SAC,
                Material.INK_SAC,Material.INK_SAC,Material.INK_SAC),ShootType.BLIND);

        if (fileConfiguration.getBoolean("fire_shoot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                Material.FIRE_CHARGE,Material.TNT,Material.FIRE_CHARGE,
                Material.TNT,Material.FIRE_CHARGE,Material.TNT,
                Material.FIRE_CHARGE,Material.TNT,Material.FIRE_CHARGE),ShootType.FIRE);

        if (fileConfiguration.getBoolean("nogravity_shoot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,
                Material.PHANTOM_MEMBRANE,Material.TNT,Material.PHANTOM_MEMBRANE,
                Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE),ShootType.NOGRAVITY);

        if (fileConfiguration.getBoolean("huge_shoot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                Material.MAGMA_CREAM,Material.TNT,Material.MAGMA_CREAM,
                Material.TNT,Material.TNT,Material.TNT,
                Material.MAGMA_CREAM,Material.TNT,Material.MAGMA_CREAM),ShootType.HUGESHOOT);

        /*
        this.shootTypeContainer.put(Arrays.asList(
                Material.GUNPOWDER,Material.GUNPOWDER,Material.GUNPOWDER,
                Material.TNT,Material.TNT,Material.TNT,
                Material.GUNPOWDER,Material.GUNPOWDER,Material.GUNPOWDER),ShootType.MULTISHOOT);

         */
    }

    public void clearContainer() {
        this.shootTypeContainer.clear();
    }

    public ShootType getShootType(List<Material> recipe) {

        if (this.shootTypeContainer.get(recipe) == null)
            return ShootType.NORMAL;
        else
            return this.shootTypeContainer.get(recipe);

    }

}

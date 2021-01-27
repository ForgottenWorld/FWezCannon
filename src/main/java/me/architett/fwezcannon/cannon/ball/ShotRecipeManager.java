package me.architett.fwezcannon.cannon.ball;

import me.architett.fwezcannon.FWezCannon;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShotRecipeManager {

    private static ShotRecipeManager shotRecipeManager;

    private HashMap<List<Material>, ShotType> shotTypeContainer;

    private ShotRecipeManager() {
        if(shotRecipeManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.shotTypeContainer = new HashMap<>();
        buildContainer();

    }

    public static ShotRecipeManager getInstance() {
        if(shotRecipeManager == null) {
            shotRecipeManager = new ShotRecipeManager();
        }
        return shotRecipeManager;
    }

    public void buildContainer() {

        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();

        if (fileConfiguration.getBoolean("slow_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB,
                Material.HONEYCOMB,Material.TNT,Material.HONEYCOMB,
                Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB), ShotType.SLOW);

        if (fileConfiguration.getBoolean("poison_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,
                Material.POISONOUS_POTATO,Material.TNT,Material.POISONOUS_POTATO,
                Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,Material.POISONOUS_POTATO), ShotType.POISON);

        if (fileConfiguration.getBoolean("blind_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.INK_SAC,Material.INK_SAC,Material.INK_SAC,
                Material.INK_SAC,Material.TNT,Material.INK_SAC,
                Material.INK_SAC,Material.INK_SAC,Material.INK_SAC), ShotType.BLIND);

        if (fileConfiguration.getBoolean("fire_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.FIRE_CHARGE,Material.TNT,Material.FIRE_CHARGE,
                Material.TNT,Material.FIRE_CHARGE,Material.TNT,
                Material.FIRE_CHARGE,Material.TNT,Material.FIRE_CHARGE), ShotType.FIRE);

        if (fileConfiguration.getBoolean("nogravity_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,
                Material.PHANTOM_MEMBRANE,Material.TNT,Material.PHANTOM_MEMBRANE,
                Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE), ShotType.NOGRAVITY);

        if (fileConfiguration.getBoolean("huge_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.MAGMA_CREAM,Material.TNT,Material.MAGMA_CREAM,
                Material.TNT,Material.TNT,Material.TNT,
                Material.MAGMA_CREAM,Material.TNT,Material.MAGMA_CREAM), ShotType.HUGE_SHOT);

        if (fileConfiguration.getBoolean("multi_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                    Material.GUNPOWDER,Material.GUNPOWDER,Material.GUNPOWDER,
                    Material.TNT,Material.TNT,Material.TNT,
                    Material.GUNPOWDER,Material.GUNPOWDER,Material.GUNPOWDER),ShotType.MULTI_SHOT);

        if (fileConfiguration.getBoolean("bounce_shot.enable"))
            this.shotTypeContainer.put(Arrays.asList(
                Material.AIR,Material.SLIME_BLOCK,Material.AIR,
                Material.SLIME_BLOCK,Material.TNT,Material.SLIME_BLOCK,
                Material.AIR,Material.SLIME_BLOCK,Material.AIR),ShotType.BOUNCE_SHOT);

        /*

        if (fileConfiguration.getBoolean("special_shot.enable"))
            this.shootTypeContainer.put(Arrays.asList(
                    Material.FEATHER,Material.AIR,Material.FEATHER,
                    Material.FEATHER,Material.AIR,Material.FEATHER,
                    Material.WHITE_WOOL,Material.TNT,Material.WHITE_WOOL),ShotType.SPECIAL);

         */

    }

    public void clearContainer() {
        this.shotTypeContainer.clear();
    }

    public ShotType getShotType(List<Material> recipe) {

        if (this.shotTypeContainer.get(recipe) == null)
            return ShotType.NORMAL;
        else
            return this.shotTypeContainer.get(recipe);

    }

}

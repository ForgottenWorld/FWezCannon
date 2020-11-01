package me.architett.fwezcannon.cannon;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class ShootRecipe {

    public static List<Material> stickyShoot = Arrays.asList(
            Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB,
            Material.HONEYCOMB,Material.TNT,Material.HONEYCOMB,
            Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB);

    public static List<Material> poisonShoot = Arrays.asList(
            Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,
            Material.POISONOUS_POTATO,Material.TNT,Material.POISONOUS_POTATO,
            Material.POISONOUS_POTATO,Material.POISONOUS_POTATO,Material.POISONOUS_POTATO);

    public static List<Material> blindShoot = Arrays.asList(
            Material.INK_SAC,Material.INK_SAC,Material.INK_SAC,
            Material.INK_SAC,Material.TNT,Material.INK_SAC,
            Material.INK_SAC,Material.INK_SAC,Material.INK_SAC);

    public static List<Material> hungerShoot = Arrays.asList(
            Material.ROTTEN_FLESH,Material.ROTTEN_FLESH,Material.ROTTEN_FLESH,
            Material.ROTTEN_FLESH,Material.TNT,Material.ROTTEN_FLESH,
            Material.ROTTEN_FLESH,Material.ROTTEN_FLESH,Material.ROTTEN_FLESH);

    public static List<Material> nogravityShoot = Arrays.asList(
            Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,
            Material.PHANTOM_MEMBRANE,Material.TNT,Material.PHANTOM_MEMBRANE,
            Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE,Material.PHANTOM_MEMBRANE);

    public static List<Material> levitationShoot = Arrays.asList(
            Material.ENDER_PEARL,Material.FEATHER,Material.ENDER_PEARL,
            Material.FEATHER,Material.TNT,Material.FEATHER,
            Material.ENDER_PEARL,Material.FEATHER,Material.ENDER_PEARL);

    //NOT IMPLEMENTED YET

    public static List<Material> bigbigShoot = Arrays.asList(
            Material.MAGMA_CREAM,Material.TNT,Material.MAGMA_CREAM,
            Material.TNT,Material.MAGMA_CREAM,Material.TNT,
            Material.MAGMA_CREAM,Material.TNT,Material.MAGMA_CREAM);

    public static List<Material> fireShoot = Arrays.asList(
            Material.FIRE_CHARGE,Material.TNT,Material.FIRE_CHARGE,
            Material.TNT,Material.FIRE_CHARGE,Material.TNT,
            Material.FIRE_CHARGE,Material.TNT,Material.FIRE_CHARGE);


}

package me.architett.fwezcannon.cannon;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class ShootType {

    /*

    private final List<Material> stickyShoot = Arrays.asList(
            Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB,
            Material.HONEYCOMB,Material.TNT,Material.HONEYCOMB,
            Material.HONEYCOMB,Material.HONEYCOMB,Material.HONEYCOMB);

     */


    private final List<Material> stickyShoot = new ArrayList<Material>(){
        {
            add(Material.HONEYCOMB);
            add(Material.HONEYCOMB);
            add(Material.HONEYCOMB);
            add(Material.HONEYCOMB);
            add(Material.TNT);
            add(Material.HONEYCOMB);
            add(Material.HONEYCOMB);
            add(Material.HONEYCOMB);
            add(Material.HONEYCOMB);
        }
    };

    private final List<Material> poisonShoot = new ArrayList<Material>(){
        {
            add(Material.POISONOUS_POTATO);
            add(Material.POISONOUS_POTATO);
            add(Material.POISONOUS_POTATO);
            add(Material.POISONOUS_POTATO);
            add(Material.TNT);
            add(Material.POISONOUS_POTATO);
            add(Material.POISONOUS_POTATO);
            add(Material.POISONOUS_POTATO);
            add(Material.POISONOUS_POTATO);
        }
    };

    private final List<Material> blindShoot = new ArrayList<Material>(){
        {
            add(Material.INK_SAC);
            add(Material.INK_SAC);
            add(Material.INK_SAC);
            add(Material.INK_SAC);
            add(Material.TNT);
            add(Material.INK_SAC);
            add(Material.INK_SAC);
            add(Material.INK_SAC);
            add(Material.INK_SAC);
        }
    };

    private final List<Material> levitationShoot = new ArrayList<Material>(){
        {
            add(Material.PHANTOM_MEMBRANE);
            add(Material.PHANTOM_MEMBRANE);
            add(Material.PHANTOM_MEMBRANE);
            add(Material.PHANTOM_MEMBRANE);
            add(Material.TNT);
            add(Material.PHANTOM_MEMBRANE);
            add(Material.PHANTOM_MEMBRANE);
            add(Material.PHANTOM_MEMBRANE);
            add(Material.PHANTOM_MEMBRANE);
        }
    };

    private final List<Material> hungerShoot = new ArrayList<Material>(){
        {
            add(Material.ROTTEN_FLESH);
            add(Material.ROTTEN_FLESH);
            add(Material.ROTTEN_FLESH);
            add(Material.ROTTEN_FLESH);
            add(Material.TNT);
            add(Material.ROTTEN_FLESH);
            add(Material.ROTTEN_FLESH);
            add(Material.ROTTEN_FLESH);
            add(Material.ROTTEN_FLESH);
        }
    };



    private List<Material> inputRecipe;

    public ShootType(List<Material> inputRecipe){
        this.inputRecipe = inputRecipe;
    }

    public PotionEffectType getShootType() {

        if (inputRecipe.equals(stickyShoot))
            return PotionEffectType.SLOW;
        else if (inputRecipe.equals(poisonShoot))
            return PotionEffectType.POISON;
        else if (inputRecipe.equals(blindShoot))
            return PotionEffectType.BLINDNESS;
        else if (inputRecipe.equals(levitationShoot))
            return PotionEffectType.LEVITATION;
        else if (inputRecipe.equals(hungerShoot))
            return PotionEffectType.HUNGER;
        else
            return null;

    }


}


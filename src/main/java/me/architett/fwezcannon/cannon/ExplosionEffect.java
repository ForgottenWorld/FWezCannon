package me.architett.fwezcannon.cannon;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ExplosionEffect{

    private List<Material> inputRecipe;

    public ExplosionEffect(List<Material> inputRecipe){
        this.inputRecipe = inputRecipe;
    }

    public PotionEffectType getShootType() {

        if (inputRecipe.equals(ShootRecipe.stickyShoot))
            return PotionEffectType.SLOW;
        else if (inputRecipe.equals(ShootRecipe.poisonShoot))
            return PotionEffectType.POISON;
        else if (inputRecipe.equals(ShootRecipe.blindShoot))
            return PotionEffectType.BLINDNESS;
        else if (inputRecipe.equals(ShootRecipe.levitationShoot))
            return PotionEffectType.LEVITATION;
        else if (inputRecipe.equals(ShootRecipe.hungerShoot))
            return PotionEffectType.HUNGER;
        else
            return null;

    }


}


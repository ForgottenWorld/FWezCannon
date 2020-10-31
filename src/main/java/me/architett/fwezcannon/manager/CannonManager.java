package me.architett.fwezcannon.manager;

import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class CannonManager {

    //todo: possibilità di avere esplosioni con effetti sui players coinvolti (es: slowness,blindness,etc)


    private static CannonManager cannonManager;

    private final HashMap<Integer, PotionEffectType> cannonBall;

    private CannonManager() {
        if(cannonManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.cannonBall = new HashMap<>();

    }

    public static CannonManager getInstance() {
        if(cannonManager == null) {
            cannonManager = new CannonManager();
        }
        return cannonManager;
    }

    public void addCannonBall(int id,PotionEffectType potionEffectType) {
        this.cannonBall.put(id,potionEffectType);
    }

    public void removeCannonBall(int id) {
        this.cannonBall.remove(id);
    }

    public boolean isCannonBall(int id) {
        return cannonBall.containsKey(id);
    }

    public PotionEffectType getExplosionType(int id) { return  cannonBall.get(id); }

}

package me.architett.fwezcannon.cannon.ball;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.effect.CannonParticleEffects;
import me.architett.fwezcannon.cannon.util.BounceVector;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class CannonBallManager {

    private static CannonBallManager cannonBallManager;

    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<Integer, ShotType> cannonTNTcontainer;

    private CannonBallManager() {
        if(cannonBallManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.cannonTNTcontainer = new HashMap<>();

    }

    public static CannonBallManager getInstance() {
        if(cannonBallManager == null) {
            cannonBallManager = new CannonBallManager();
        }
        return cannonBallManager;
    }

    public void addBall(int entityID, ShotType shotType) {
        this.cannonTNTcontainer.put(entityID, shotType);
    }

    public boolean isBall(int entityID) {
        return this.cannonTNTcontainer.containsKey(entityID);
    }

    public void executeExplosion(Entity entityTNT) {

        ShotType shotType = cannonTNTcontainer.get(entityTNT.getEntityId());

        removeBall(entityTNT.getEntityId());

        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();

        double shootRange;
        float explosionPower;

        switch (shotType) {
            case FIRE:
                shootRange = fileConfiguration.getDouble("fire_shot.range");
                explosionPower = (float) fileConfiguration.getDouble("fire_shot.explosion_power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,
                        fileConfiguration.getBoolean("fire_shot.burn_block"),true,
                        entityTNT);

                entityTNT.getLocation().getNearbyPlayers(shootRange).forEach(p ->
                    p.setFireTicks(fileConfiguration.getInt("fire_shot.duration")));

                break;
            case BLIND:
                shootRange = fileConfiguration.getDouble("blind_shot.range");
                explosionPower = (float) fileConfiguration.getDouble("blind_shot.explosion_power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,false,true,
                        entityTNT);

                entityTNT.getLocation().getNearbyPlayers(shootRange).forEach(p ->
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                                fileConfiguration.getInt("blind_shot.duration"),
                                fileConfiguration.getInt("blind_shot.amplifier"))));

                break;
            case SLOW:
                shootRange = fileConfiguration.getDouble("slow_shot.range");
                explosionPower = (float) fileConfiguration.getDouble("slow_shot.explosion_power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,false,true,
                        entityTNT);

                entityTNT.getLocation().getNearbyPlayers(shootRange).forEach(p ->
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                                fileConfiguration.getInt("slow_shot.duration"),
                                fileConfiguration.getInt("slow_shot.amplifier"))));

                break;
            case POISON:
                shootRange = fileConfiguration.getDouble("poison_shot.range");
                explosionPower = (float) fileConfiguration.getDouble("poison_shot.explosion_power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,false,true,
                        entityTNT);

                entityTNT.getLocation().getNearbyPlayers(shootRange).forEach(p ->
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                                fileConfiguration.getInt("poison_shot.duration"),
                                fileConfiguration.getInt("poison_shot.amplifier"))));

                break;
            case HUGE_SHOT:
                explosionPower = (float) fileConfiguration.getDouble("huge_shot.power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,false,true,
                        entityTNT);
                break;
            case BOUNCE_SHOT:
                shootRange = fileConfiguration.getDouble("bounce_shot.range");
                explosionPower = (float) fileConfiguration.getDouble("bounce_shot.explosion_power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,false,true,
                        entityTNT);

                entityTNT.getLocation().getNearbyPlayers(shootRange).forEach(p ->
                        p.setVelocity(new BounceVector(p.getLocation().toVector()
                                ,entityTNT.getLocation().toVector()).getBounce()));

                break;
            default:
                explosionPower = (float) fileConfiguration.getDouble("normal_shot.power");

                entityTNT.getWorld().createExplosion(entityTNT.getLocation(),
                        explosionPower,false,true,
                        entityTNT);

        }

        CannonParticleEffects.getInstance().tntExplosionEffect(entityTNT.getLocation());

    }

    public void removeBall(int entityID) {
        this.cannonTNTcontainer.remove(entityID);
    }

}

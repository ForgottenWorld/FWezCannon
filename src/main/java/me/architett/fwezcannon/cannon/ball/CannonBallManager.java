package me.architett.fwezcannon.cannon.ball;

import me.architett.fwezcannon.FWezCannon;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class CannonBallManager {

    private static CannonBallManager cannonBallManager;

    private HashMap<Integer, ShootType> cannonTNTcontainer;

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

    public void addBall(int entityID, ShootType shootType) {
        this.cannonTNTcontainer.put(entityID, shootType);
    }

    public boolean isBall(int entityID) {
        return this.cannonTNTcontainer.containsKey(entityID);
    }

    public void executeExplosion(Entity entityTNT) {


        ShootType shootType = cannonTNTcontainer.get(entityTNT.getEntityId());
        removeBall(entityTNT.getEntityId());


        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();
        double shootRange;


        switch (shootType) {
            case FIRE:
                shootRange = fileConfiguration.getDouble("fire_shoot.range");
                entityTNT.getLocation().getWorld().createExplosion(entityTNT.getLocation(),
                        (float) fileConfiguration.getDouble("normal_shoot.power"),false,true);
                entityTNT.getNearbyEntities(shootRange,shootRange,shootRange).stream().filter(e -> e instanceof Player)
                        .forEach(e -> {
                    Player player = ((Player) e).getPlayer();
                    player.setFireTicks(fileConfiguration.getInt("fire_shoot.duration"));
                });
                break;
            case BLIND:
                shootRange = fileConfiguration.getDouble("blind_shoot.range");
                entityTNT.getLocation().getWorld().playSound(entityTNT.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1,1);
                entityTNT.getNearbyEntities(shootRange,shootRange,shootRange).stream().filter(e -> e instanceof Player)
                        .forEach(e -> {
                            Player player = ((Player) e).getPlayer();
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                                    fileConfiguration.getInt("blind_shoot.duration"),
                                    fileConfiguration.getInt("blind_shoot.amplifier")));
                        });
                break;
            case SLOW:
                shootRange = fileConfiguration.getDouble("slow_shoot.range");
                entityTNT.getLocation().getWorld().playSound(entityTNT.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1,1);
                entityTNT.getNearbyEntities(shootRange,shootRange,shootRange).stream().filter(e -> e instanceof Player)
                        .forEach(e -> {
                            Player player = ((Player) e).getPlayer();
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                                    fileConfiguration.getInt("slow_shoot.duration"),
                                    fileConfiguration.getInt("slow_shoot.amplifier")));
                        });
                break;
            case POISON:
                shootRange = fileConfiguration.getDouble("poison_shoot.range");
                entityTNT.getLocation().getWorld().playSound(entityTNT.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,1,1);
                entityTNT.getNearbyEntities(shootRange,shootRange,shootRange).stream().filter(e -> e instanceof Player)
                        .forEach(e -> {
                            Player player = ((Player) e).getPlayer();
                            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                                    fileConfiguration.getInt("poison_shoot.duration"),
                                    fileConfiguration.getInt("poison_shoot.amplifier")));
                        });
                break;
            case HUGESHOOT:
                entityTNT.getLocation().getWorld().createExplosion(entityTNT.getLocation(),
                        (float) fileConfiguration.getDouble("huge_shoot.power"),false,true);
                break;
            default:
                entityTNT.getLocation().getWorld().createExplosion(entityTNT.getLocation(),
                        (float) fileConfiguration.getDouble("normal_shoot.power"), false, true);

        }


    }

    public void removeBall(int entityID) {
        this.cannonTNTcontainer.remove(entityID);
    }

}

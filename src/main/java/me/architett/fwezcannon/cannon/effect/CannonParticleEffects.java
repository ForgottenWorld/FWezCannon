package me.architett.fwezcannon.cannon.effect;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.*;
import me.architett.fwezcannon.FWezCannon;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public class CannonParticleEffects {
    private static CannonParticleEffects cannonParticleEffects;

    private final EffectManager effectManager;

    private CannonParticleEffects() {
        if(cannonParticleEffects != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        effectManager = new EffectManager(FWezCannon.getPlugin(FWezCannon.class));

    }

    public static CannonParticleEffects getInstance() {
        if(cannonParticleEffects == null) {
            cannonParticleEffects = new CannonParticleEffects();
        }
        return cannonParticleEffects;
    }

    public void shootTrace(Entity entity, Location location) {

        TraceEffect traceEffect = new TraceEffect(effectManager);
        traceEffect.setEntity(entity);
        traceEffect.particle = Particle.CAMPFIRE_COSY_SMOKE;
        traceEffect.iterations = 10;
        traceEffect.start();

        ExplodeEffect explodeEffect = new ExplodeEffect(effectManager);
        explodeEffect.setLocation(location);
        explodeEffect.amount = 5;
        explodeEffect.start();

    }

    public void shootFail(Location location) {

        ParticleEffect particleEffect = new ParticleEffect(effectManager);
        particleEffect.setLocation(location);
        particleEffect.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        particleEffect.particleCount = 0;
        particleEffect.particleData = 0.2F;
        particleEffect.particleOffsetY = 0.2F;
        particleEffect.period = 10;
        particleEffect.iterations = 3;
        particleEffect.start();

    }

    public void cannonExplosionEffect(Location location) {

        SphereEffect sphereEffect2 = new SphereEffect(effectManager);
        sphereEffect2.setLocation(location);
        sphereEffect2.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        sphereEffect2.particles = 30;
        sphereEffect2.radiusIncrease = 0.4;
        sphereEffect2.iterations = 4;
        sphereEffect2.particleData = 0.01F;
        sphereEffect2.start();
    }

    public void tntExplosionEffect(Location location) {
        SphereEffect sphereEffect = new SphereEffect(effectManager);
        sphereEffect.setLocation(location);
        sphereEffect.particle = Particle.CAMPFIRE_SIGNAL_SMOKE;
        sphereEffect.particles = 20;
        sphereEffect.radiusIncrease = 0.1;
        sphereEffect.iterations = 2;
        sphereEffect.particleData = 0.01F;
        sphereEffect.start();
    }

}

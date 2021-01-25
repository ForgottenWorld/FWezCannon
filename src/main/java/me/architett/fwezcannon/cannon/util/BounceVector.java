package me.architett.fwezcannon.cannon.util;

import me.architett.fwezcannon.FWezCannon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

public class BounceVector {

    private final Vector vector;

    public BounceVector(Vector playerVec, Vector cannonBallVec) {
        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();
        this.vector = playerVec.subtract(cannonBallVec).normalize()
                .multiply(fileConfiguration.getDouble("bounce_shot.bounce_power"))
                .add(new Vector(0,fileConfiguration.getDouble("bounce_shot.vertical_bounce_power"),0));
    }

    public Vector getBounce() {
        return this.vector;
    }
}

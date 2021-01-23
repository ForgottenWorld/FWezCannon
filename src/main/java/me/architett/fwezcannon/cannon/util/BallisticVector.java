package me.architett.fwezcannon.cannon.util;

import me.architett.fwezcannon.FWezCannon;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

public class BallisticVector {

    private Vector ballisticvector;

    public BallisticVector(Block dispenser, int gunpowderAmount, int weigth) {

        Directional dispenserDirectional = (Directional) dispenser.getBlockData();

        FileConfiguration defaultConfig = FWezCannon.getDefaultConfig();

        double shootPower = (defaultConfig.getDouble("gunpowder_power") * gunpowderAmount) + 1;
        double shootAngle = weigth / defaultConfig.getDouble("weight_effect");

        this.ballisticvector = dispenser.getLocation().getDirection();

        switch(dispenserDirectional.getFacing()) {
            case NORTH:
                this.ballisticvector = this.ballisticvector.add(new Vector(0, shootAngle, shootPower * - 1));
                break;
            case SOUTH:
                this.ballisticvector = this.ballisticvector.add(new Vector(0, shootAngle, shootPower));
                break;
            case EAST:
                this.ballisticvector = this.ballisticvector.add(new Vector(shootPower, shootAngle, 0));
                break;
            case WEST:
                this.ballisticvector = this.ballisticvector.add(new Vector(shootPower * -1, shootAngle,0));
                break;
        }

    }

    public Vector generate() {
        return this.ballisticvector;
    }
}
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

        switch(dispenserDirectional.getFacing()) {
            case NORTH:
                this.ballisticvector = new Vector(0, 0, -1);
                break;
            case SOUTH:
                this.ballisticvector = new Vector(0, 0, 1);
                break;
            case EAST:
                this.ballisticvector = new Vector(1, 0, 0);
                break;
            case WEST:
                this.ballisticvector = new Vector(-1, 0, 0);
                break;
        }

        this.ballisticvector = this.ballisticvector.multiply(shootPower).add(new Vector(0,shootAngle,0));

    }

    public Vector generate() {
        return this.ballisticvector;
    }
}

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

        double gunpowderPower = (defaultConfig.getDouble("gunpowder_power") * gunpowderAmount) + 1;

        switch(dispenserDirectional.getFacing()) {
            case NORTH:
                this.ballisticvector = new Vector(0, 0, -1).rotateAroundX(Math.toRadians(weigth));
                break;
            case SOUTH:
                this.ballisticvector = new Vector(0, 0, 1).rotateAroundX(Math.toRadians(-weigth));
                break;
            case EAST:
                this.ballisticvector = new Vector(1, 0, 0).rotateAroundZ(Math.toRadians(weigth));
                break;
            case WEST:
                this.ballisticvector = new Vector(-1, 0, 0).rotateAroundZ(Math.toRadians(-weigth));
                break;
        }

        this.ballisticvector = this.ballisticvector.multiply(gunpowderPower);

    }

    public Vector generate() {
        return this.ballisticvector;
    }

}

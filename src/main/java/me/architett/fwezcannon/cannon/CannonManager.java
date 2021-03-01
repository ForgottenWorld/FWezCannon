package me.architett.fwezcannon.cannon;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class CannonManager {

    private static CannonManager cannonManager;

    private Set<Vector> autorizedCannon;

    private CannonManager() {
        if(cannonManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.autorizedCannon = new HashSet<>();

    }

    public static CannonManager getInstance() {
        if(cannonManager == null) {
            cannonManager = new CannonManager();
        }
        return cannonManager;
    }

    public void addAutorizedCannon(Location location) {
        this.autorizedCannon.add(location.toVector());
    }

    public void removeAutorizedCannon(Location location) {
        this.autorizedCannon.remove(location.toVector());
    }

    public boolean isAutorizedCannon(Location location) {
        return autorizedCannon.contains(location.toVector());
    }

    public void clearAutorizedCannon() {
        this.autorizedCannon.clear();
    }
}

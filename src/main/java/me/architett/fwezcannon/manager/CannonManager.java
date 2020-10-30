package me.architett.fwezcannon.manager;

import java.util.ArrayList;
import java.util.List;

public class CannonManager {

    //todo: possibilità di avere esplosioni con effetti sui players coinvolti (es: slowness,blindness,etc)


    private static CannonManager cannonManager;

    private final List<Integer> cannonBall;

    private CannonManager() {
        if(cannonManager != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.cannonBall = new ArrayList<>();

    }

    public static CannonManager getInstance() {
        if(cannonManager == null) {
            cannonManager = new CannonManager();
        }
        return cannonManager;
    }

    public void addCannonBall(int id) {
        this.cannonBall.add(id);
    }

    public void removeCannonBall(int id) {
        this.cannonBall.remove((Integer)id);
    }

    public boolean isCannonBall(int id) {
        return cannonBall.contains(id);
    }


}

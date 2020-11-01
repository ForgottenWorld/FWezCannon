package me.architett.fwezcannon.cannon;

import me.architett.fwezcannon.FWezcannon;
import me.architett.fwezcannon.cannon.parts.BlastChamber;
import me.architett.fwezcannon.cannon.parts.CannonBarrel;
import me.architett.fwezcannon.manager.CannonManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class Cannon {

    private final Block blastFurnace;
    private final Directional bfDirection;

    private Block block1;
    private Block block2;
    private Block block3;
    private Block block4;
    private Block dispenser;
    private Block air;

    //private Block antiHopper; allow hopper under blastfurnace ?

    private BlastChamber blastChamber;
    private CannonBarrel cannonBarrel;

    public Cannon(Block blastFurnace) {

        this.blastFurnace = blastFurnace;
        this.bfDirection = (Directional) blastFurnace.getBlockData();


        switch(bfDirection.getFacing()) {
            case NORTH:
                this.block1 = blastFurnace.getRelative(0, 0, 1);
                this.block2 = blastFurnace.getRelative(0, 0, 2);
                this.block3 = blastFurnace.getRelative(0, 0, 3);
                this.block4 = blastFurnace.getRelative(0, 0, 4);
                this.dispenser = blastFurnace.getRelative(0, 0, 5);
                this.air = blastFurnace.getRelative(0, 0, 6);
                break;
            case SOUTH:
                this.block1 = blastFurnace.getRelative(0, 0, -1);
                this.block2 = blastFurnace.getRelative(0, 0, -2);
                this.block3 = blastFurnace.getRelative(0, 0, -3);
                this.block4 = blastFurnace.getRelative(0, 0, -4);
                this.dispenser = blastFurnace.getRelative(0, 0, -5);
                this.air = blastFurnace.getRelative(0, 0, -6);
                break;
            case EAST:
                this.block1 = blastFurnace.getRelative(-1, 0, 0);
                this.block2 = blastFurnace.getRelative(-2, 0, 0);
                this.block3 = blastFurnace.getRelative(-3, 0, 0);
                this.block4 = blastFurnace.getRelative(-4, 0, 0);
                this.dispenser = blastFurnace.getRelative(-5, 0, 0);
                this.air = blastFurnace.getRelative(-6, 0, 0);
                break;
            case WEST:
                this.block1 = blastFurnace.getRelative(1, 0, 0);
                this.block2 = blastFurnace.getRelative(2, 0, 0);
                this.block3 = blastFurnace.getRelative(3, 0, 0);
                this.block4 = blastFurnace.getRelative(4, 0, 0);
                this.dispenser = blastFurnace.getRelative(5, 0, 0);
                this.air = blastFurnace.getRelative(6, 0, 0);

        }
    }

    public boolean isCanon() {

        if (blastFurnace.getType() == Material.BLAST_FURNACE
                && block1.getType() == Material.NETHERITE_BLOCK
                && block2.getType() == Material.NETHERITE_BLOCK
                && block3.getType() == Material.NETHERITE_BLOCK
                && block4.getType() == Material.NETHERITE_BLOCK
                && dispenser.getType() == Material.DISPENSER
                && air.isPassable()) {

            Directional dsDirection = (Directional) dispenser.getBlockData();

            switch(bfDirection.getFacing()) {
                case NORTH:
                    return dsDirection.getFacing() == BlockFace.SOUTH;
                case SOUTH:
                    return dsDirection.getFacing() == BlockFace.NORTH;
                case EAST:
                    return dsDirection.getFacing() == BlockFace.WEST;
                case WEST:
                    return dsDirection.getFacing() == BlockFace.EAST;
            }
        }
        return false;
    }

    public void fire() {


        this.blastFurnace.getWorld().playSound(this.blastFurnace.getLocation(), Sound.ENTITY_CREEPER_PRIMED,1,1);
        BlastFurnace bf = (BlastFurnace) this.blastFurnace.getState();
        bf.setBurnTime((short) 30);
        bf.update();

        this.blastChamber = new BlastChamber(bf);
        this.cannonBarrel = new CannonBarrel((Dispenser) this.dispenser.getState());

        new BukkitRunnable() {

            @Override
            public void run() {

                if (blastChamber.getPropellant() == -1 || cannonBarrel.getExplosiveAmount() == -1) {
                    return;
                }

                if (blastChamber.getPropellant() == 0 || cannonBarrel.getExplosiveAmount() == 0) {
                    fail();
                    return;
                }

                blastChamber.addWaste(new Random().nextInt(blastChamber.getPropellant()) + 1);

                if (blastChamber.getWaste() >= 64 || !checkProportion(blastChamber.getPropellant(),cannonBarrel.getExplosiveAmount())) {
                    selfdestroy();
                    return;
                }

                shoot();

            }
        }.runTaskLater(FWezcannon.plugin,40L);

    }

    public Vector generateBallisticVector() {

        int x = blastChamber.getPropellant();
        double y = 0.15 * ( blastChamber.getWeight() / 5.0 );


        switch(bfDirection.getFacing()) {
            case NORTH:
                return dispenser.getLocation().add(0,0,1).toVector().subtract(dispenser.getLocation().add(0,-1 * y,-0.3 * (x / 10F)).toVector());
            case SOUTH:
                return dispenser.getLocation().add(0,0,-1).toVector().subtract(dispenser.getLocation().add(0,-1 * y,0.3 * (x / 10F)).toVector());
            case EAST:
                return dispenser.getLocation().add(-1,0,0).toVector().subtract(dispenser.getLocation().add(0.3 * (x / 10F),-1 * y,0).toVector());
            case WEST:
                return dispenser.getLocation().add(1,0,0).toVector().subtract(dispenser.getLocation().add(-0.3 * (x / 10F),-1 * y,0).toVector());
        }
        return null;
    }

    private void shoot() {

        Entity tnt = dispenser.getWorld().spawnEntity(air.getLocation(),EntityType.PRIMED_TNT);
        tnt.setVelocity(generateBallisticVector());
        ((TNTPrimed)tnt).setFuseTicks(120);


        //TEST CODE
        if (cannonBarrel.getRecipe().equals(ShootRecipe.nogravityShoot))
            tnt.setGravity(false);

        //TEST CODE

        CannonManager.getInstance().addCannonBall(tnt.getEntityId(),new ExplosionEffect(cannonBarrel.getRecipe()).getShootType());

        air.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,air.getLocation(),0,0,0.1,0);
        blastFurnace.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,blastFurnace.getLocation().add(0,1,0),1);
        blastFurnace.getWorld().playSound(blastFurnace.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,1,1);

        blastChamber.clearPropellant();
        cannonBarrel.clearBarrel();

    }

    private void selfdestroy() {

        blastChamber.clearPropellant();
        cannonBarrel.clearBarrel();

        block1.setType(Material.AIR);
        block2.setType(Material.AIR);
        block3.setType(Material.AIR);
        block4.setType(Material.AIR);
        dispenser.setType(Material.AIR);
        blastFurnace.getWorld().createExplosion(blastFurnace.getLocation(),8F,false,true);

    }

    private void fail() {
        new BukkitRunnable() {
            int times = 0;

            @Override
            public void run(){
                blastFurnace.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE,blastFurnace.getLocation(),0,0,0.1,0);
                times++;
                if (times == 3) {
                    this.cancel();
                }
            }
        }.runTaskTimer(FWezcannon.plugin,0,10);

    }

    private boolean checkProportion(int gunpowderCount,int tntAmount) {
        return tntAmount * 10 == gunpowderCount;
    }

}

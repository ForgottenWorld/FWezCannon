package me.architett.fwezcannon.cannon;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.ball.CannonBallManager;
import me.architett.fwezcannon.cannon.parts.BlastChamber;
import me.architett.fwezcannon.cannon.parts.CannonBarrel;
import me.architett.fwezcannon.cannon.util.BallisticVector;
import me.architett.fwezcannon.cannon.util.ShootRecipeManager;
import me.architett.fwezcannon.cannon.ball.ShootType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

public class Cannon {

    private final Block blastFurnaceBlock;

    private Block block1;
    private Block block2;
    private Block block3;
    private Block block4;
    private Block dispenser;
    private Block air;

    private BlastChamber blastChamber;
    private CannonBarrel cannonBarrel;

    public Cannon(Block blastFurnaceBlock) {

        this.blastFurnaceBlock = blastFurnaceBlock;

        Directional blastFurnaceDirectional = (Directional) blastFurnaceBlock.getBlockData();

        switch(blastFurnaceDirectional.getFacing()) {
            case NORTH:
                this.block1 = blastFurnaceBlock.getRelative(0, 0, 1);
                this.block2 = blastFurnaceBlock.getRelative(0, 0, 2);
                this.block3 = blastFurnaceBlock.getRelative(0, 0, 3);
                this.block4 = blastFurnaceBlock.getRelative(0, 0, 4);
                this.dispenser = blastFurnaceBlock.getRelative(0, 0, 5);
                this.air = blastFurnaceBlock.getRelative(0, 0, 6);
                break;
            case SOUTH:
                this.block1 = blastFurnaceBlock.getRelative(0, 0, -1);
                this.block2 = blastFurnaceBlock.getRelative(0, 0, -2);
                this.block3 = blastFurnaceBlock.getRelative(0, 0, -3);
                this.block4 = blastFurnaceBlock.getRelative(0, 0, -4);
                this.dispenser = blastFurnaceBlock.getRelative(0, 0, -5);
                this.air = blastFurnaceBlock.getRelative(0, 0, -6);
                break;
            case EAST:
                this.block1 = blastFurnaceBlock.getRelative(-1, 0, 0);
                this.block2 = blastFurnaceBlock.getRelative(-2, 0, 0);
                this.block3 = blastFurnaceBlock.getRelative(-3, 0, 0);
                this.block4 = blastFurnaceBlock.getRelative(-4, 0, 0);
                this.dispenser = blastFurnaceBlock.getRelative(-5, 0, 0);
                this.air = blastFurnaceBlock.getRelative(-6, 0, 0);
                break;
            case WEST:
                this.block1 = blastFurnaceBlock.getRelative(1, 0, 0);
                this.block2 = blastFurnaceBlock.getRelative(2, 0, 0);
                this.block3 = blastFurnaceBlock.getRelative(3, 0, 0);
                this.block4 = blastFurnaceBlock.getRelative(4, 0, 0);
                this.dispenser = blastFurnaceBlock.getRelative(5, 0, 0);
                this.air = blastFurnaceBlock.getRelative(6, 0, 0);

        }
    }

    public boolean isCanon() {

        if (blastFurnaceBlock.getType() == Material.BLAST_FURNACE
                && block1.getType() == Material.NETHERITE_BLOCK
                && block2.getType() == Material.NETHERITE_BLOCK
                && block3.getType() == Material.NETHERITE_BLOCK
                && block4.getType() == Material.NETHERITE_BLOCK
                && dispenser.getType() == Material.DISPENSER
                && air.isPassable()) {

            Directional dispenseDirectional = (Directional) dispenser.getBlockData();
            Directional blastFurnaceDirectional = (Directional) blastFurnaceBlock.getBlockData();


            switch(blastFurnaceDirectional.getFacing()) {
                case NORTH:
                    return dispenseDirectional.getFacing() == BlockFace.SOUTH;
                case SOUTH:
                    return dispenseDirectional.getFacing() == BlockFace.NORTH;
                case EAST:
                    return dispenseDirectional.getFacing() == BlockFace.WEST;
                case WEST:
                    return dispenseDirectional.getFacing() == BlockFace.EAST;
            }
        }
        return false;
    }

    public void setFire() {

        this.blastFurnaceBlock.getWorld().playSound(this.blastFurnaceBlock.getLocation(), Sound.ENTITY_CREEPER_PRIMED,1,1);

        BlastFurnace blastFurnace = (BlastFurnace) this.blastFurnaceBlock.getState();
        blastFurnace.setBurnTime((short) 30);
        blastFurnace.update();

        this.blastChamber = new BlastChamber(blastFurnace);
        this.cannonBarrel = new CannonBarrel((Dispenser) this.dispenser.getState());


        new BukkitRunnable() {

            @Override
            public void run() {

                FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();

                int gunpowderAmount = blastChamber.getGunpowderAmount();

                if (gunpowderAmount == 0 || cannonBarrel.getTNTamount() == 0) {
                    fail();
                    return;
                }

                if (gunpowderAmount > fileConfiguration.getInt("max_gunpowder_amount")) { //Valore settabile da config
                    selfExplode(); //impostare meccanismo di sicurezza con un Math.min(gunpowderAmount,valoreDiConfig)
                    return;
                }

                shoot(gunpowderAmount,
                        ShootRecipeManager.getInstance().getShootType(cannonBarrel.getRecipe()));

            }
        }.runTaskLater(FWezCannon.getPlugin(FWezCannon.class),40L);

    }

    private void shoot(int gunpowderAmount, ShootType shootType) {

        Entity tnt = dispenser.getWorld().spawnEntity(air.getLocation(),EntityType.PRIMED_TNT);

        tnt.setVelocity(new BallisticVector(dispenser, gunpowderAmount,
                blastChamber.getWeight()).generate());

        ((TNTPrimed)tnt).setFuseTicks(120);

        CannonBallManager.getInstance().addBall(tnt.getEntityId(), shootType);

        if (shootType.equals(ShootType.NOGRAVITY))
            tnt.setGravity(false);


        //todo: effetto fiamme che escono dal cannone ?
        air.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,air.getLocation(),0,0,0.1,0);
        blastFurnaceBlock.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, blastFurnaceBlock.getLocation().add(0,1,0),1);
        blastFurnaceBlock.getWorld().playSound(blastFurnaceBlock.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,1,1);

        blastChamber.clearPropellant();

        cannonBarrel.reduceInventory();

    }

    private void fail() {
        new BukkitRunnable() {
            int times = 0;

            @Override
            public void run(){
                blastFurnaceBlock.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, blastFurnaceBlock.getLocation(),0,0,0.1,0);
                times++;
                if (times == 3) {
                    this.cancel();
                }
            }
        }.runTaskTimer(FWezCannon.getPlugin(FWezCannon.class),0,10);
    }

    private void selfExplode() {
        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();

        block1.setType(Material.AIR);
        block2.setType(Material.AIR);
        block3.setType(Material.AIR);
        block4.setType(Material.AIR);
        blastFurnaceBlock.getWorld().createExplosion(blastFurnaceBlock.getLocation(),
                (float) fileConfiguration.getDouble("selfdestroy_explosion_power"),false,true);
    }

}

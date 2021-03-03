package me.architett.fwezcannon.cannon;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.ball.CannonBallManager;
import me.architett.fwezcannon.cannon.effect.CannonParticleEffects;
import me.architett.fwezcannon.cannon.parts.BlastChamber;
import me.architett.fwezcannon.cannon.parts.CannonBarrel;
import me.architett.fwezcannon.cannon.util.BallisticVector;
import me.architett.fwezcannon.cannon.ball.ShotRecipeManager;
import me.architett.fwezcannon.cannon.ball.ShotType;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

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

        Material cannonMaterial = Material.getMaterial(Objects.requireNonNull(FWezCannon
                .getDefaultConfig().getString("cannon_block")));

        if (blastFurnaceBlock.getType() == Material.BLAST_FURNACE
                && block1.getType() == cannonMaterial
                && block2.getType() == cannonMaterial
                && block3.getType() == cannonMaterial
                && block4.getType() == cannonMaterial
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

    public void ignite() {

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

                if (!isCanon() || gunpowderAmount == 0 || cannonBarrel.getTNTamount() == 0) {
                    CannonParticleEffects.getInstance().shootFail(blastFurnace.getLocation().add(0.5,1,0.5));
                    return;
                }

                if (gunpowderAmount > fileConfiguration.getInt("max_gunpowder_amount")) {
                    cannonSelfDestruction();
                    return;
                }


                preshot();

            }
        }.runTaskLater(FWezCannon.getPlugin(FWezCannon.class),40L);

    }

    private void preshot() {

        ShotType shotType = ShotRecipeManager.getInstance().getShotType(cannonBarrel.getRecipe());

        if (shotType.equals(ShotType.MULTI))
            shot(shotType, FWezCannon.getDefaultConfig().getInt("multi_shot.repeat_times"));
        else
            shot(shotType, 1);

        blastChamber.clearPropellant();

        cannonBarrel.reduceInventory();

    }

    private void shot(ShotType shotType, int shotRepeat) {

        new BukkitRunnable() {

            private final Vector ballisticVector = new BallisticVector(dispenser, blastChamber.getGunpowderAmount(),
                    blastChamber.getWeight()).generate();

            private int i = shotRepeat;

            @Override
            public void run() {

                if (!isCanon()) {
                    this.cancel();
                    CannonParticleEffects.getInstance().shootFail(blastFurnaceBlock.getLocation().add(0.5,1,0.5));
                    return;
                }

                Entity tnt = dispenser.getWorld().spawnEntity(air.getLocation().toCenterLocation(),EntityType.PRIMED_TNT);
                tnt.setVelocity(ballisticVector);
                CannonParticleEffects.getInstance().shootTrace(tnt,air.getLocation().toCenterLocation());

                ((TNTPrimed)tnt).setFuseTicks(FWezCannon.getDefaultConfig().getInt("tnt_fuse_ticks"));

                CannonBallManager.getInstance().addBall(tnt.getEntityId(), shotType);

                if (shotType.equals(ShotType.NOGRAVITY))
                    tnt.setGravity(false);


                i--;
                if (i <= 0)
                    this.cancel();
            }
        }.runTaskTimer(FWezCannon.getPlugin(FWezCannon.class),0,
                FWezCannon.getDefaultConfig().getLong("multi_shot.repeat_delay_ticks"));
    }


    private void cannonSelfDestruction() {
        FileConfiguration fileConfiguration = FWezCannon.getDefaultConfig();
        Location loc = blastFurnaceBlock.getLocation().toCenterLocation();

        blastFurnaceBlock.setType(Material.AIR);
        block1.setType(Material.AIR);
        block2.setType(Material.AIR);
        block3.setType(Material.AIR);
        block4.setType(Material.AIR);
        dispenser.setType(Material.AIR);
        loc.getWorld().playSound(loc,Sound.BLOCK_ANVIL_BREAK,1,1);

        CannonParticleEffects.getInstance().cannonExplosionEffect(loc);

        Entity tnt = loc.getWorld().spawnEntity(loc,EntityType.PRIMED_TNT);
        loc.getWorld().createExplosion(loc,
                (float) fileConfiguration.getDouble("selfdestroy_explosion_power"),false,true,
                tnt);
        tnt.remove();

    }

}

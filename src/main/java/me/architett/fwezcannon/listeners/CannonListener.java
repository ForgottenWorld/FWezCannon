package me.architett.fwezcannon.listeners;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.Cannon;
import me.architett.fwezcannon.cannon.CannonManager;
import me.architett.fwezcannon.cannon.ball.CannonBallManager;
import me.architett.fwezcannon.cannon.effect.CannonParticleEffects;
import me.architett.fwezcannon.util.MessageUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class CannonListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private Set<UUID> cooldown = new HashSet<>();

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null
                || !FWezCannon.getDefaultConfig().getStringList("enabled_world")
                .contains(event.getClickedBlock().getLocation().getWorld().getName()))
            return;


        ItemStack itemStack = event.getItem();

        if (block.getType() == Material.BLAST_FURNACE
                && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && itemStack != null
                && itemStack.getType() == Material.FLINT_AND_STEEL ) {

            if (FWezCannon.getDefaultConfig().getBoolean("authorized_cannons_only")
                    && !CannonManager.getInstance().isAutorizedCannon(block.getLocation())) {
                return;
            }

            Cannon cannon = new Cannon(block);

            if (cannon.isCanon()) {

                event.setCancelled(true);

                if (cooldown.contains(event.getPlayer().getUniqueId())) {
                    event.getPlayer().sendActionBar(MessageUtil.format(FWezCannon
                            .getDefaultConfig().getString("string.cooldown_message")));
                    return;
                }

                cannon.ignite();

                if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)
                        || FWezCannon.getDefaultConfig().getLong("player_use_cannon_cooldown") == 0)
                    cooldownTask(event.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onTNTExplosion(ExplosionPrimeEvent event) {

        if (!(event.getEntity() instanceof TNTPrimed))
            return;

        if (CannonBallManager.getInstance().isBall(event.getEntity().getEntityId())) {
            event.setCancelled(true);
            CannonBallManager.getInstance().executeExplosion(event.getEntity());
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.BLAST_FURNACE)
                && FWezCannon.getDefaultConfig().getBoolean("authorized_cannons_only")
                && CannonManager.getInstance().isAutorizedCannon(event.getBlock().getLocation())) {
            CannonManager.getInstance().removeAutorizedCannon(event.getBlock().getLocation());
            CannonParticleEffects.getInstance().cannonExplosionEffect(event.getBlock().getLocation());
            event.getPlayer().sendMessage(MessageUtil.format(FWezCannon.getDefaultConfig().getString("string.cannon_destroyed")));
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (event.getBlock().getType().equals(Material.BLAST_FURNACE)
                && FWezCannon.getDefaultConfig().getBoolean("authorized_cannons_only")
                && CannonManager.getInstance().isAutorizedCannon(event.getBlock().getLocation())) {
            CannonManager.getInstance().removeAutorizedCannon(event.getBlock().getLocation());
            CannonParticleEffects.getInstance().cannonExplosionEffect(event.getBlock().getLocation());
        }
    }

    public void cooldownTask(UUID uuid) {
        cooldown.add(uuid);
        new BukkitRunnable() {
            @Override
            public void run() {
                cooldown.remove(uuid);
            }
        }.runTaskLaterAsynchronously(FWezCannon.getPlugin(FWezCannon.class),
                FWezCannon.getDefaultConfig().getLong("player_use_cannon_cooldown") * 20);
    }

}

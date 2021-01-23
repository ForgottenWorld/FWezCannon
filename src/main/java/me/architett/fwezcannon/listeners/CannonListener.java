package me.architett.fwezcannon.listeners;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.Cannon;
import me.architett.fwezcannon.cannon.ball.CannonBallManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CannonListener implements Listener {

    private List<UUID> cooldown = new ArrayList<>();

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null ||
                !FWezCannon.getDefaultConfig().getStringList("enabled_world")
                        .contains(event.getClickedBlock().getLocation().getWorld().getName()))
            return;

        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (block.getType() == Material.BLAST_FURNACE
                && action.equals(Action.RIGHT_CLICK_BLOCK)
                && itemStack != null
                && itemStack.getType() == Material.FLINT_AND_STEEL ) {

            Cannon cannon = new Cannon(block);

            if (cannon.isCanon()) {

                event.setCancelled(true);

                if (cooldown.contains(event.getPlayer().getUniqueId())) {
                    block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE,1,1);
                    event.getPlayer().sendActionBar(ChatColor.YELLOW + "" + ChatColor.BOLD
                            + FWezCannon.getDefaultConfig().getString("cooldown_message"));
                    return;
                }

                cannon.setFire();
                if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
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

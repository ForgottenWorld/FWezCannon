package me.architett.fwezcannon.listeners;

import me.architett.fwezcannon.FWezcannon;
import me.architett.fwezcannon.cannon.Cannon;
import me.architett.fwezcannon.manager.CannonManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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

public class CannonListener implements Listener{

    List<UUID> cooldown = new ArrayList<>();

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (block == null)
            return;

        if (block.getType() == Material.BLAST_FURNACE
                && action.equals(Action.RIGHT_CLICK_BLOCK)
                && itemStack != null
                && itemStack.getType() == Material.FLINT_AND_STEEL ) {

            Cannon cannon = new Cannon(block);

            if (cannon.isCanon()) {
                event.setCancelled(true);

                if (cooldown.contains(player.getUniqueId())){
                    player.sendMessage("Ricarica del cannone in corso ...");
                    return;
                }

                cooldown.add(player.getUniqueId());
                fireCooldown(player);
                cannon.fire();

            }
        }

    }

    @EventHandler
    public void onTNTExplosion(ExplosionPrimeEvent event) {

        if (!(event.getEntity() instanceof TNTPrimed))
            return;


        if (CannonManager.getInstance().isCannonBall(event.getEntity().getEntityId())) {
            //more code here

            //todo: possibilità di avere esplosioni con effetti sui players coinvolti (es: slowness,blindness,etc)

            //and then
            CannonManager.getInstance().removeCannonBall(event.getEntity().getEntityId());
        }

    }

    public void fireCooldown(Player player) {

        new BukkitRunnable() {

            @Override
            public void run(){
                cooldown.remove(player.getUniqueId());
            }
        }.runTaskLater(FWezcannon.plugin,100L); //VALORE DI PROVA

    }

}

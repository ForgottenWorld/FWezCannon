package me.architett.fwezcannon.listeners;

import me.architett.fwezcannon.cannon.Cannon;
import me.architett.fwezcannon.manager.CannonManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CannonListener implements Listener{


    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
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
                cannon.fire();

            }
        }
    }

    @EventHandler
    public void onTNTExplosion(ExplosionPrimeEvent event) {

        if (!(event.getEntity() instanceof TNTPrimed))
            return;

        if (CannonManager.getInstance().isCannonBall(event.getEntity().getEntityId())) {
            PotionEffectType potionEffectType = CannonManager.getInstance().getExplosionType(event.getEntity().getEntityId());

            if (potionEffectType != null){

                for(Entity entity : event.getEntity().getNearbyEntities(5, 3, 5)){
                    if (entity instanceof Player){
                        Player player = (Player) entity;
                        player.addPotionEffect(new PotionEffect(potionEffectType,200,1));
                    }
                }

            }

            CannonManager.getInstance().removeCannonBall(event.getEntity().getEntityId());
        }

    }

}

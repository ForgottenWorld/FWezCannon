package me.architett.fwezcannon.listeners;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.ball.CannonBallManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ExtraListener implements Listener {

    @EventHandler
    public void defuseTNT(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof TNTPrimed))
            return;

        if (CannonBallManager.getInstance().isBall(event.getRightClicked().getEntityId())) {
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

            if (itemStack != null &&  itemStack.getType().equals(Material.SHEARS)) {

                CannonBallManager.getInstance().removeBall(event.getRightClicked().getEntityId());

                event.getRightClicked().getLocation().getWorld().playSound(event.getRightClicked().getLocation(),
                        Sound.ENTITY_SQUID_SQUIRT,2,1);

                if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
                    damageDefuseKit(event.getPlayer());

                event.getRightClicked().remove();
            }
        }

    }

    private void damageDefuseKit(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();
        Damageable damageable = (Damageable) itemMeta;

        damageable.setDamage(damageable.getDamage() + FWezCannon.getDefaultConfig().getInt("defuser_use_damage"));
        itemStack.setItemMeta((ItemMeta) damageable);

        if (itemStack.getType().getMaxDurability() < damageable.getDamage()) {
            itemStack.setAmount(0);
            player.getLocation().getWorld().playSound(player.getLocation(),Sound.ENTITY_ITEM_BREAK,1,1);
        }
    }
}

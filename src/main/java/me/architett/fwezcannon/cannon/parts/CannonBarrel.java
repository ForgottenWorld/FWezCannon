package me.architett.fwezcannon.cannon.parts;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;


public class CannonBarrel {

    private Dispenser dispenser;
    private ItemStack[] barrel;

    //todo: possibilità di avere esplosioni con effetti sui players coinvolti (es: slowness,blindness,etc)

    public CannonBarrel(Dispenser dispenser) {

        this.dispenser = dispenser;
        this.barrel = dispenser.getInventory().getContents();

    }

    public Integer getExplosiveAmount() {
        int tntAmount = 0;
        for (ItemStack itemStack : barrel) {
            if (itemStack != null && itemStack.getType() == Material.TNT)
                tntAmount += itemStack.getAmount();
        }
        return tntAmount;
    }


    public void clearBarrel() { dispenser.getInventory().clear(); }

}

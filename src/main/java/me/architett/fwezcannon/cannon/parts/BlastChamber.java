package me.architett.fwezcannon.cannon.parts;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlastFurnace;
import org.bukkit.inventory.ItemStack;

public class BlastChamber {

    private final BlastFurnace blastFurnace;

    public BlastChamber(BlastFurnace blastFurnace) {
        this.blastFurnace  = blastFurnace;
    }

    public Integer getGunpowderAmount() {

        ItemStack itemStack = blastFurnace.getInventory().getSmelting();

        if (itemStack == null || itemStack.getType() != Material.GUNPOWDER)
            return 0;
        else
            return itemStack.getAmount();

    }

    public Integer getWeight() {

        ItemStack itemStack = blastFurnace.getInventory().getFuel();

        if (itemStack == null || !Tag.LOGS.getValues().contains(itemStack.getType()))
            return 0;
        else
            return itemStack.getAmount();

    }

    public void clearPropellant() {
        blastFurnace.getInventory().setSmelting(null);
    }


}

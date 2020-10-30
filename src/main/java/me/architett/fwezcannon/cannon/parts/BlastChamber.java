package me.architett.fwezcannon.cannon.parts;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlastFurnace;
import org.bukkit.inventory.ItemStack;

public class BlastChamber {

    private BlastFurnace blastFurnace;

    private ItemStack propellant;
    private ItemStack wasteMaterial;
    private ItemStack weightMaterial;

    public BlastChamber(BlastFurnace blastFurnace) {
        this.blastFurnace  = blastFurnace;
        this.propellant = blastFurnace.getInventory().getSmelting();
        this.wasteMaterial = blastFurnace.getInventory().getResult();
        this.weightMaterial = blastFurnace.getInventory().getFuel();
    }

    public Integer getPropellant() {

        if (propellant == null)
            return 0;
        else if (propellant.getType() != Material.GUNPOWDER)
            return -1;
        else
            return propellant.getAmount();

    }

    public Integer getWaste() {

        if (wasteMaterial == null)
            return 0;
        else if (wasteMaterial.getType() != Material.CHARCOAL)
            return -1;
        else
            return wasteMaterial.getAmount();

    }

    public Integer getWeight() {

        if (weightMaterial == null)
            return 0;
        else if (!Tag.LOGS.getValues().contains(weightMaterial.getType()))
            return -1;
        else
            return weightMaterial.getAmount();

    }

    public void clearPropellant() {
        blastFurnace.getInventory().setSmelting(null);
    }

    public void addWaste(int value) {
        if (wasteMaterial != null && wasteMaterial.getType() == Material.CHARCOAL)
            blastFurnace.getInventory().setResult(wasteMaterial.add(value));
        else
            blastFurnace.getInventory().setResult(new ItemStack(Material.CHARCOAL,value));
    }

}

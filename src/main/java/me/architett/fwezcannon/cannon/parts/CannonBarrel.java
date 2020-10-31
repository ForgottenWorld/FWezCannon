package me.architett.fwezcannon.cannon.parts;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class CannonBarrel {

    private Dispenser dispenser;
    private ItemStack[] barrel;

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

    public List<Material> getRecipe() {

        //stream ?
        if (barrel.length != 9)
            return null;

        List<Material> recipeList = new ArrayList<>();

        for (ItemStack itemStack : barrel) {

            if (itemStack == null)
                recipeList.add(Material.AIR);
            else
                recipeList.add(itemStack.getType());

        }
        return recipeList;
    }

    public void clearBarrel() { dispenser.getInventory().clear(); }

}

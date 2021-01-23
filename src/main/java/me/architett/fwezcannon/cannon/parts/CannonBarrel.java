package me.architett.fwezcannon.cannon.parts;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CannonBarrel {

    private final Dispenser dispenser;

    public CannonBarrel(Dispenser dispenser) {

        this.dispenser = dispenser;

    }

    public int getTNTamount() {

        return Arrays.stream(dispenser.getInventory().getContents()).filter(Objects::nonNull)
                .filter(itemStack -> itemStack.getType().equals(Material.TNT))
                .mapToInt(ItemStack::getAmount).sum();
    }

    public List<Material> getRecipe() {

        return Arrays.stream(dispenser.getInventory().getContents())
                .map(itemStack -> Objects.isNull(itemStack) ? new ItemStack(Material.AIR) : itemStack)
                .map(ItemStack::getType).collect(Collectors.toList());

    }

    public void reduceInventory() {
        Arrays.stream(this.dispenser.getInventory().getContents())
                .filter(Objects::nonNull).forEach(itemStack -> itemStack.setAmount(itemStack.getAmount() - 1));
    }

}

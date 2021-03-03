package me.architett.fwezcannon.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.DispenserGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import me.architett.fwezcannon.cannon.ball.ShotRecipeManager;
import me.architett.fwezcannon.cannon.ball.ShotType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RecipeGUI {

    public RecipeGUI(ShotType shotType, Player player) {
        DispenserGui dispenserGui = new DispenserGui(ChatColor.DARK_GRAY
                + shotType.toString().replace("_", " ") + " SHOT");
        OutlinePane pane = new OutlinePane(0,0,3,3);

        for (Material material : ShotRecipeManager.getInstance().getShotRecipe(shotType)) {
            pane.addItem(new GuiItem(new ItemStack(material)));
        }
        dispenserGui.getContentsComponent().addPane(pane);
        dispenserGui.setOnGlobalClick(e-> e.setCancelled(true));
        dispenserGui.show(player);
    }
}

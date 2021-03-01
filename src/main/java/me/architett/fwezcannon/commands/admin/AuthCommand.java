package me.architett.fwezcannon.commands.admin;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.CannonManager;
import me.architett.fwezcannon.commands.SubCommand;
import me.architett.fwezcannon.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class AuthCommand extends SubCommand {
    @Override
    public String getName() {
        return "auth";
    }

    @Override
    public String getDescription() {
        return "add/remove authorization";
    }

    @Override
    public String getSyntax() {
        return "/fwezcannon auth";
    }

    @Override
    public String getPermission() {
        return "fwezcannon.auth";
    }

    @Override
    public int getArgsRequired() {
        return 1;
    }

    @Override
    public void perform(Player sender, String[] args) {
        if (args.length > 1 && args[1].equalsIgnoreCase("clear")) {
            CannonManager.getInstance().clearAutorizedCannon();
            sender.sendMessage(MessageUtil.format(FWezCannon.getDefaultConfig().getString("string.auth_cannons_clear")));
            return;
        }

        Block block = sender.getTargetBlock(10);

        if (block == null || !block.getType().equals(Material.BLAST_FURNACE)) {
            sender.sendMessage(MessageUtil.format(FWezCannon.getDefaultConfig().getString("string.auth_invalid_block")));
            return;
        }

        if (CannonManager.getInstance().isAutorizedCannon(block.getLocation())) {
            CannonManager.getInstance().removeAutorizedCannon(block.getLocation());
            sender.sendMessage(MessageUtil.format(FWezCannon.getDefaultConfig().getString("string.auth_cannon_removed")));
            return;
        }

        CannonManager.getInstance().addAutorizedCannon(block.getLocation());
        sender.sendMessage(MessageUtil.format(FWezCannon.getDefaultConfig().getString("string.auth_cannon_added")));

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}

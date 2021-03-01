package me.architett.fwezcannon.commands.admin;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.CannonManager;
import me.architett.fwezcannon.cannon.ball.ShotRecipeManager;
import me.architett.fwezcannon.commands.SubCommand;
import me.architett.fwezcannon.config.ConfigurationManager;
import me.architett.fwezcannon.util.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "reload config. WARNING: All authorized cannons will be removed";
    }

    @Override
    public String getSyntax() {
        return "/fwezcannon reload";
    }

    @Override
    public String getPermission() {
        return "fwezcannon.reload";
    }

    @Override
    public int getArgsRequired() {
        return 1;
    }

    @Override
    public void perform(Player sender, String[] args) {

        ConfigurationManager.getInstance().reloadDefaultConfig();
        ShotRecipeManager.getInstance().clearContainer();
        ShotRecipeManager.getInstance().buildContainer();
        CannonManager.getInstance().clearAutorizedCannon();
        sender.sendMessage(MessageUtil.format(FWezCannon.getDefaultConfig().getString("string.reload_success")));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}

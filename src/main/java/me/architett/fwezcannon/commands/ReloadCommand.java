package me.architett.fwezcannon.commands;

import me.architett.fwezcannon.cannon.util.ShootRecipeManager;
import me.architett.fwezcannon.config.ConfigurationManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player))
            return false;

        if (!commandSender.hasPermission("fwezcannon.reload")) {
            commandSender.sendMessage(ChatColor.RED + "Error: can't run command");
            return false;
        }

        ConfigurationManager.getInstance().reloadDefaultConfig();
        ShootRecipeManager.getInstance().clearContainer();
        ShootRecipeManager.getInstance().buildContainer();
        commandSender.sendMessage(ChatColor.GREEN + "Reloaded config successfully");

        return false;
    }
}

package me.architett.fwezcannon.commands.user;

import me.architett.fwezcannon.FWezCannon;
import me.architett.fwezcannon.cannon.ball.ShotRecipeManager;
import me.architett.fwezcannon.cannon.ball.ShotType;
import me.architett.fwezcannon.commands.SubCommand;
import me.architett.fwezcannon.gui.RecipeGUI;
import me.architett.fwezcannon.util.MessageUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class InfoCommand extends SubCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "returns info and shot recipe";
    }

    @Override
    public String getSyntax() {
        return "/fwezcannon info";
    }

    @Override
    public String getPermission() {
        return "fwezcannon.info";
    }

    @Override
    public int getArgsRequired() {
        return 1;
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (args.length == 2) {
            switch (args[1].toUpperCase()) {
                case "BLIND":
                    if (!FWezCannon.getDefaultConfig().getBoolean("blind_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.BLIND,sender);
                    break;
                case "SLOW":
                    if (!FWezCannon.getDefaultConfig().getBoolean("slow_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.SLOW,sender);
                    break;
                case "FIRE":
                    if (!FWezCannon.getDefaultConfig().getBoolean("fire_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.FIRE,sender);
                    break;
                case "POISON":
                    if (!FWezCannon.getDefaultConfig().getBoolean("poison_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.POISON,sender);
                    break;
                case "NOGRAVITY":
                    if (!FWezCannon.getDefaultConfig().getBoolean("nogravity_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.NOGRAVITY,sender);
                    break;
                case "HUGE":
                    if (!FWezCannon.getDefaultConfig().getBoolean("huge_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.HUGE,sender);
                    break;
                case "MULTI":
                    if (!FWezCannon.getDefaultConfig().getBoolean("multi_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.MULTI,sender);
                    break;
                case "BOUNCE":
                    if (!FWezCannon.getDefaultConfig().getBoolean("bounce_shot.enable"))
                        break;
                    new RecipeGUI(ShotType.BOUNCE,sender);
                    break;
                default:

            }
            return;
        }

        sender.sendMessage(MessageUtil.cannonInfoHeader());
        sender.sendMessage(MessageUtil.formatListMessage("AUTH CANNON : " + ChatColor.BOLD + ""
                + ChatColor.GOLD + FWezCannon.getDefaultConfig().getString("authorized_cannons_only")));
        sender.sendMessage(MessageUtil.formatListMessage("ALLOWED WORLD : " + ChatColor.BOLD + ""
                + ChatColor.GOLD + FWezCannon.getDefaultConfig().getStringList("enabled_world")));
        sender.sendMessage(MessageUtil.formatListMessage("DEFUSER ENABLED : " + ChatColor.BOLD + ""
                + ChatColor.GOLD + FWezCannon.getDefaultConfig().getString("enable_defuser")));
        sender.sendMessage(MessageUtil.formatListMessage("SPECIAL SHOT ENABLED : "));
        for (ShotType shotType : ShotRecipeManager.getInstance().getShotTypeContainer().values()) {
            TextComponent msg = new TextComponent(shotType.toString());
            msg.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            msg.setBold(true);
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ezc info " + shotType.toString()));
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("Click to see " + ChatColor.GOLD
                    + shotType.toString().toLowerCase() + " shot" + ChatColor.RESET + " recipe!")));
            sender.sendMessage(new TextComponent(ChatColor.BOLD + "  >> "),msg);
        }
        sender.sendMessage(MessageUtil.chatFooter());
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            return ShotRecipeManager.getInstance().getShotTypeContainer()
                    .values().stream().map(Enum::toString).collect(Collectors.toList());
        }
        return null;
    }
}

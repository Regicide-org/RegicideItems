package org.regicide.regicideitems.command;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.regicide.regicideitems.RegicideItems;
import org.regicide.regicideitems.entities.items.CustomItemManager;

public class GiveItem implements CommandExecutor {
    private final RegicideItems plugin;

    public GiveItem(RegicideItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }


        if (args.length != 2) {
            sender.sendMessage("Usage: /giveitem <player> <item>");
            return true;
        }

        Player targetPlayer = plugin.getServer().getPlayer(args[0]);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage("Player " + args[0] + " is not online!");
            return true;
        }

        String itemID = args[1];
        if (!CustomItemManager.hasCustomItem(itemID)) {
            sender.sendMessage("Custom item with ID " + itemID + " not found!");
            return true;
        }

        targetPlayer.getInventory().addItem(CustomItemManager.getCustomItem(itemID));
        targetPlayer.sendMessage("You received a custom item!");

        return true;
    }
}


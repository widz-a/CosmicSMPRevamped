package withicality.csmp.global.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import withicality.csmp.api.CosmicCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScrambleCommand extends CosmicCommand {
    public ScrambleCommand() {
        super("scramble", "Scrambles player's inventory contents", "/scramble <player>", new ArrayList<>(), "csmp.scramble");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }
        Player victim = Bukkit.getPlayerExact(args[0]);
        if (victim == null) {
            noPlayerFound(args[0], sender);
            return;
        }

        List<ItemStack> inv = Arrays.asList(victim.getInventory().getStorageContents());
        Collections.shuffle(inv);
        victim.getInventory().setStorageContents(inv.toArray(new ItemStack[0]));
        sender.sendMessage(ChatColor.AQUA + "Scrambled " + ChatColor.DARK_AQUA + victim.getName() + ChatColor.AQUA + "'s inventory.");
    }
}

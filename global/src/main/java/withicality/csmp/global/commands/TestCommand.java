package withicality.csmp.global.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.LootManager;
import withicality.csmp.global.CosmicGlobal;

import java.util.Arrays;
import java.util.Map;

import static withicality.csmp.api.LootManager.getItem;

public class TestCommand extends CosmicCommand {
    public TestCommand() {
        super("test", "Forces the demo screen to popup", "/demo <player>", ImmutableList.of(), "csmp.test");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        try {
            player.getInventory().addItem(getItem(args[0], args[1]));



            /*for (String key : config.getKeys(true)) {
                player.sendMessage(key + ":");
                /*for (String x : Arrays.asList("type", "chance", "lottable", "min", "max")) {
                    player.sendMessage(ChatColor.GRAY + key + "." + x);
                    player.sendMessage("- " + x + ": " + config.getString(key + "." + x));
                }
            }*/
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + e.toString());
            for (StackTraceElement s : e.getStackTrace()) {
                sender.sendMessage(ChatColor.RED + "\t" + s.toString());
            }
        }
    }
}

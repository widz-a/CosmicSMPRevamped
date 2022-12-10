package withicality.csmp.global.commands.utils;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.SchematicManager;

import java.util.List;

public class SchemCommand extends CosmicCommand {
    public SchemCommand() {
        super("schemtest", "Paste a schematic", "/schemtest <schematic file> <x> <y> <z> <world>\n/schemtest list\n", ImmutableList.of(), "csmp.schem");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        String schem = args[0];

        if (schem.equalsIgnoreCase("list")) {
            sender.sendMessage(APIStuff.HR);
            sender.sendMessage(String.join("\n", SchematicManager.get()));
            sender.sendMessage(APIStuff.HR);
            return;
        }

        if (args.length < 5) {
            sendUsage(sender);
            return;
        }

        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        boolean a = SchematicManager.load(new Location(Bukkit.getWorld(args[4]), x, y, z), schem);
        player.sendMessage(a ? ChatColor.GREEN + "Success!" : ChatColor.RED + "Failed!");
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }
}

package withicality.csmp.global.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.SchematicManager;

public class SchemCommand extends CosmicCommand {
    public SchemCommand() {
        super("schemtest", "Paste a schematic", "/schemtest <schematic file> [<x>] [<y>] [<z>]\n/schemtest list\n", ImmutableList.of(), "csmp.schem");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (!isPlayer(sender)) {
            sender.sendMessage(getNotPlayerMessage());
            return;
        }
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        String schem = args[0];
        if (schem.equalsIgnoreCase("list")) {
            player.sendMessage(APIStuff.HR);
            player.sendMessage(String.join("\n", SchematicManager.get()));
            player.sendMessage(APIStuff.HR);
            return;
        }
        Location loc = player.getLocation();
        int x = args.length < 4 ? loc.getBlockX() : Integer.parseInt(args[1]);
        int y = args.length < 4 ? loc.getBlockY() : Integer.parseInt(args[2]);
        int z = args.length < 4 ? loc.getBlockZ() : Integer.parseInt(args[3]);
        boolean a = SchematicManager.load(new Location(loc.getWorld(), x, y, z), schem);
        player.sendMessage(a ? ChatColor.GREEN + "Success!" : ChatColor.RED + "Failed!");
    }
}

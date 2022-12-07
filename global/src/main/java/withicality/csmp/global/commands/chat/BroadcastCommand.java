package withicality.csmp.global.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.CosmicCommand;

import java.util.Collections;

public class BroadcastCommand extends CosmicCommand {
    public BroadcastCommand() {
        super("broadcast", "Broadcast to the server. The default color is " + ChatColor.GOLD + "GOLD", "/broadcast <message>", Collections.singletonList("bc"), "csmp.broadcast");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
    }
}

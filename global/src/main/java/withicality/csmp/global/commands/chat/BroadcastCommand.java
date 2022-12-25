package withicality.csmp.global.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import withicality.csmp.api.CosmicCommand;

public class BroadcastCommand extends CosmicCommand {
    public BroadcastCommand() {
        super("broadcast|bc", "Broadcast to the server. The default color is " + ChatColor.GOLD + "GOLD", "/broadcast <message>", "csmp.broadcast");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
    }
}

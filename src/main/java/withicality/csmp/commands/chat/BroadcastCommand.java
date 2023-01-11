package withicality.csmp.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import withicality.csmp.CosmicCommand;

public class BroadcastCommand extends CosmicCommand {
    public BroadcastCommand() {
        super("broadcast|bc", "Broadcast to the server. The default color is " + ChatColor.WHITE + "WHITE", "/broadcast <message>", "csmp.broadcast");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        Bukkit.broadcastMessage(ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
    }
}

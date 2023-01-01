package withicality.csmp.api;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.command.SimpleCommand;

public abstract class CosmicCommand extends SimpleCommand {

    private String[] usag;

    public CosmicCommand(String name, String description, String usageMessage, String permission) {
        super(name);
        setDescription(description);
        setPermission(permission);

        usag = new String[]{usageMessage};
    }

    public CosmicCommand(String name, String description, String usageMessage) {
        super(name);
        setDescription(description);
        setPermission(null);

        usag = new String[]{usageMessage};
    }


    @Override
    protected String[] getMultilineUsageMessage() {
        return usag == null ? super.getMultilineUsageMessage() : usag;
    }

    public CosmicCommand(String name) {
        super(name);
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(APIStuff.HR);
        sender.sendMessage(ChatColor.DARK_AQUA + "Usage: " + ChatColor.AQUA + getUsage() + " " + getDescription());
        sender.sendMessage(APIStuff.HR);
    }

    public String noPlayerFound(String player) {
        return ChatColor.RED + "No player named " + ChatColor.WHITE + player + ChatColor.RED + " is connected to this server.";
    }

}

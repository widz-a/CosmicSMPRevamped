package withicality.csmp.api;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import withicality.withicalutilities.APIManager;
import withicality.withicalutilities.command.WithicalCommand;

import java.util.List;

public abstract class CosmicCommand extends WithicalCommand {

    public static void register(CosmicCommand command) {
        String[] array =  command.getClass().getPackage().getName().split("\\.");
        APIManager.registerCommand(command, MainClass.getPlugin(MainClass.class), "csmp." + array[array.length - 1]);
    }

    public CosmicCommand(String name, String description, String usageMessage, List<String> aliases, String permission) {
        super(name, description, usageMessage, aliases, permission);
    }

    public CosmicCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public CosmicCommand(String name) {
        super(name);
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(APIStuff.HR);
        sender.sendMessage(ChatColor.DARK_AQUA + "Usage: " + ChatColor.AQUA + getUsage() + " " + getDescription());
        sender.sendMessage(APIStuff.HR);
    }

    public void noPlayerFound(String player, CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "No player named " + ChatColor.WHITE + player + ChatColor.RED + " is connected to this server.");
    }

}

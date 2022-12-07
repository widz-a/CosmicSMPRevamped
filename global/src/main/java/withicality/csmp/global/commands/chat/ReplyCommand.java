package withicality.csmp.global.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.MessageManager;

import java.util.Arrays;
import java.util.UUID;

public class ReplyCommand extends CosmicCommand {
    public ReplyCommand() {
        super(
                "reply",
                "Quickly reply to the last player to message you or show the player you are messaging to.",
                "/reply [message] (It shows who you are replying to when there is no message)",
                Arrays.asList("r","rep")
        );
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (player == null) {
            sender.sendMessage(getNotPlayerMessage());
            return;
        }

        UUID uuid = MessageManager.getReplyTo(player);

        if (uuid == null) {
            player.sendMessage(ChatColor.RED + "You have nobody to reply to.");
            return;
        }

        String name = Bukkit.getOfflinePlayer(uuid).getName();

        if (args.length == 0) {
            player.sendMessage(ChatColor.AQUA + "You are currently messaging " + ChatColor.DARK_AQUA + name + ChatColor.AQUA + ".");
            return;
        }

        Player receiver = Bukkit.getPlayer(uuid);
        if (receiver == null) {
            noPlayerFound(name, player);
            return;
        }

        String message = String.join(" ", args);
        MessageManager.send(player, receiver, message);

    }
}

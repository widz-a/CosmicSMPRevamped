package withicality.csmp.global.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.manager.MessageManager;

import java.util.UUID;

public class ReplyCommand extends CosmicCommand {
    public ReplyCommand() {
        super(
                "reply|r|rep",
                "Quickly reply to the last player to message you or show the player you are messaging to.",
                "/reply [message] (It shows who you are replying to when there is no message)"
        );
    }

    @Override
    protected void onCommand() {
        checkConsole();

        Player player = (Player) sender;
        UUID uuid = MessageManager.getReplyTo(player);
        checkNotNull(uuid, "You have nobody to reply to.");

        String name = Bukkit.getOfflinePlayer(uuid).getName();
        checkArgs(1, ChatColor.AQUA + "You are currently messaging " + ChatColor.DARK_AQUA + name + ChatColor.AQUA + ".");

        Player receiver = Bukkit.getPlayer(uuid);
        checkNotNull(receiver, noPlayerFound(name));

        String message = String.join(" ", args);
        MessageManager.send(player, receiver, message);
    }
}

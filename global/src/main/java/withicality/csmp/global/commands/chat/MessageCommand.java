package withicality.csmp.global.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.MessageManager;

import java.util.Arrays;

public class MessageCommand extends CosmicCommand {
    public MessageCommand() {
        super(
                "message",
                "Send a private message to the specified player",
                "/message <player> <message>",
                Arrays.asList("w","m","t","pm","tell","whisper","msg")
        );
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (player == null) {
            sender.sendMessage(getNotPlayerMessage());
            return;
        }
        if (args.length < 2) {
            sendUsage(sender);
            return;
        }

        Player receiver = Bukkit.getPlayerExact(args[0]);
        if (receiver == null) {
            noPlayerFound(args[0], player);
            return;
        }

        String message = String.join(" ", args).substring(args[0].length() + 1);
        MessageManager.send(player, receiver, message);

    }
}

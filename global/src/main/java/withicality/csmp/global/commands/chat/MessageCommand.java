package withicality.csmp.global.commands.chat;

import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.manager.MessageManager;

public class MessageCommand extends CosmicCommand {
    public MessageCommand() {
        super(
                "message|w|m|t|pm|tell|whisper|msg",
                "Send a private message to the specified player",
                "/message <player> <message>"
        );

        setMinArguments(2);
    }


    @Override
    protected void onCommand() {
        checkConsole();
        Player player = (Player) sender;
        checkArgs(1, "Please specify player's name");
        checkArgs(2, "Please specify the message");

        Player receiver = APIStuff.getPlayer(args[0], player);
        checkNotNull(receiver, noPlayerFound(args[0]));

        String message = String.join(" ", args).substring(args[0].length() + 1);
        MessageManager.send(player, receiver, message);
    }
}

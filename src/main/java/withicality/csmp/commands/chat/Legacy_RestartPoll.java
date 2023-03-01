package withicality.csmp.commands.chat;


import withicality.csmp.CosmicCommand;
import withicality.csmp.manager.RestartPollManager;

import java.util.Arrays;
import java.util.Locale;

public class Legacy_RestartPoll extends CosmicCommand {
    public Legacy_RestartPoll() {
        super("restartpoll", "should it restarts?", "/restartpoll");
    }

    @Override
    protected void onCommand() {
        RestartPollManager manager = RestartPollManager.getInstance();
        if (args.length == 0) {
            checkPerm("csmp.restart");
            checkBoolean(!manager.denied(), "A polling is in session, try again in: " + manager.endIn() + "s");
            manager.createPoll();
            return;
        }

        checkConsole();
        checkBoolean(Arrays.asList("accept", "deny").contains(args[0].toLowerCase(Locale.ROOT)), "no perm");
        sender.sendMessage(args[0]);
    }
}

package withicality.csmp.commands.utils;

import withicality.csmp.CosmicCommand;

public class TestCommand extends CosmicCommand {
    public TestCommand() {
        super("test", "Forces the demo screen to popup", "/demo <player>", "csmp.test");
    }

    @Override
    protected void onCommand() {
        sender.sendMessage("unused");
    }
}

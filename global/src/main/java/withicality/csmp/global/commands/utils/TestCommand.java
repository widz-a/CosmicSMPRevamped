package withicality.csmp.global.commands.utils;

import withicality.csmp.api.CosmicCommand;

public class TestCommand extends CosmicCommand {
    public TestCommand() {
        super("test", "Forces the demo screen to popup", "/demo <player>", "csmp.test");
    }

    @Override
    protected void onCommand() {
        sender.sendMessage("unused");
    }
}

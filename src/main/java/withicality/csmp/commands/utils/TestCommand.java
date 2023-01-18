package withicality.csmp.commands.utils;

import org.bukkit.entity.Player;
import withicality.csmp.CosmicCommand;
import withicality.csmp.manager.power.HotbarManager;

public class TestCommand extends CosmicCommand {
    public TestCommand() {
        super("test", "Forces the demo screen to popup", "/demo <player>", "csmp.test");
    }

    @Override
    protected void onCommand() {
        checkConsole();
        Player player = (Player) sender;
        player.openInventory(HotbarManager.getInventory(player));
    }
}

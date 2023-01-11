package withicality.csmp.commands.troll;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import withicality.csmp.manager.PlayerManager;
import withicality.csmp.CosmicCommand;

public class FakeopCommand extends CosmicCommand {
    public FakeopCommand() {
        super("fakeop", "To fake op a player", "/fakeop <player>", "csmp.demotroll");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        Player victim = PlayerManager.getPlayer(args[0], sender);
        checkNotNull(victim, noPlayerFound(args[0]));

        victim.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "["+ sender.getName() + ": Made " + victim.getName() + " a server operator]");
        sender.sendMessage(ChatColor.AQUA + "Fake-opped " + ChatColor.DARK_AQUA + victim.getName() + ChatColor.AQUA + ".");
    }
}

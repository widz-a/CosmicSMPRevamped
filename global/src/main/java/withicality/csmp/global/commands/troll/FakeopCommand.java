package withicality.csmp.global.commands.troll;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;

public class FakeopCommand extends CosmicCommand {
    public FakeopCommand() {
        super("fakeop", "To fake op a player", "/fakeop <player>", "csmp.demotroll");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        Player victim = APIStuff.getPlayer(args[0], sender);
        checkNotNull(victim, noPlayerFound(args[0]));

        victim.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "["+ sender.getName() + ": Made " + victim.getName() + " a server operator]");
        sender.sendMessage(ChatColor.AQUA + "Fake-opped " + ChatColor.DARK_AQUA + victim.getName() + ChatColor.AQUA + ".");
    }
}

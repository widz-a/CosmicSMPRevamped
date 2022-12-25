package withicality.csmp.global.commands.loop;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.api.CosmicCommand;

import java.util.ArrayList;
import java.util.List;

import static withicality.csmp.global.commands.loop.RunloopCommand.runnables;

public class StoploopCommand extends CosmicCommand {
    public StoploopCommand() {
        super("stoploop", "Stop a loop made by /runloop", "/stoploop <id>", "csmp.stoploop");
        setMinArguments(1);
    }

    public List<String> tab(CommandSender sender, String[] args) {
        return new ArrayList<>(runnables.keySet());
    }

    @Override
    protected void onCommand() {
        checkBoolean(runnables.containsKey(args[0]), ChatColor.RED + "There is no task running using this id.");

        BukkitRunnable runnable = runnables.get(args[0]);
        runnable.cancel();
        runnables.remove(args[0]);
        sender.sendMessage(ChatColor.GREEN + "Stopped the loop");
    }

    @Override
    protected List<String> tabComplete() {
        return completeLastWord(runnables.keySet());
    }
}

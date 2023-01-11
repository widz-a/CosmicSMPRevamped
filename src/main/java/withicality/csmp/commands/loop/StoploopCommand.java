package withicality.csmp.commands.loop;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.CosmicCommand;

import java.util.ArrayList;
import java.util.List;

public class StoploopCommand extends CosmicCommand {
    public StoploopCommand() {
        super("stoploop", "Stop a loop made by /runloop", "/stoploop <id>", "csmp.stoploop");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        checkBoolean(RunloopCommand.runnables.containsKey(args[0]), ChatColor.RED + "There is no task running using this id.");

        BukkitRunnable runnable = RunloopCommand.runnables.get(args[0]);
        runnable.cancel();
        RunloopCommand.runnables.remove(args[0]);
        sender.sendMessage(ChatColor.GREEN + "Stopped the loop");
    }

    @Override
    protected List<String> tabComplete() {
        return completeLastWord(RunloopCommand.runnables.keySet());
    }
}

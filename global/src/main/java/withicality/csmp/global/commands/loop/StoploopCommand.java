package withicality.csmp.global.commands.loop;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.global.CosmicGlobal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static withicality.csmp.global.commands.loop.RunloopCommand.runnables;

public class StoploopCommand extends CosmicCommand {
    public StoploopCommand() {
        super("stoploop", "Stop a loop made by /runloop", "/stoploop <id>", ImmutableList.of(), "csmp.stoploop");
    }

    @Override
    public void run(CommandSender sender, Player a, String[] args) {
        if (args.length < 1) {
            sendUsage(sender);
            return;
        }

        if (!runnables.containsKey(args[0])) {
            sender.sendMessage(ChatColor.RED + "There is no task running using this id.");
            return;
        }

        BukkitRunnable runnable = runnables.get(args[0]);
        runnable.cancel();
        runnables.remove(args[0]);
        sender.sendMessage(ChatColor.GREEN + "Stopped the loop");
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return new ArrayList<>(runnables.keySet());
    }
}

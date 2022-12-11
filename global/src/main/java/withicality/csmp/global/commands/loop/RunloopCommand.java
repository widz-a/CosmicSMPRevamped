package withicality.csmp.global.commands.loop;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.global.CosmicGlobal;

import java.util.HashMap;

public class RunloopCommand extends CosmicCommand {

    protected static final HashMap<String, BukkitRunnable> runnables = new HashMap<>();

    public RunloopCommand() {
        super("runloop", "Force console to run a command on loop (<delay> or <period> is equal to <second>*20 (minecraft tick))", "/runloop <id> <delay> <period> <command without />", ImmutableList.of(), "csmp.runloop");
    }

    @Override
    public void run(CommandSender sender, Player a, String[] args) {
        if (args.length < 4) {
            sendUsage(sender);
            return;
        }

        if (runnables.containsKey(args[0])) {
            sender.sendMessage(ChatColor.RED + "There is already a task running using this id.");
            return;
        }

        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        };

        try {
            runnable.runTaskTimer(CosmicGlobal.getPlugin(CosmicGlobal.class), Long.parseLong(args[1]), Long.parseLong(args[2]));
            runnables.put(args[0], runnable);

            sender.sendMessage(ChatColor.GREEN + "Running task: " + args[0]);
        } catch (NumberFormatException e) {
            sendUsage(sender);
            sender.sendMessage(ChatColor.RED + "Make sure <delay> and <period> fields are integer number!");
        }
    }
}

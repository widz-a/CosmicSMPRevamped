package withicality.csmp.commands.loop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.CosmicPlugin;
import withicality.csmp.CosmicCommand;

import java.util.HashMap;
import java.util.List;

public class RunloopCommand extends CosmicCommand {

    protected static final HashMap<String, BukkitRunnable> runnables = new HashMap<>();

    public RunloopCommand() {
        super("runloop", "Force console to run a command on loop (<delay> or <period> is equal to <second>*20 (minecraft tick))", "/runloop <id> <delay> <period> <command without />", "csmp.runloop");
        setMinArguments(4);
    }

    @Override
    protected void onCommand() {
        checkBoolean(!runnables.containsKey(args[0]), "There is already a task running using this id.");

        String command = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        };

        try {
            runnable.runTaskTimer(CosmicPlugin.getPlugin(CosmicPlugin.class), Long.parseLong(args[1]), Long.parseLong(args[2]));
            runnables.put(args[0], runnable);

            sender.sendMessage(ChatColor.GREEN + "Running task: " + args[0]);
        } catch (NumberFormatException e) {
            sendUsage(sender);
            sender.sendMessage(ChatColor.RED + "Make sure <delay> and <period> fields are integer number!");
        }
    }

    @Override
    protected List<String> tabComplete() {
        return NO_COMPLETE;
    }
}

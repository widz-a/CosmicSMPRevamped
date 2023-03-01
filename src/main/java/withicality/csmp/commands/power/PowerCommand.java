package withicality.csmp.commands.power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.mineacademy.fo.ReflectionUtil;
import withicality.csmp.CosmicCommand;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.OfflinePlayerManager;
import withicality.csmp.manager.power.HotbarManager;
import withicality.csmp.manager.power.PowerManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class PowerCommand extends CosmicCommand {
    public PowerCommand() {
        // /power <enable|disable|toggle> <power>|*  <player>|*[*] [world]
        super("power|powah", "p", "below", "csmp.power");
        //setMinArguments(1);
    }

    private final String check = "/power [(<enable|disable|toggle> <power>|*  [<player>|*[*]] [<world>|*])|save|load]";

    @Override
    protected String[] getMultilineUsageMessage() {
        return new String[] {
                "/power <enable|disable|toggle> <power>|*  [<player>|*[*]] [<world>|*]",
                "/power <save|load>"
        };
    }

    @Override
    protected void onCommand() {
        if (args.length == 0) {
            checkConsole();
            Player player = (Player) sender;
            player.openInventory(HotbarManager.getInventory(player));
            return;
        }

        checkBoolean(Arrays.asList("enable", "disable", "toggle", "save", "load").contains(args[0].toLowerCase(Locale.ROOT)), "Make sure the 1st argument is either enable, disable, toggle, save, or load.");

        try {
            if (Arrays.asList("save", "load").contains(args[0].toLowerCase(Locale.ROOT))) {
                PowerManager.class.getDeclaredMethod(args[0].toLowerCase(Locale.ROOT)).invoke(null);
                HotbarManager.class.getDeclaredMethod(args[0].toLowerCase(Locale.ROOT)).invoke(null);
                sender.sendMessage("ok");
                return;
            }

            checkArgs(2, check);
            Power[] powers = args[1].equals("*") ? Power.values() : new Power[]{ReflectionUtil.lookupEnumSilent(Power.class, args[1].toUpperCase())};
            OfflinePlayer[] players = args.length < 3 ? (sender instanceof Player ? new OfflinePlayer[] { ((OfflinePlayer) sender) } : new OfflinePlayer[0]) : args[2].equals("*") ? Bukkit.getOnlinePlayers().toArray(new OfflinePlayer[0]) : (args[2].equals("**") ? Bukkit.getOfflinePlayers() : new OfflinePlayer[]{OfflinePlayerManager.getOfflinePlayer(args[2])});
            World[] worlds = args.length < 4 ? (sender instanceof Player ? new World[] { ((Player) sender).getWorld() } : new World[0]) : (args[3].equals("*") ? Bukkit.getWorlds().toArray(new World[0]) : new World[]{Bukkit.getWorld(args[3])});

            Method method = PowerManager.class.getDeclaredMethod(args[0].toLowerCase(Locale.ROOT), Power.class, OfflinePlayer.class, World.class);
            for (Power power : powers) {
                if (power == null) continue;
                for (OfflinePlayer offline : players) {
                    if (offline == null) continue; //just in case...
                    for (World world : worlds) {
                        if (world == null) continue;
                        boolean c = (boolean) method.invoke(boolean.class, power, offline, world);
                        String action = args[0].equalsIgnoreCase("toggle") ? (c ? "enable" : "disable") : args[0].toLowerCase(Locale.ROOT);

                        sender.sendMessage(
                                ChatColor.GREEN + action + "d " + offline.getName() + "'s " + power.getName() + " in " + world.getName() + "."
                        );
                    }
                }
                //sender.sendMessage(ChatColor.AQUA + "Completed " + completed + "/" + tasks + " tasks.");
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            tellError("Something happened lol!");
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (Arrays.asList("save", "load").contains(args[0].toLowerCase(Locale.ROOT))) return NO_COMPLETE;

        return switch (args.length) {
            case 1 -> completeLastWord("enable", "disable", "toggle", "save", "load");
            case 2 -> completeLastWord(Power.actualValues(), "*");
            case 3 -> completeLastWord(OfflinePlayerManager.getOfflinePlayersS(), "*", "**");
            case 4 -> completeLastWord(completeLastWordWorldNames(), "*");
            default -> NO_COMPLETE;
        };
    }

    private OfflinePlayer[] getPlayers() {
        if (args.length < 3) {
            return sender instanceof Player ? new OfflinePlayer[] { ((OfflinePlayer) sender) } : new OfflinePlayer[0];
        } else {
            return args[2].equals("*") ? Bukkit.getOnlinePlayers().toArray(new OfflinePlayer[0]) : (args[2].equals("**") ? Bukkit.getOfflinePlayers() : new OfflinePlayer[]{OfflinePlayerManager.getOfflinePlayer(args[2])});
        }
    }

    private World[] world() {
        if (args.length < 4) {
            return sender instanceof Player ? new World[] { ((Player) sender).getWorld() } : new World[0];
        } else {
            return args[3].equals("*") ? Bukkit.getWorlds().toArray(new World[0]) : new World[]{Bukkit.getWorld(args[3])};
        }
    }

    private enum OptionKey {
        PLAYER, POWER, WORLD
    }

    private enum OptionVal {
        ONE, A
    }
}

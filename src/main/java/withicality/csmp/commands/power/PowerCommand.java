package withicality.csmp.commands.power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.mineacademy.fo.ReflectionUtil;
import withicality.csmp.CosmicCommand;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.OfflinePlayerManager;
import withicality.csmp.manager.PowerManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PowerCommand extends CosmicCommand {
    public PowerCommand() {
        // /power <enable|disable|toggle> <power>|*  <player>|*[*] [world]
        super("power|powah", "p", "below", "csmp.power");
        setMinArguments(1);
    }

    @Override
    protected String[] getMultilineUsageMessage() {
        return new String[] {
                "/power <enable|disable|toggle> <power>|*  <player>|*[*] [world]",
                "/power <save|load>"
        };
    }

    @Override
    protected void onCommand() {
        checkBoolean(Arrays.asList("enable", "disable", "toggle", "save", "load").contains(args[0].toLowerCase(Locale.ROOT)), "Make sure the 1st argument is either enable, disable, toggle, save, or load.");

        try {
            if (Arrays.asList("save", "load").contains(args[0].toLowerCase(Locale.ROOT))) {
                PowerManager.class.getDeclaredMethod(args[0].toUpperCase(Locale.ROOT)).invoke(null);
                sender.sendMessage("ok");
                return;
            }

            checkArgs(3, "/power <enable|disable|toggle> <power>|*  <player>|*[*] [world]");
            Power[] powers = args[1].equals("*") ? Power.values() : new Power[]{ReflectionUtil.lookupEnumSilent(Power.class, args[1].toUpperCase())};
            OfflinePlayer[] players = args[2].equals("*") ? Bukkit.getOnlinePlayers().toArray(new OfflinePlayer[0]) : (args[2].equals("**") ? Bukkit.getOfflinePlayers() : new OfflinePlayer[]{OfflinePlayerManager.getOfflinePlayer(args[2])});
            World[] worlds = args.length < 4 ? Bukkit.getWorlds().toArray(new World[0]) : new World[]{Bukkit.getWorld(args[3])};

            int completed = 0;
            int tasks = powers.length * players.length * worlds.length;

            Method method = PowerManager.class.getDeclaredMethod(args[0].toUpperCase(Locale.ROOT), Power.class, OfflinePlayer.class, World.class);
            for (Power power : powers) {
                if (power == null) continue;
                for (OfflinePlayer offline : players) {
                    if (offline == null) continue; //just in case...
                    for (World world : worlds) {
                        if (world == null) continue;
                        boolean c = (boolean) method.invoke(boolean.class, power, offline, world);
                        if (c) completed++;
                    }
                }
                sender.sendMessage(ChatColor.AQUA + "Completed " + completed + "/" + tasks + " tasks.");
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (Arrays.asList("save", "load").contains(args[0].toLowerCase(Locale.ROOT))) return NO_COMPLETE;

        return switch (args.length) {
            case 1 -> completeLastWord("enable", "disable", "toggle", "save", "load");
            case 2 -> completeLastWord(Power.values(), "*");
            case 3 -> completeLastWord(OfflinePlayerManager.getOfflinePlayersS(), "*", "**");
            case 4 -> completeLastWordWorldNames();
            default -> NO_COMPLETE;
        };
    }
}

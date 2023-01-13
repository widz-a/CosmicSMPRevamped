package withicality.csmp.commands.chat.power;

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

public class PowerCommand extends CosmicCommand {
    public PowerCommand() {
        // /power <enable|disable|toggle> <power>|*  <player>|*[*] [world]
        super("power|powah", "p", "ambatukam", "csmp.power");
        setMinArguments(3);
    }

    @Override
    protected void onCommand() {
        checkBoolean(Arrays.asList("enable", "disable", "toggle").contains(args[0]), "Make sure the 1st argument is either enable, disable or toggle.");

        Power[] powers = args[1].equals("*") ? Power.values() : new Power[] {ReflectionUtil.lookupEnumSilent(Power.class, args[1].toUpperCase())};
        OfflinePlayer[] players = args[2].equals("*") ? Bukkit.getOnlinePlayers().toArray(new OfflinePlayer[0]) : (args[2].equals("**") ? Bukkit.getOfflinePlayers() : new OfflinePlayer[]{OfflinePlayerManager.getOfflinePlayer(args[2])});
        World[] worlds = args.length < 4 ? Bukkit.getWorlds().toArray(new World[0]) : new World[] {Bukkit.getWorld(args[3])};

        int completed = 0;
        int tasks = powers.length * players.length * worlds.length;

        try {
            Method method = PowerManager.class.getDeclaredMethod(args[0], Power.class, OfflinePlayer.class, World.class);
            for (Power power : powers) {
                for (OfflinePlayer offline : players) {
                    for (World world : worlds) {
                        boolean c = (boolean) method.invoke(boolean.class, power, offline, world);
                        if (c) completed++;
                    }
                }
            }

            sender.sendMessage(ChatColor.AQUA + "Completed " + completed + "/" + tasks + " tasks.");

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> tabComplete() {
        return switch (args.length) {
            case 1 -> completeLastWord("enable", "disable", "toggle");
            case 2 -> completeLastWord(Power.values(), "*");
            case 3 -> completeLastWord(OfflinePlayerManager.getOfflinePlayersS(), "*", "**");
            case 4 -> completeLastWordWorldNames();
            default -> NO_COMPLETE;
        };
    }
}

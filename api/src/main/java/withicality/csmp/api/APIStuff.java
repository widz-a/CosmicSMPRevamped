package withicality.csmp.api;

import com.comphenix.protocol.ProtocolManager;
import com.google.common.base.Strings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class APIStuff {
    public static final String HR = ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + Strings.repeat(" ", 80);

    private static ProtocolManager manager;
    protected static void setManager(ProtocolManager m) {
        manager = m;
    }
    public static ProtocolManager getProtocolManager() {
        return manager;
    }

    public static Player getPlayer(String name, CommandSender sender) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) return null;
        if (!(sender instanceof Player)) return player;
        return ((Player) sender).canSee(player) ? player : null;
    }

    public static List<String> getPlayersCanBeSeen(Player looker) {
        List<String> matchedPlayers = new ArrayList<>();

        for (Player player : looker.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if (looker.canSee(player)) {
                matchedPlayers.add(name);
            }
        }

        matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
}

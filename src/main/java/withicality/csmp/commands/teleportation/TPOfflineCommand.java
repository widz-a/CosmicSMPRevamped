package withicality.csmp.commands.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import withicality.csmp.CosmicCommand;
import withicality.csmp.manager.OfflinePlayerManager;

import java.util.List;

public class TPOfflineCommand extends CosmicCommand {

    public TPOfflineCommand() {
        super("tpoffline|otp|offlinetp|tpoff|tpo", "Teleport to a player location even if he (she, they) is (are) offline", "/{label} <player>", "csmp.tpoffline");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        checkConsole();
        Player player = (Player) sender;
        OfflinePlayer offline = OfflinePlayerManager.getOfflinePlayer(args[0]);

        checkBoolean(offline.hasPlayedBefore(), offline.getName() + " has never joined before");
        Location location = offline.isOnline() && offline.getPlayer() != null ? offline.getPlayer().getLocation() : new OfflinePlayerManager(offline).getLocation();

        checkNotNull(location, "There are no recorded locations for " + offline.getName() + ".");
        player.teleport(location);

        player.sendMessage(ChatColor.AQUA + "Teleported to " + ChatColor.DARK_AQUA + offline.getName() + ChatColor.AQUA + "'s logout spot.");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getUniqueId().equals(player.getUniqueId())) return;
            if (!p.hasPermission(getPermission())) return;

            p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "[" + player.getName() + ": teleported to" + offline.getName() + "'s logout spot]");
        }

    }

    @Override
    protected List<String> tabComplete() {
        return completeLastWord(OfflinePlayerManager.getOfflinePlayersS());
    }
}

package withicality.csmp.manager;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerManager {
    private final Player player;

    public PlayerManager(Player player) {
        this.player = player;
    }

    public void setScoreboard(String title, List<String> lines) {
        FastBoard board = ScoreboardManager.getBoard(player);
        board.updateTitle(title);
        board.updateLines(lines);
    }

    public void resetScoreboard() {
        ScoreboardManager.removeBoard(player);
        ScoreboardManager.addBoard(player);
    }

    public void setScoreboard(String title, String... lines) {
        setScoreboard(title, Arrays.asList(lines));
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
            if (looker.canSee(player) || looker.getUniqueId().equals(player.getUniqueId())) matchedPlayers.add(player.getName());
        }

        matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
}

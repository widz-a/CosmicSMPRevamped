package withicality.csmp.listeners;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import withicality.csmp.manager.ScoreboardManager;

public class APIListener implements Listener {

    public APIListener() {
        addAll();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ScoreboardManager.addBoard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        ScoreboardManager.removeBoard(player);
    }

    public static void addAll() {
        Bukkit.getOnlinePlayers().forEach(ScoreboardManager::addBoard);
    }

    private void defaultboard(Player player, FastBoard board) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}

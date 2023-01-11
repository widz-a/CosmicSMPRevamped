package withicality.csmp.manager;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager {
    private static final HashMap<UUID, FastBoard> boards = new HashMap<>();

    public static FastBoard getBoard(Player player) {
        return boards.get(player.getUniqueId());
    }

    public static void addBoard(Player player) {
        FastBoard board = new FastBoard(player);
        boards.put(player.getUniqueId(), board);
    }

    public static void removeBoard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) board.delete();
    }

}

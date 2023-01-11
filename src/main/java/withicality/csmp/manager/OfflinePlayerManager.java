package withicality.csmp.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.UUID;

public class OfflinePlayerManager {

    private final OfflinePlayer offline;
    private final ConfigManager configInstance;

    public OfflinePlayerManager(OfflinePlayer offline) {
        this.offline = offline;
        configInstance = ConfigManager.getInstance();
    }

    public Location getLocation() {
        FileConfiguration config = configInstance.getCustomConfig("storageplayerdata21");
        return config.getLocation(offline.getUniqueId() + "." + "location");
    }

    public static OfflinePlayer getOfflinePlayer(String name) {
        FileConfiguration config = ConfigManager.getInstance().getCustomConfig("storageuuiddata21");
        String p = config.getString(name.toLowerCase());
        return p==null ? Bukkit.getOfflinePlayer(name) : Bukkit.getOfflinePlayer(UUID.fromString(p));
    }

    public static Set<String> getOfflinePlayersS() {
        FileConfiguration config = ConfigManager.getInstance().getCustomConfig("storageuuiddata21");
        return config.getKeys(false);
    }

}

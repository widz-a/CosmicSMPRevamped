package withicality.csmp.api.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import withicality.csmp.api.CosmicConfig;

import java.util.UUID;

public class OPlayerManager {

    private final OfflinePlayer offline;
    private final CosmicConfig configInstance;

    public OPlayerManager(OfflinePlayer offline) {
        this.offline = offline;
        configInstance = CosmicConfig.getInstance();
    }

    public Location getLocation() {
        FileConfiguration config = configInstance.getCustomConfig("storageplayerdata21");
        return config.getLocation(offline.getUniqueId() + "." + "location");
    }

    public static OfflinePlayer getOfflinePlayer(String name) {
        FileConfiguration config = CosmicConfig.getInstance().getCustomConfig("storageuuiddata21");
        String p = config.getString(name.toLowerCase());
        return p==null ? Bukkit.getOfflinePlayer(name) : Bukkit.getOfflinePlayer(UUID.fromString(p));
    }

}

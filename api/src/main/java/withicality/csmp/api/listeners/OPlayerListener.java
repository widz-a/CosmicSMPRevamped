package withicality.csmp.api.listeners;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import withicality.csmp.api.CosmicConfig;

import java.io.IOException;

public class OPlayerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        FileConfiguration config = CosmicConfig.getInstance().getCustomConfig("storageplayerdata21");
        config.set(player.getUniqueId() + "." + "location", location);
        CosmicConfig.getInstance().save("storageplayerdata21");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();

        FileConfiguration config = CosmicConfig.getInstance().getCustomConfig("storageuuiddata21");
        config.set(player.getName().toLowerCase(), player.getUniqueId().toString());
        CosmicConfig.getInstance().save("storageuuiddata21");
    }

}

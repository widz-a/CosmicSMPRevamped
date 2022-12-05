package withicality.csmp.api.listeners;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JoinLeaveVanishListener implements Listener {
    @EventHandler
    public void onVanish(PlayerHideEvent event) {
        if (event.isSilent()) return;
        Bukkit.getScheduler().runTaskAsynchronously(DiscordSRV.getPlugin(),
                () -> DiscordSRV.getPlugin().sendLeaveMessage(event.getPlayer(), event.getPlayer().getDisplayName() + " left the CosmicSMP."));
    }

    @EventHandler
    public void onAppear(PlayerShowEvent event) {
        if (event.isSilent()) return;
        Bukkit.getScheduler().runTaskAsynchronously(DiscordSRV.getPlugin(), () ->
                DiscordSRV.getPlugin().sendJoinMessage(event.getPlayer(), event.getPlayer().getDisplayName() + " joined the CosmicSMP."));
    }

}

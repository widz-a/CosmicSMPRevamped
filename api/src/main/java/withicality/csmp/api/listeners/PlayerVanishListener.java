package withicality.csmp.api.listeners;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import de.myzelyam.supervanish.SuperVanish;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import withicality.withicalutilities.entity.WPlayer;
import withicality.withicalutilities.event.PlayerUpdateEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerVanishListener implements Listener {
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

    @EventHandler
    public void onUpdate(PlayerUpdateEvent event) {
        SuperVanish superVanish = SuperVanish.getPlugin(SuperVanish.class);
        Player player = event.getPlayer();
        WPlayer wPlayer = new WPlayer(player);

        if (!superVanish.getVanishPlayer(player).isOnlineVanished()) {
            wPlayer.resetScoreboard();
            return;
        }


        StringBuilder tps = new StringBuilder();
        double[] recentTps = Bukkit.getTPS();
        for (int i = 0; i < recentTps.length; i++) {
            tps.append(formatTPS(recentTps[i])).append(i == recentTps.length - 1 ? "" : " ");
        }

        int vanished = superVanish.getVanishStateMgr().getOnlineVanishedPlayers().size();
        String online = ChatColor.AQUA.toString() + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ChatColor.GRAY + " [" + vanished + "]";

        int latency = player.spigot().getPing();
        String ping = (latency < 150 ? ChatColor.GREEN : latency < 300 ? ChatColor.GOLD : ChatColor.RED) + String.valueOf(latency) + "ms";

        List<String> lines = new ArrayList<>();
        lines.add(" ");
        lines.add("Players: " + online);
        lines.add("TPS: " + tps);
        lines.add("Ping: " + ping);
        lines.add(" ");
        wPlayer.setScoreboard(ChatColor.AQUA + "" + ChatColor.BOLD + "CosmicSMP", lines);
    }

    private String formatTPS(double tps) {
        return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED) + (tps > 20.0 ? "*" : "") + Math.min(Math.round(tps), 20.0);
    }

}

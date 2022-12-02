package withicality.csmp.global.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import withicality.csmp.api.MessageManager;

import java.util.UUID;

public class SocialSpyListeners implements Listener {
    @EventHandler
    public void onSussy(MessageManager.MessageEvent event) {
        String message = ChatColor.translateAlternateColorCodes('&', "" +
                "&7[&3SS&7] &b[" + event.getSender().getName() + " -> " + event.getReciever().getName() + "] &r" + event.getMessage());
        for (UUID spy : MessageManager.getSpies()) {
            Player player = Bukkit.getPlayer(spy);
            if (player != null) player.sendMessage(message);
        }
    }
}

package withicality.csmp.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MessageManager {
    private static final List<UUID> spies = new ArrayList<>();
    private static final HashMap<UUID, UUID> replies = new HashMap<>();

    public static void send(Player sender, Player reciever, String message) {
        sender.sendMessage(ChatColor.AQUA + "(To " + reciever.getDisplayName() + ChatColor.AQUA + ") " + message);
        reciever.sendMessage(ChatColor.AQUA + "(From " + sender.getDisplayName() + ChatColor.AQUA + ") " + message);
        reciever.playSound(reciever.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.VOICE, 1, 1);

        replies.put(sender.getUniqueId(), reciever.getUniqueId());
        replies.put(reciever.getUniqueId(), sender.getUniqueId());

        MessageEvent event = new MessageEvent(sender, reciever, message);
        Bukkit.getPluginManager().callEvent(event);

    }

    public static UUID getReplyTo(Player player) {
        return replies.get(player.getUniqueId());
    }

    public static class MessageEvent extends PlayerEvent {
        private final Player sender;
        private final Player reciever;
        private final String message;

        public MessageEvent(Player sender, Player reciever, String message) {
            super(sender);
            this.sender = sender;
            this.reciever = reciever;
            this.message = message;
        }

        private static final HandlerList handlers = new HandlerList();

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }

        public Player getReciever() {
            return reciever;
        }

        public Player getSender() {
            return sender;
        }

        public String getMessage() {
            return message;
        }
    }

    public static boolean containSpies(Player player) {
        return spies.contains(player.getUniqueId());
    }

    public static boolean updateSpies(Player player, boolean enable) {
        if (enable) {
            if (containSpies(player)) return false;
            spies.add(player.getUniqueId());
        } else {
            if (!containSpies(player)) return false;
            spies.remove(player.getUniqueId());
        }
        return true;
    }

    public static List<UUID> getSpies() {
        return spies;
    }
}

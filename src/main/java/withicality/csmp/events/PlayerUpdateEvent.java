package withicality.csmp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUpdateEvent extends PlayerEvent {
    @Override
    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
    private static final HandlerList handlers = new HandlerList();

    public PlayerUpdateEvent(Player who) {
        super(who);
    }
}
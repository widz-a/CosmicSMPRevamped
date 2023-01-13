package withicality.csmp.manager;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import withicality.csmp.enums.Power;

import java.util.*;

public class PowerManager {
    private static final Map<Power, Map<UUID, List<World>>> powers = new HashMap<>();

    static {
        for (Power power : Power.values()) {
            powers.put(power, new HashMap<>());
        }
    }

    public static boolean hasPower(Power power, OfflinePlayer player, World world) {
        return powers.get(power).get(player.getUniqueId()) != null && powers.get(power).get(player.getUniqueId()).contains(world);
    }

    public static boolean enable(Power power, OfflinePlayer player, World world) {
        if (hasPower(power, player, world)) return false;
        List<World> w = powers.get(power).get(player.getUniqueId());
        List<World> worlds = w == null ? new ArrayList<>() : w;
        worlds.add(world);
        powers.get(power).put(player.getUniqueId(), worlds);
        return true;
    }

    public static boolean disable(Power power, OfflinePlayer player, World world) {
        if (!hasPower(power, player, world)) return false;
        List<World> worlds = powers.get(power).get(player.getUniqueId());
        worlds.remove(world);
        powers.get(power).remove(player.getUniqueId(), worlds);
        return true;
    }

    public static boolean toggle(Power power, OfflinePlayer player, World world) {
        if (hasPower(power, player, world)) disable(power, player, world);
        else                                enable (power, player, world);
        return true;
    }

    public static abstract class BasedListener implements Listener {

        private final Power power;
        public BasedListener(Power power) {
            this.power = power;
        }

        @EventHandler
        public void onUse(PlayerSwapHandItemsEvent event) {
            Player player = event.getPlayer();
            if (!player.isSneaking()) return;
            if (!hasPower(power, player, player.getWorld())) return;
            event.setCancelled(true);
            run(event, player);
        }

        public abstract void run(PlayerSwapHandItemsEvent event, Player player);
    }

}

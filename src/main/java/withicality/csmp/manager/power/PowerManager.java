package withicality.csmp.manager.power;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.CosmicPlugin;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.ConfigManager;

import java.io.IOException;
import java.util.*;

public class PowerManager {
    public static final String CONFIG_NAME = "storagepower21";
    private static FileConfiguration config;

    public static void init() {
        load();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimerAsynchronously(CosmicPlugin.getInstance(), 5*60*20, 5*60*20);
    }

    public static void save() throws IOException {
        ConfigManager.getInstance().save(CONFIG_NAME);
        Bukkit.broadcast(ChatColor.AQUA + "Power config saved.", "csmp.power");
    }

    public static void load() {
        ConfigManager.getInstance().reloadConfig(CONFIG_NAME);
        config = ConfigManager.getInstance().getCustomConfig(CONFIG_NAME);
        Bukkit.broadcast(ChatColor.AQUA + "Power config loaded.", "csmp.power");
    }

    public static boolean hasPower(Power power, OfflinePlayer player, World world) {
        return power.equals(Power.NOT_SELECTED) || config.getStringList(getPath(power, player.getUniqueId())).contains(world.getName());
    }

    public static boolean enable(Power power, OfflinePlayer player, World world) {
        if (power.equals(Power.NOT_SELECTED)) return false;
        if (hasPower(power, player, world)) return false;

        List<String> list = config.getStringList(getPath(power, player.getUniqueId()));
        list.add(world.getName());
        config.set(getPath(power, player.getUniqueId()), list);
        return true;
    }

    public static boolean disable(Power power, OfflinePlayer player, World world) {
        if (power.equals(Power.NOT_SELECTED)) return false;
        if (!hasPower(power, player, world)) return false;

        List<String> list = config.getStringList(getPath(power, player.getUniqueId()));
        list.remove(world.getName());
        config.set(getPath(power, player.getUniqueId()), list);
        return true;
    }

    public static boolean toggle(Power power, OfflinePlayer player, World world) {
        return hasPower(power, player, world) ? disable(power, player, world) : enable(power, player, world);
    }

    private static String getPath(Power power, UUID uuid) {
        return power.name() + "." + uuid.toString();
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

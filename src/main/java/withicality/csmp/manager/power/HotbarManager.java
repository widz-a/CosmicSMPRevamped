package withicality.csmp.manager.power;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.ReflectionUtil;
import withicality.csmp.CosmicPlugin;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.ConfigManager;

import java.io.IOException;
import java.util.Arrays;

public class HotbarManager {
    public static final String CONFIG_NAME = "storagehotbar21";
    public static final String CONTAINTER_SLOT = "Hotbar Switch";
    public static final String CONTAINTER_POWAH = "Choose power:";

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

    public static void set(Player player, int slot, Power power) {
        config.set(getPath(player, slot), power.name());
    }

    public static String getPath(Player player, int slot) {
        return player.getUniqueId() + "." + slot;
    }

    public static Inventory getPowerInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, CONTAINTER_POWAH);
        for (Power power : Arrays.stream(Power.HOTBAR).filter(x -> PowerManager.hasPower(x, player, player.getWorld())).toList()) {
            inventory.addItem(power.getItem());
        }
        return inventory;
    }

    public static Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9, CONTAINTER_SLOT);
        for (int i = 0; i < 9; i++) {
            String name = config.getString(getPath(player, i));
            Power e = ReflectionUtil.lookupEnumSilent(Power.class, name == null ? "NOT_SELECTED" : name);
            Power power = e == null ? Power.NOT_SELECTED : e;

            ItemStack item = power.getItem();

            inventory.setItem(i, item);
        }
        return inventory;
    }
}

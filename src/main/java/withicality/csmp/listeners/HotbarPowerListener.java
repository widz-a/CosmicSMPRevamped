package withicality.csmp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.mineacademy.fo.ReflectionUtil;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.power.HotbarManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HotbarPowerListener implements Listener {
    Map<UUID, Integer> map = new HashMap<>();
    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals(HotbarManager.CONTAINTER_POWAH)) return;
        map.remove(event.getPlayer().getUniqueId());
        event.getPlayer().sendMessage("closed");
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(HotbarManager.CONTAINTER_SLOT)) {
            int slot = event.getSlot();
            event.setCancelled(true);
            map.put(player.getUniqueId(), slot);
            player.openInventory(HotbarManager.getPowerInventory((player)));
            return;
        }

        if (!event.getView().getTitle().equals(HotbarManager.CONTAINTER_POWAH)) return;
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        String powerData = data.get(Power.KEY, PersistentDataType.STRING);
        if (!data.has(Power.KEY)) return;
        if (powerData == null) return;
        Power power = ReflectionUtil.lookupEnumSilent(Power.class, powerData);
        if (power == null) return;

        HotbarManager.set(player, map.get(player.getUniqueId()), power);
        player.openInventory(HotbarManager.getInventory((player)));
        event.getWhoClicked().sendMessage("clicked");
    }

    @EventHandler
    public void open(InventoryOpenEvent event) {
        if (!event.getView().getTitle().equals(HotbarManager.CONTAINTER_POWAH)) return;
        event.getPlayer().sendMessage("OPen");
    }
}

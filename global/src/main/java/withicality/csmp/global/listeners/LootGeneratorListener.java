package withicality.csmp.global.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import withicality.csmp.api.CosmicConfig;
import withicality.csmp.api.LootManager;

import java.util.Map;
import java.util.Objects;

public class LootGeneratorListener implements Listener {

    @EventHandler
    public void event(LootGenerateEvent event) {
        FileConfiguration config = CosmicConfig.getInstance().getCustomConfig("loots");
        for (Map<?, ?> map : config.getMapList("loots")) {
            String namespace = (String) map.get("namespace");
            String type = (String) map.get("type");
            double chance = (double) map.get("chance");
            String loottable = (String) map.get("loottable");
            int min = (int) map.get("min");
            int max = (int) map.get("max");

            double random = Math.random();
            if (!event.getLootTable().getKey().getKey().equals(loottable)) continue;
            if (random > chance) continue;

            ItemStack disc = Objects.requireNonNull(LootManager.getItem(namespace, type.toLowerCase())).clone();
            disc.setAmount((int) (Math.random() * (max - min)) + min);

            event.getLoot().add(disc);
        }
    }

}

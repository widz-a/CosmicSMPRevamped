package withicality.csmp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.mineacademy.fo.ReflectionUtil;
import org.mineacademy.fo.plugin.SimplePlugin;
import withicality.csmp.events.PlayerUpdateEvent;
import withicality.csmp.manager.ConfigManager;
import withicality.csmp.manager.power.HotbarManager;
import withicality.csmp.manager.power.PowerManager;
import withicality.csmp.manager.SchematicManager;
import withicality.csmp.protocol.ServerListListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CosmicPlugin extends SimplePlugin {
    private static ProtocolManager manager;
    public static ProtocolManager getProtocolManager() {
        return manager;
    }

    @Override
    protected void onPluginStart() {
        ConfigManager.createInstance("storageplayerdata21", "storageuuiddata21", "storagepower21", "storagehotbar21");
        SchematicManager.init();
        PowerManager.init();
        HotbarManager.init();

        manager = ProtocolLibrary.getProtocolManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerUpdateEvent event = new PlayerUpdateEvent(player);
                Bukkit.getPluginManager().callEvent(event);
            }
        }, 0L, 1L);

        for (PacketListener listener : manager.getPacketListeners()) {
            if (!listener.getPlugin().getName().equals("SuperVanish")) continue;
            if (!listener.getClass().getSimpleName().equals("ServerListPacketListener")) continue;
            manager.removePacketListener(listener);
        }

        try {
            ReflectionUtil.getClasses(this, Class.forName("withicality.csmp.CosmicCommand")).stream().filter(x -> !x.getSimpleName().startsWith("Legacy_")).forEach(x -> registerCommand((CosmicCommand) newInstance(x)));
            ReflectionUtil.getClasses(this, Class.forName("withicality.csmp.CosmicPlugin$CosmicListener")).stream().filter(x -> !x.getSimpleName().startsWith("Legacy_")).forEach(x -> registerEvents((Listener) newInstance(x)));
            //ReflectionUtil.getClasses(this, Class.forName("com.comphenix.protocol.events.PacketAdapter")).stream().filter(x -> !x.getSimpleName().startsWith("Legacy_")).forEach(x -> manager.addPacketListener((PacketAdapter) newInstance(x)));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        manager.addPacketListener(new ServerListListener());
    }

    @Override
    protected void onPluginStop() {
        try {
            PowerManager.save();
            HotbarManager.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object newInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            displayError0(e);
            setEnabled(false);
            return null;
        }
    }

    public static abstract class CosmicListener implements Listener { }
}

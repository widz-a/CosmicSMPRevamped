package withicality.csmp.api;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.plugin.java.JavaPlugin;
import withicality.csmp.api.listeners.PlayerVanishListener;
import withicality.csmp.api.protocol.ServerListListener;

public class MainClass extends JavaPlugin {
    @Override
    public void onEnable() {
        CosmicConfig.createInstance("loots");
        SchematicManager.init();

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        APIStuff.setManager(manager);

        for (PacketListener listener : manager.getPacketListeners()) {
            if (!listener.getPlugin().getName().equals("SuperVanish")) continue;
            if (!listener.getClass().getSimpleName().equals("ServerListPacketListener")) continue;
            manager.removePacketListener(listener);
        }

        getServer().getPluginManager().registerEvents(new PlayerVanishListener(), this);
        manager.addPacketListener(new ServerListListener());

    }
}

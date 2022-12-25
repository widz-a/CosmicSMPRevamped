package withicality.csmp.api;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import org.mineacademy.fo.plugin.SimplePlugin;
import withicality.csmp.api.listeners.PlayerVanishListener;
import withicality.csmp.api.protocol.ServerListListener;

public class MainClass extends SimplePlugin {

    @Override
    protected void onPluginStart() {
        CosmicConfig.createInstance("loots");
        SchematicManager.init();

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        APIStuff.setManager(manager);

        for (PacketListener listener : manager.getPacketListeners()) {
            if (!listener.getPlugin().getName().equals("SuperVanish")) continue;
            if (!listener.getClass().getSimpleName().equals("ServerListPacketListener")) continue;
            manager.removePacketListener(listener);
        }

        registerEvents(new PlayerVanishListener());
        manager.addPacketListener(new ServerListListener());
    }
}

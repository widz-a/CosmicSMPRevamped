package withicality.csmp.api;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.plugin.java.JavaPlugin;
import withicality.csmp.api.protocol.PlayerCountListener;

public class MainClass extends JavaPlugin {
    @Override
    public void onEnable() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        APIStuff.setManager(manager);

        for (PacketListener listener : manager.getPacketListeners()) {
            if (!listener.getPlugin().getName().equals("SuperVanish")) continue;
            if (!listener.getClass().getSimpleName().equals("ServerListPacketListener")) continue;
            manager.removePacketListener(listener);
        }

        manager.addPacketListener(new PlayerCountListener());

    }
}

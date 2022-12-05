package withicality.csmp.api;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import withicality.csmp.api.protocol.PlayerCountListener;

public class MainClass extends JavaPlugin {
    @Override
    public void onEnable() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        APIStuff.setManager(manager);

        manager.addPacketListener(new PlayerCountListener());

    }
}

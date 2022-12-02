package withicality.csmp.api;

import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
    @Override
    public void onEnable() {
        APIStuff.setManager(ProtocolLibrary.getProtocolManager());
        getLogger().info("hi!");
    }
}

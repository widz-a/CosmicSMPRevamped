package withicality.csmp.api;

import com.comphenix.protocol.ProtocolManager;
import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class APIStuff {
    public static final String HR = ChatColor.DARK_AQUA + "" + ChatColor.STRIKETHROUGH + Strings.repeat(" ", 80);

    private static ProtocolManager manager;
    protected static void setManager(ProtocolManager m) {
        manager = m;
    }
    public static ProtocolManager getProtocolManager() {
        return manager;
    }
}

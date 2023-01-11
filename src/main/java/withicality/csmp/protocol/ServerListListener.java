package withicality.csmp.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import org.bukkit.Bukkit;
import withicality.csmp.CosmicPlugin;

import java.util.Collections;

public class ServerListListener extends PacketAdapter {
    public ServerListListener() {
        super(CosmicPlugin.getInstance(), ListenerPriority.NORMAL,
                Collections.singletonList(PacketType.Status.Server.SERVER_INFO), ListenerOptions.ASYNC);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        ping.setPlayersVisible(false);
        ping.setVersionName("CosmicSpigot " + Bukkit.getBukkitVersion().split("-")[0]);
    }
}

package withicality.csmp.api.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import withicality.csmp.api.MainClass;

import java.util.Collections;

public class PlayerCountListener extends PacketAdapter {
    public PlayerCountListener() {
        super(MainClass.getPlugin(MainClass.class), ListenerPriority.NORMAL,
                Collections.singletonList(PacketType.Status.Server.SERVER_INFO), ListenerOptions.ASYNC);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        ping.setPlayersVisible(false);
    }
}

package withicality.csmp.global.commands.troll;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;

import java.lang.reflect.InvocationTargetException;

public class DemotrollCommand extends CosmicCommand {
    public DemotrollCommand() {
        super("demo", "Forces the demo screen to popup", "/demo <player>", "csmp.demotroll");
        setMinArguments(1);
    }

    @Override
    protected void onCommand() {
        Player victim = APIStuff.getPlayer(args[0], sender);
        checkNotNull(victim, noPlayerFound(args[0]));

        ProtocolManager manager = APIStuff.getProtocolManager();
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
        packet.getGameStateIDs().write(0, 5);
        packet.getFloat().write(0, 0F);
        try {
            manager.sendServerPacket(victim, packet);
            sender.sendMessage(ChatColor.AQUA + "You've forced the demo menu to appear for " + ChatColor.DARK_AQUA + victim.getName() + ChatColor.AQUA + ".");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Unable to troll");
        }
    }
}

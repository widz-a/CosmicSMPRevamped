package withicality.csmp.global.commands;

import com.comphenix.protocol.PacketType;
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
import java.util.Collections;
import java.util.List;

public class FakeopCommand extends CosmicCommand {
    public FakeopCommand() {
        super("fakeop", "To fake op a player", "/fakeop <player>", ImmutableList.of(), "csmp.demotroll");
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

        victim.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "["+ sender.getName() + ": Made " + victim.getName() + " a server operator]");
        sender.sendMessage(ChatColor.AQUA + "Fake-opped " + ChatColor.DARK_AQUA + victim.getName() + ChatColor.AQUA + ".");
    }
}

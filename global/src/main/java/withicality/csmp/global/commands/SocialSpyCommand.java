package withicality.csmp.global.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.MessageManager;
import withicality.withicalutilities.entity.WPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialSpyCommand extends CosmicCommand {
    public SocialSpyCommand() {
        super("socialspy", "Toggles if you can see msg/mail commands in chat", "/socialspy [player] [on|off]", ImmutableList.of(), "csmp.socialspy");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (!isPlayer(sender)) {
            sender.sendMessage("You know, you can see what command players are executing, right?");
            return;
        }

        // /socialspy
        if (args.length == 0) {
            boolean a = MessageManager.updateSpies(player, !MessageManager.containSpies(player));
            send(player, a);
            return;
        }

        // /socialspy [on|off]
        if (Arrays.asList("on", "off").contains(args[0].toLowerCase())) {
            boolean a = MessageManager.updateSpies(player, args[0].equalsIgnoreCase("on"));
            send(player, a);
            return;
        }

        Player victim = Bukkit.getPlayerExact(args[0]);
        if (victim == null) {
            noPlayerFound(args[0], player);
            return;
        }

        // /socialspy [player]
        if (args.length == 1) {
            boolean a = MessageManager.updateSpies(victim, !MessageManager.containSpies(player));
            send(player, a);
            return;
        }

        // /socialspy [player] [on|off]
        if (!Arrays.asList("on", "off").contains(args[1].toLowerCase())) {
            sendUsage(player);
            return;
        }

        boolean a = MessageManager.updateSpies(victim, args[1].equalsIgnoreCase("on"));
        send(player, a);

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (!isPlayer(sender)) {
            return ImmutableList.of();
        }
        List<String> YN = Arrays.asList("on", "off");
        List<String> players = new ArrayList<>();
        players.addAll(WPlayer.getPlayersCanBeSeen((Player) sender));
        players.addAll(YN);

        return args.length == 1 ? players : (args.length == 2 ? YN : ImmutableList.of());
    }

    private void send(Player player, boolean resp) {
        String turned = MessageManager.containSpies(player) ? "on" : "off";
        String already = resp ? ChatColor.AQUA + "T" : ChatColor.RED +  "Already t";
        String pName = ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + ".";
        player.sendMessage(already + "urned " + turned + " social spy for: " + (resp ? pName : ChatColor.stripColor(pName)));
    }

}

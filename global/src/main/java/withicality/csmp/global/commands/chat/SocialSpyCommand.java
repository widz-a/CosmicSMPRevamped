package withicality.csmp.global.commands.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.manager.MessageManager;
import java.util.Arrays;
import java.util.List;

public class SocialSpyCommand extends CosmicCommand {
    public SocialSpyCommand() {
        super("socialspy", "Toggles if you can see msg/mail commands in chat", "/socialspy [player] [on|off]", "csmp.socialspy");
    }

    @Override
    protected void onCommand() {
        checkConsole();
        // i 100% will not understand this tmr
        Player player = (Player) sender;
        boolean toggle = (args.length == 1 || args.length == 2) && Arrays.asList("on", "off").contains(args[args.length - 1].toLowerCase()) ? args[args.length - 1].equalsIgnoreCase("on") : !MessageManager.containSpies(player);
        Player victim = args.length > 1 && !Arrays.asList("on", "off").contains(args[0]) ? APIStuff.getPlayer(args[0], player) : player;

        boolean a = MessageManager.updateSpies(victim, toggle);
        send(player, victim, a);
    }

    @Override
    protected List<String> tabComplete() {
        return switch (args.length) {
            case 1 -> completeLastWord("on", "off", APIStuff.getPlayersCanBeSeen((Player) sender));
            case 2 -> completeLastWord("on", "off");
            default -> NO_COMPLETE;
        };
    }

    private void send(Player sender, Player player, boolean resp) {
        String turned = MessageManager.containSpies(player) ? "on" : "off";
        String already = resp ? ChatColor.AQUA + "T" : ChatColor.RED +  "Already t";
        String pName = ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + ".";
        sender.sendMessage(already + "urned " + turned + " social spy for: " + (resp ? pName : ChatColor.stripColor(pName)));
    }
}
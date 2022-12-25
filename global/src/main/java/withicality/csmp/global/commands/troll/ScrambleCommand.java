package withicality.csmp.global.commands.troll;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScrambleCommand extends CosmicCommand {
    public ScrambleCommand() {
        super("scramble", "Scrambles player's inventory contents", "/scramble <player>", "csmp.scramble");
        setMinArguments(1);
    }

    @Override
    public void onCommand() {
        Player victim = APIStuff.getPlayer(args[0], sender);
        checkNotNull(victim, noPlayerFound(args[0]));
        checkNotNull(victim.getInventory(), "ok idk what the fuck happened but it happened (unable to find player's inventory). crazy, i know");

        List<ItemStack> inv = Arrays.asList(victim.getInventory().getStorageContents());
        Collections.shuffle(inv);
        victim.getInventory().setStorageContents(inv.toArray(new ItemStack[0]));
        sender.sendMessage(ChatColor.AQUA + "Scrambled " + ChatColor.DARK_AQUA + victim.getName() + ChatColor.AQUA + "'s inventory.");
    }
}

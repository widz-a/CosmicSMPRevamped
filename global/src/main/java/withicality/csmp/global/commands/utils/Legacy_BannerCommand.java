package withicality.csmp.global.commands.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import withicality.csmp.api.CosmicCommand;

import java.util.List;

@Deprecated
public class Legacy_BannerCommand extends CosmicCommand {
    public Legacy_BannerCommand() {
        super("banner");
    }

    @Override
    public void onCommand() {
        checkConsole();
        Player player = (Player) sender;

        ItemStack banner = player.getInventory().getItemInMainHand();
        ItemStack helmet = player.getInventory().getHelmet();

        checkBoolean(banner.getType().toString().endsWith("_BANNER"), "You are not holding the banner");

        player.getInventory().setHelmet(banner.clone());
        player.getInventory().setItemInMainHand(helmet == null ? new ItemStack(Material.AIR) : helmet.clone());
    }

    @Override
    protected List<String> tabComplete() {
        return NO_COMPLETE;
    }
}

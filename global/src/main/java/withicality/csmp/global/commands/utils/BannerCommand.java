package withicality.csmp.global.commands.utils;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import withicality.csmp.api.CosmicCommand;

public class BannerCommand extends CosmicCommand {
    public BannerCommand() {
        super("banner");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] strings) {
        if (player == null) {
            sender.sendMessage(getNotPlayerMessage());
            return;
        }
        ItemStack banner = player.getInventory().getItemInMainHand();
        ItemStack helmet = player.getInventory().getHelmet();

        if (!banner.getType().toString().endsWith("_BANNER")) return;

        player.getInventory().setHelmet(banner.clone());
        player.getInventory().setItemInMainHand(helmet == null ? new ItemStack(Material.AIR) : helmet.clone());
    }
}

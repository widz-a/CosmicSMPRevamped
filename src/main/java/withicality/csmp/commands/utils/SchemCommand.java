package withicality.csmp.commands.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import withicality.csmp.CosmicStatic;
import withicality.csmp.CosmicCommand;
import withicality.csmp.manager.SchematicManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SchemCommand extends CosmicCommand {
    public SchemCommand() {
        super("schemtest", "Paste/Load/Undo/Redo a schematic", "/schemtest <schematic file> <x> <y> <z> <world> [<id>]\n/schemtest list\n/schemtest undo/redo <id>\n", "csmp.schem");
        setMinArguments(1);
    }

    @Override
    public void onCommand() {
        String schem = args[0];

        if (schem.equalsIgnoreCase("list")) {
            sender.sendMessage(CosmicStatic.HR);
            SchematicManager.get().forEach(x -> {
                TextComponent id = new TextComponent(x);
                id.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, x));
                sender.spigot().sendMessage(id);
            });
            sender.sendMessage(CosmicStatic.HR);
            return;
        }

        if (schem.equalsIgnoreCase("undo") || schem.equalsIgnoreCase("redo")) {
            checkArgs(2, "You forgot the id");
            boolean a = schem.equalsIgnoreCase("undo") ? SchematicManager.undo(args[1]) : SchematicManager.redo(args[1]);
            sender.sendMessage(a ? ChatColor.GREEN + "Succeeded!" : ChatColor.RED + "Failed!");
            return;
        }

        if (schem.equalsIgnoreCase("save")) {
            try {
                SchematicManager.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Saved to config!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        if (schem.equalsIgnoreCase("load")) {
            SchematicManager.loadConfig();
            sender.sendMessage(ChatColor.GREEN + "Saved to config!");
            return;
        }

        checkArgs(5, "You forgot (some) stuff. Tip: make sure you specified world in the end");

        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        String a = SchematicManager.load(new Location(Bukkit.getWorld(args[4]), x, y, z), schem, args.length < 6 ? null : args[5]);
        checkNotNull(a, "Failed :(");

        TextComponent id = new TextComponent(a);
        id.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, a));
        sender.spigot().sendMessage(new TextComponent(ChatColor.RED + "Succeeded! Id: "), id);
    }

    @Override
    public List<String> tabComplete() {
        int length = args.length;

        if (length == 1) return completeLastWord(SchematicManager.get(), "undo", "redo", "list", "save", "load");

        if (length >= 2 && Arrays.asList("undo", "redo", "list").contains(args[0].toLowerCase(Locale.ROOT))) {
            if (args[0].equalsIgnoreCase("list") || length > 2) return NO_COMPLETE;
            if (args[0].equalsIgnoreCase("undo") || args[0].equalsIgnoreCase("redo")) return SchematicManager.getIds(args[0]);

        }

        if (Arrays.asList("save", "load").contains(args[0].toLowerCase(Locale.ROOT))) return NO_COMPLETE;
        if (!(sender instanceof Player player)) return NO_COMPLETE;

        return switch (length) {
            case 2 -> completeLastWord(String.valueOf(player.getLocation().getBlockX()));
            case 3 -> completeLastWord(String.valueOf(player.getLocation().getBlockY()));
            case 4 -> completeLastWord(String.valueOf(player.getLocation().getBlockZ()));
            case 5 -> completeLastWordWorldNames();
            default -> NO_COMPLETE;
        };
    }
}

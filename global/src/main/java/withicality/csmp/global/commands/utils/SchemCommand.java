package withicality.csmp.global.commands.utils;

import com.google.common.collect.ImmutableList;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import withicality.csmp.api.APIStuff;
import withicality.csmp.api.CosmicCommand;
import withicality.csmp.api.SchematicManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchemCommand extends CosmicCommand {
    public SchemCommand() {
        super("schemtest", "Paste/Load/Undo/Redo a schematic", "/schemtest <schematic file> <x> <y> <z> <world>\n/schemtest list\n/schemtest undo/redo <id>\n", ImmutableList.of(), "csmp.schem");
    }

    @Override
    public void run(CommandSender sender, Player player, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return;
        }

        String schem = args[0];

        if (schem.equalsIgnoreCase("list")) {
            sender.sendMessage(APIStuff.HR);
            SchematicManager.get().forEach(x -> {
                TextComponent id = new TextComponent(x);
                id.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, x));
                sender.spigot().sendMessage(id);
            });
            sender.sendMessage(APIStuff.HR);
            return;
        }

        if (schem.equalsIgnoreCase("undo") || schem.equalsIgnoreCase("redo")) {
            if (args.length < 2) {
                sendUsage(sender);
                return;
            }
            boolean a = schem.equalsIgnoreCase("undo") ? SchematicManager.undo(args[1]) : SchematicManager.redo(args[1]);
            sender.sendMessage(a ? ChatColor.GREEN + "Succeeded!" : ChatColor.RED + "Failed!");
            return;
        }

        if (args.length < 5) {
            sendUsage(sender);
            return;
        }

        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        String a = SchematicManager.load(new Location(Bukkit.getWorld(args[4]), x, y, z), schem);

        if (a != null) {
            TextComponent id = new TextComponent(a);
            id.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, a));
            sender.spigot().sendMessage(new TextComponent(ChatColor.RED + "Succeeded! Id: "), id);
        } else {
            sender.sendMessage(ChatColor.RED + "Failed!");
        }
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        int length = args.length;

        if (length == 1) {
            List<String> a = SchematicManager.get();
            a.addAll(Arrays.asList("undo", "redo", "list"));
            return a;
        }

        if (length >= 2 && Arrays.asList("undo", "redo", "list").contains(args[0])) {
            if (args[0].equalsIgnoreCase("list") || length > 2) return ImmutableList.of();
            if (args[0].equalsIgnoreCase("undo") || args[0].equalsIgnoreCase("redo")) return SchematicManager.getIds(args[0]);
        }


        if (!isPlayer(sender)) return ImmutableList.of();
        Player player = (Player) sender;

        switch (length) {
            case 2 -> { return List.of(String.valueOf(player.getLocation().getBlockX())); }

            case 3 -> { return List.of(String.valueOf(player.getLocation().getBlockY())); }
            case 4 -> { return List.of(String.valueOf(player.getLocation().getBlockZ())); }
            case 5 -> {
                List<String> worlds = new ArrayList<>();
                Bukkit.getWorlds().forEach(x -> worlds.add(x.getName()));
                return worlds;
            }
            default -> { return ImmutableList.of(); }
        }


    }
}

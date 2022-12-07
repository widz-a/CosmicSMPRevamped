package withicality.csmp.api;

import com.google.common.collect.ImmutableList;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Special thanks to Harry0198 for the tutorial
 * @link {<a href="https://www.spigotmc.org/threads/1-13-load-paste-schematics-with-the-worldedit-api-simplified.357335/">...</a>}
 */
public class SchematicManager {
    private final static MainClass plugin = MainClass.getPlugin(MainClass.class);

    public static void init() {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics");
        if (!file.exists()) file.mkdirs();
    }

    public static List<String> get() {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics");
        File[] files = file.listFiles((dir, name) -> name.toLowerCase().endsWith(".schem"));

        if (files == null) return ImmutableList.of();
        List<String> strings = new ArrayList<>();

        for (File f : files) {
            strings.add(f.getName());
        }

        return strings;
    }
    public static boolean load(Location location, String schem) {
        schem = schem.endsWith(".schem") ? schem : schem + ".schem";

        try {
            File file = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics/" + schem);
            ClipboardFormat format = ClipboardFormats.findByFile(file);

            ClipboardReader reader = format.getReader(new FileInputStream(file));
            Clipboard clipboard = reader.read();
            World adaptedWorld = BukkitAdapter.adapt(location.getWorld());

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);

            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ())).ignoreAirBlocks(true).build();

            Operations.complete(operation);
            editSession.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

package withicality.csmp.manager;

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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.CosmicPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Special thanks to Harry0198 for the tutorial
 * @link {<a href="https://www.spigotmc.org/threads/1-13-load-paste-schematics-with-the-worldedit-api-simplified.357335/">...</a>}
 */
public class SchematicManager {
    private final static CosmicPlugin plugin = (CosmicPlugin) CosmicPlugin.getInstance();
    public final static String ID = "storageeditsession21";

    private final static HashMap<String, EditSession> undo = new HashMap<>();
    private final static HashMap<String, EditSession> redo = new HashMap<>();

    public static void init() {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/schematics");
        if (!file.exists()) file.mkdirs();

        loadConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    saveConfig();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimerAsynchronously(CosmicPlugin.getInstance(), 5*60*20, 5*60*20);
    }

    public static void loadConfig() {
        FileConfiguration config = ConfigManager.getInstance().getCustomConfig(ID);
        ConfigManager.getInstance().reloadConfig(ID);
        if (config.get("undo") != null) config.getMapList("undo").forEach(x -> x.keySet().forEach(y -> {
            EditSession session = (EditSession) config.get("undo." + y, EditSession.class);
            undo.put((String) y, session);
        }));

        if (config.get("redo") != null) config.getMapList("redo").forEach(x -> x.keySet().forEach(y -> {
            EditSession session = (EditSession) config.get("redo." + y, EditSession.class);
            redo.put((String) y, session);
        }));
    }

    public static void saveConfig() throws IOException {
        FileConfiguration config = ConfigManager.getInstance().getCustomConfig(ID);

        undo.forEach((x, y) -> config.set("undo." + x, y));
        redo.forEach((x, y) -> config.set("redo." + x, y));

        ConfigManager.getInstance().save(ID);
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
    public static String load(Location location, String schem, String id) {
        if (id == null) id = Long.toHexString(System.currentTimeMillis());
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
            undo.put(id, editSession);

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean undo(String id) {
        if (!undo.containsKey(id)) return false;

        EditSession session = undo.get(id);
        session.undo(session);
        session.close();

        undo.remove(id, session);
        redo.put(id, session);

        return true;
    }

    public static boolean redo(String id) {
        if (!redo.containsKey(id)) return false;

        EditSession session = redo.get(id);
        session.redo(session);
        session.close();

        redo.remove(id, session);
        undo.put(id, session);

        return true;
    }

    public static List<String> getIds(String type) {
        Set<String> a = type.equalsIgnoreCase("undo") ? undo.keySet() : redo.keySet();
        return new ArrayList<>(a);
    }

}

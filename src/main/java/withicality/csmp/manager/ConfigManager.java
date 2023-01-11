package withicality.csmp.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import withicality.csmp.CosmicPlugin;

public class ConfigManager {
    private final JavaPlugin plugin;
    private final HashMap<String, File> configFile;
    private final HashMap<String, FileConfiguration> config;
    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        return instance;
    }

    public static void createInstance(String... configs) {
        instance = new ConfigManager(configs);
    }

    public ConfigManager(String... configs) {
        this.plugin = CosmicPlugin.getInstance();
        this.configFile = new HashMap<>();
        this.config = new HashMap<>();
        this.createCustomConfig(configs);
    }

    public FileConfiguration getCustomConfig(String filename) {
        return this.config.get(filename);
    }

    public File getConfigFile(String filename) {
        return this.configFile.get(filename);
    }

    private void createCustomConfig(String... filenames) {
        for (String filename : filenames) {
            configFile.put(filename, new File(plugin.getDataFolder(), filename + ".yml"));
            if (!configFile.get(filename).exists()) {
                configFile.get(filename).getParentFile().mkdirs();
                plugin.saveResource(filename + ".yml", false);
            }

            config.put(filename, new YamlConfiguration());
            try {
                config.get(filename).load(configFile.get(filename));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }


    public void reloadConfig(String filename) {
        this.config.put(filename, new YamlConfiguration());

        try {
            this.config.get(filename).load((File)this.configFile.get(filename));
        } catch (InvalidConfigurationException | IOException var3) {
            var3.printStackTrace();
        }

    }

    public void save(String filename) throws IOException {
        getCustomConfig(filename).save(getConfigFile(filename));
    }
}

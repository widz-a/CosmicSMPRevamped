package withicality.csmp.api;

import withicality.withicalutilities.WithicalConfig;

public class CosmicConfig extends WithicalConfig {

    private static CosmicConfig instance;

    public static CosmicConfig getInstance() {
        return instance;
    }

    public static void createInstance(String... configs) {
        instance = new CosmicConfig(configs);
    }

    public CosmicConfig(String... configs) {
        super(MainClass.getPlugin(MainClass.class), configs);
    }
}

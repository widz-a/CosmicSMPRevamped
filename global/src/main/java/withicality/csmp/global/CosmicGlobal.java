package withicality.csmp.global;

import org.bukkit.plugin.java.JavaPlugin;
import withicality.csmp.global.commands.*;
import withicality.csmp.global.listeners.LootGeneratorListener;
import withicality.csmp.global.listeners.SocialSpyListeners;
import withicality.withicalutilities.APIManager;
import withicality.withicalutilities.WithicalConfig;

public class CosmicGlobal extends JavaPlugin {
    public static WithicalConfig CONFIG;
    @Override
    public void onEnable() {
        CONFIG = new WithicalConfig(this, "loots");

        APIManager.registerCommand(new TestCommand(), this);
        APIManager.registerCommand(new BannerCommand(), this);
        APIManager.registerCommand(new FunnyCommand(), this);
        APIManager.registerCommand(new MessageCommand(), this);
        APIManager.registerCommand(new ReplyCommand(), this);
        APIManager.registerCommand(new DemotrollCommand(), this);
        APIManager.registerCommand(new ScrambleCommand(), this);
        APIManager.registerCommand(new FakeopCommand(), this);
        APIManager.registerCommand(new SocialSpyCommand(), this);

        getServer().getPluginManager().registerEvents(new SocialSpyListeners(), this);
        getServer().getPluginManager().registerEvents(new LootGeneratorListener(), this);
    }
}

package withicality.csmp.global;

import org.bukkit.plugin.java.JavaPlugin;
import withicality.csmp.global.commands.chat.BroadcastCommand;
import withicality.csmp.global.commands.chat.MessageCommand;
import withicality.csmp.global.commands.chat.ReplyCommand;
import withicality.csmp.global.commands.chat.SocialSpyCommand;
import withicality.csmp.global.commands.loop.RunloopCommand;
import withicality.csmp.global.commands.loop.StoploopCommand;
import withicality.csmp.global.commands.troll.DemotrollCommand;
import withicality.csmp.global.commands.troll.FakeopCommand;
import withicality.csmp.global.commands.troll.FunnyCommand;
import withicality.csmp.global.commands.troll.ScrambleCommand;
import withicality.csmp.global.commands.utils.BannerCommand;
import withicality.csmp.global.commands.utils.SchemCommand;
import withicality.csmp.global.commands.utils.TestCommand;
import withicality.csmp.global.listeners.LootGeneratorListener;
import withicality.csmp.global.listeners.SocialSpyListener;
import withicality.csmp.api.CosmicCommand;

public class CosmicGlobal extends JavaPlugin {
    @Override
    public void onEnable() {
        CosmicCommand.register(new TestCommand());
        CosmicCommand.register(new BannerCommand());
        CosmicCommand.register(new FunnyCommand());
        CosmicCommand.register(new MessageCommand());
        CosmicCommand.register(new ReplyCommand());
        CosmicCommand.register(new DemotrollCommand());
        CosmicCommand.register(new ScrambleCommand());
        CosmicCommand.register(new FakeopCommand());
        CosmicCommand.register(new SocialSpyCommand());
        CosmicCommand.register(new SchemCommand());
        CosmicCommand.register(new BroadcastCommand());
        CosmicCommand.register(new RunloopCommand());
        CosmicCommand.register(new StoploopCommand());

        getServer().getPluginManager().registerEvents(new SocialSpyListener(), this);
        getServer().getPluginManager().registerEvents(new LootGeneratorListener(), this);
    }
}

package withicality.csmp.global;

import org.mineacademy.fo.plugin.SimplePlugin;
import withicality.csmp.global.commands.troll.FakeopCommand;
import withicality.csmp.global.listeners.SocialSpyListener;
import withicality.csmp.global.commands.chat.BroadcastCommand;
import withicality.csmp.global.commands.chat.MessageCommand;
import withicality.csmp.global.commands.chat.ReplyCommand;
import withicality.csmp.global.commands.chat.SocialSpyCommand;
import withicality.csmp.global.commands.loop.RunloopCommand;
import withicality.csmp.global.commands.loop.StoploopCommand;
import withicality.csmp.global.commands.troll.DemotrollCommand;
import withicality.csmp.global.commands.troll.FunnyCommand;
import withicality.csmp.global.commands.troll.ScrambleCommand;
import withicality.csmp.global.commands.utils.BannerCommand;
import withicality.csmp.global.commands.utils.SchemCommand;
import withicality.csmp.global.listeners.LootGeneratorListener;
import withicality.csmp.global.commands.teleportation.TPOfflineCommand;

public class CosmicGlobal extends SimplePlugin {

    @Override
    protected void onPluginStart() {
        registerCommand(new BroadcastCommand());
        registerCommand(new BannerCommand());
        registerCommand(new FunnyCommand());
        registerCommand(new MessageCommand());
        registerCommand(new ReplyCommand());
        registerCommand(new DemotrollCommand());
        registerCommand(new ScrambleCommand());
        registerCommand(new FakeopCommand());
        registerCommand(new SocialSpyCommand());
        registerCommand(new SchemCommand());
        registerCommand(new BroadcastCommand());
        registerCommand(new RunloopCommand());
        registerCommand(new StoploopCommand());
        registerCommand(new TPOfflineCommand());

        registerEvents(new SocialSpyListener());
        registerEvents(new LootGeneratorListener());
    }
}

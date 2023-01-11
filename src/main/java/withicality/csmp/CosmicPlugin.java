package withicality.csmp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mineacademy.fo.plugin.SimplePlugin;
import withicality.csmp.events.PlayerUpdateEvent;
import withicality.csmp.listeners.*;
import withicality.csmp.manager.ConfigManager;
import withicality.csmp.manager.SchematicManager;
import withicality.csmp.commands.chat.BroadcastCommand;
import withicality.csmp.commands.chat.SocialSpyCommand;
import withicality.csmp.commands.loop.RunloopCommand;
import withicality.csmp.commands.troll.FakeopCommand;
import withicality.csmp.commands.chat.MessageCommand;
import withicality.csmp.commands.chat.ReplyCommand;
import withicality.csmp.commands.loop.StoploopCommand;
import withicality.csmp.commands.troll.DemotrollCommand;
import withicality.csmp.commands.troll.FunnyCommand;
import withicality.csmp.commands.troll.ScrambleCommand;
import withicality.csmp.commands.utils.SchemCommand;
import withicality.csmp.commands.teleportation.TPOfflineCommand;
import withicality.csmp.protocol.ServerListListener;

public class CosmicPlugin extends SimplePlugin {
    private static ProtocolManager manager;
    public static ProtocolManager getProtocolManager() {
        return manager;
    }

    @Override
    protected void onPluginStart() {
        ConfigManager.createInstance("storageplayerdata21", "storageuuiddata21");
        SchematicManager.init();

        manager = ProtocolLibrary.getProtocolManager();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerUpdateEvent event = new PlayerUpdateEvent(player);
                Bukkit.getPluginManager().callEvent(event);
            }
        }, 0L, 1L);

        for (PacketListener listener : manager.getPacketListeners()) {
            if (!listener.getPlugin().getName().equals("SuperVanish")) continue;
            if (!listener.getClass().getSimpleName().equals("ServerListPacketListener")) continue;
            manager.removePacketListener(listener);
        }

        registerCommand(new BroadcastCommand());
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
        //registerEvents(new Legacy_LootGeneratorListener());
        registerEvents(new PlayerVanishListener());
        registerEvents(new OPlayerListener());
        registerEvents(new APIListener());

        manager.addPacketListener(new ServerListListener());
    }
}

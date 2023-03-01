package withicality.csmp.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import withicality.csmp.CosmicPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestartPollManager {
    private static RestartPollManager instance;
    public static RestartPollManager getInstance() {
        return instance == null ? new RestartPollManager() : instance;
    }

    private long time;
    private boolean denied = false;
    private BukkitRunnable runnable;

    private final List<UUID> accept = new ArrayList<>();
    private final List<UUID> deny = new ArrayList<>();
    private final List<UUID> voters = new ArrayList<>();

    private RestartPollManager() {
        instance = this;
    }
    public void createPoll() {
        time = System.currentTimeMillis() + 30*1000;
        denied = true;
        Bukkit.getOnlinePlayers().forEach(player -> voters.add(player.getUniqueId()));

        //Bukkit.broadcastMessage(ChatColor.AQUA + "CosmicSMP > Is it ok to restart the server?"); //	#FF5555
        TextComponent component = Component.text().append(
                Component.text("CosmicSMP » ").color(TextColor.fromHexString("#55FFFF")).decorate(TextDecoration.BOLD),
                Component.text("Is it ok to restart the server?").appendNewline(),
                Component.text("CosmicSMP » ").color(TextColor.fromHexString("#55FFFF")).decorate(TextDecoration.BOLD),
                Component.text("[Accept]").toBuilder().color(TextColor.fromHexString("#55FF55")).clickEvent(ClickEvent.runCommand("/restartpoll accept")).build(),
                Component.text("    "),
                Component.text("[Deny]").toBuilder().color(TextColor.fromHexString("#FF5555")).clickEvent(ClickEvent.runCommand("/restartpoll deny")).build().appendNewline(),
                Component.text("CosmicSMP » ").color(TextColor.fromHexString("#55FFFF")).decorate(TextDecoration.BOLD),
                Component.text("For "),
                Component.text("Bedrock ").color(TextColor.fromHexString("#AAAAAA")),
                Component.text("player, run: "),
                Component.text("/restartpoll accept/deny").color(TextColor.fromHexString("#55FF55"))
        ).build();

        //CosmicPlugin.adventure().players().sendMessage(component);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(component));
        runnable = new BukkitRunnable() {
            @Override
            public void run() { RestartPollManager.run(); }
        };
        runnable.runTaskLater(CosmicPlugin.getInstance(), 30*20);
    }

    public boolean denied() {
        return denied;
    }

    public long endIn() {
        return time - System.currentTimeMillis();
    }

    public boolean accept(Player player) {
        if (!voters.contains(player.getUniqueId())) return false;
        accept.add(player.getUniqueId());
        deny.remove(player.getUniqueId());

        checkBoth();
        return true;
    }

    public boolean deny(Player player) {
        if (!voters.contains(player.getUniqueId())) return false;
        deny.add(player.getUniqueId());
        accept.remove(player.getUniqueId());

        checkBoth();
        return true;
    }

    private void checkBoth() {
        TextComponent component = Component.text("CosmicSMP » ").color(TextColor.fromHexString("#55FFFF")).decorate(TextDecoration.BOLD);
        //yes restart
        if (accept.size() > voters.size()/2) {
            runnable.cancel();
            run();
            component.append(Component.text("Over 50% of the players have voted yes"));
            return;
        }

        //no restart
        if (deny.size() > voters.size()/2) {
            runnable.cancel();
            instance = null;
        }




    }

    private static void run() {

    }
}

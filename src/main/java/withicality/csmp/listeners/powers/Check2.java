package withicality.csmp.listeners.powers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.power.PowerManager;

public class Check2 extends PowerManager.BasedListener {
    public Check2() {
        super(Power.PREVIEW2);
    }

    @Override
    public void run(PlayerSwapHandItemsEvent event, Player player) {
        player.sendMessage("wither = smart :sunglass:");
    }
}

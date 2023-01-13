package withicality.csmp.listeners.powers;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import withicality.csmp.enums.Power;
import withicality.csmp.manager.PowerManager;

public class Check1 extends PowerManager.BasedListener {
    public Check1() {
        super(Power.PREVIEW1);
    }

    @Override
    public void run(PlayerSwapHandItemsEvent event, Player player) {
        player.sendMessage("prox is gay lmao");
    }
}

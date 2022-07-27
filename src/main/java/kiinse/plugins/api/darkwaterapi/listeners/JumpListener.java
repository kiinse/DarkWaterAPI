package kiinse.plugins.api.darkwaterapi.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class JumpListener implements Listener {

    private final DarkWaterAPI darkWaterAPI;

    public JumpListener(@NotNull DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @EventHandler
    public void jumpEvent(@NotNull PlayerJumpEvent event) {
        darkWaterAPI.getJumpSchedule().getJumpingMap().put(event.getPlayer().getUniqueId(), 0);
    }
}

package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class OnQuitListener implements Listener {

    private final DarkWaterAPI darkWaterAPI;

    public OnQuitListener(@NotNull DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @EventHandler
    public void quitEvent(@NotNull PlayerQuitEvent event) {
         darkWaterAPI.getMoveSchedule().getNotMovingMap().remove(event.getPlayer().getUniqueId());
    }
}

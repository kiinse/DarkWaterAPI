package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuitListener implements Listener {

    private final DarkWaterAPI darkWaterAPI;

    public OnQuitListener(DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event) {
         darkWaterAPI.getMoveSchedule().getNotMovingMap().remove(event.getPlayer().getUniqueId());
    }
}

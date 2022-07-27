package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public class MoveListener implements Listener {

    private final DarkWaterAPI darkWaterAPI;

    public MoveListener(@NotNull DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @EventHandler
    public void moveEvent(@NotNull PlayerMoveEvent event) {
        var player = event.getPlayer();
        if (!player.isInsideVehicle()) {
            darkWaterAPI.getMoveSchedule().getNotMovingMap().put(event.getPlayer().getUniqueId(), 0);
        }
    }
}

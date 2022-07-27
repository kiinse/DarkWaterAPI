package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.DarkWaterStatistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDeathListener implements Listener {

    private final DarkWaterStatistic darkWaterStatistic = DarkWaterAPI.getInstance().getDarkWaterStatistic();

    @EventHandler
    public void entityDeath(@NotNull EntityDeathEvent event) {
        var killer = event.getEntity().getKiller();
        if (killer != null) {
            darkWaterStatistic.addStatistic(killer, event.getEntity().getType());
        }
    }
}

package kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.utils.Config;
import kiinse.plugins.api.darkwaterapi.indicators.interfaces.IndicatorManager;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;


@SchedulerData(
        name = "IndicatorSchedule"
)
public class IndicatorSchedule extends Scheduler {
    private final IndicatorManager indicators = DarkWaterAPI.getInstance().getIndicatorManager();

    public IndicatorSchedule(DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean canStart() {
        return plugin.getConfiguration().getBoolean(Config.ACTIONBAR_INDICATORS) && Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public void run() {
        for (var player : Bukkit.getOnlinePlayers()) {
            if (PlayerUtils.isSurvivalAdventure(player)) {
                PlayerUtils.sendActionBar(player, PlaceholderAPI.setPlaceholders(player, indicators.getIndicators()));
            }
        }
    }

}

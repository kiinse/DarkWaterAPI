package kiinse.plugins.api.darkwaterapi.initialize;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.placeholders.LocaleExpansion;
import kiinse.plugins.api.darkwaterapi.placeholders.StatisticExpansion;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

public class LoadAPI {

    public LoadAPI(@NotNull DarkWaterAPI darkWaterAPI) {
        darkWaterAPI.sendLog("Registering PlaceHolderAPI...");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            darkWaterAPI.sendLog(Level.WARNING, "PlaceHolderAPI not found! The indicators above the toolbar are &cdisabled&6.");
        } else {
            Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI")).getLogger().setLevel(Level.WARNING);
            var localeExpansion = new LocaleExpansion(darkWaterAPI);
            if (!localeExpansion.isRegistered()) {
                localeExpansion.register();
                darkWaterAPI.sendLog("Locale expansion registered");
            } else {
                darkWaterAPI.sendLog("&6Locale expansion already registered");
            }
            var statisticExpansion = new StatisticExpansion(darkWaterAPI);
            if (!statisticExpansion.isRegistered()) {
                statisticExpansion.register();
                darkWaterAPI.sendLog("Statistic expansion registered");
            } else {
                darkWaterAPI.sendLog("&6Statistic expansion already registered");
            }
        }
    }
}

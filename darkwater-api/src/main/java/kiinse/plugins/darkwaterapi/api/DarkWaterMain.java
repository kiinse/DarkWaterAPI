package kiinse.plugins.darkwaterapi.api;

import kiinse.plugins.darkwaterapi.api.commands.CommandFailureHandler;
import kiinse.plugins.darkwaterapi.api.exceptions.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.api.files.statistic.DarkWaterStatistic;
import kiinse.plugins.darkwaterapi.api.indicators.IndicatorManager;
import kiinse.plugins.darkwaterapi.api.loader.DarkPluginManager;
import kiinse.plugins.darkwaterapi.api.schedulers.SchedulersManager;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface DarkWaterMain {

    @NotNull LocaleStorage getLocaleStorage();

    @NotNull PlayerLocales getPlayerLocales();

    @NotNull Messages getMessages(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException;

    @NotNull MessagesUtils getMessagesUtils(@NotNull DarkWaterJavaPlugin plugin);

    @NotNull DarkWaterStatistic getDarkWaterStatistic();

    @NotNull DarkPluginManager getPluginManager();

    @NotNull IndicatorManager getIndicatorManager();

    @NotNull SchedulersManager getSchedulersManager();

    @NotNull CommandFailureHandler getCommandFailureHandler();

    boolean isDebug();

}

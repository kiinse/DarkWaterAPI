package kiinse.plugins.api.darkwaterapi;

import kiinse.plugins.api.darkwaterapi.exceptions.PluginException;
import kiinse.plugins.api.darkwaterapi.exceptions.VersioningException;
import kiinse.plugins.api.darkwaterapi.files.config.Configuration;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.locale.PlayerLocaleImpl;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.files.locale.utils.LocaleLoaderImpl;
import kiinse.plugins.api.darkwaterapi.files.locale.utils.LocaleSaverImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.DarkWaterMessages;
import kiinse.plugins.api.darkwaterapi.files.statistic.DarkWaterStatsImpl;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.DarkWaterStatistic;
import kiinse.plugins.api.darkwaterapi.indicators.IndicatorManagerImpl;
import kiinse.plugins.api.darkwaterapi.indicators.interfaces.IndicatorManager;
import kiinse.plugins.api.darkwaterapi.initialize.LoadAPI;
import kiinse.plugins.api.darkwaterapi.initialize.RegisterCommands;
import kiinse.plugins.api.darkwaterapi.initialize.RegisterEvents;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterPluginManager;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkPluginManager;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.rest.RestConnectionImpl;
import kiinse.plugins.api.darkwaterapi.rest.interfaces.RestConnection;
import kiinse.plugins.api.darkwaterapi.schedulers.SchedulersManagerImpl;
import kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers.IndicatorSchedule;
import kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers.JumpSchedule;
import kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers.MoveSchedule;
import kiinse.plugins.api.darkwaterapi.schedulers.interfaces.SchedulersManager;
import kiinse.plugins.api.darkwaterapi.utilities.versioning.Versioning;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.logging.Level;

@SuppressWarnings({"unused", "unchecked"})
public final class DarkWaterAPI extends DarkWaterJavaPlugin {

    private static DarkWaterAPI instance;
    private DarkPluginManager pluginManager;
    private LocaleStorage localeStorage;
    private PlayerLocale locales;
    private DarkWaterStatistic darkWaterStatistic;
    private IndicatorManager indicatorManager;
    private SchedulersManager schedulersManager;
    private RestConnection rest;
    private JumpSchedule jumpSchedule;
    private MoveSchedule moveSchedule;

    @Override
    protected void start() throws PluginException {
        try {
            getLogger().setLevel(Level.CONFIG);
            sendLog("Loading " + getName() + "...");
            onStart();
            getPluginManager().registerPlugin(this);
            sendInfo();
            checkForUpdates();
        } catch (Exception e) {
            throw new PluginException(e);
        }
    }

    @Override
    public void onStart() throws Exception {
        instance = this;
        super.configuration = new Configuration(this);
        new LoadAPI(this);
        localeStorage = new LocaleLoaderImpl(this).getLocaleStorage();
        locales = new PlayerLocaleImpl(this, localeStorage);
        super.messages = new DarkWaterMessages(this);
        darkWaterStatistic = new DarkWaterStatsImpl(this);
        indicatorManager = new IndicatorManagerImpl(this);
        schedulersManager = new SchedulersManagerImpl(this);
        pluginManager = new DarkWaterPluginManager(this);
        jumpSchedule = new JumpSchedule(this);
        moveSchedule = new MoveSchedule(this);
        schedulersManager.registerSchedule(jumpSchedule);
        schedulersManager.registerSchedule(moveSchedule);
        schedulersManager.registerSchedule(new IndicatorSchedule(this));
        rest = new RestConnectionImpl(this);
        new RegisterCommands(this);
        new RegisterEvents(this);
    }

    @Override
    public void onStop() throws Exception {
        new LocaleSaverImpl(this).saveLocaleStorage();
        darkWaterStatistic.save();
        rest.stop();
        schedulersManager.stopSchedules();
    }

    @Override
    public void restart() {
        try {
            sendLog("Reloading " + getName() + "!");
            new LocaleSaverImpl(this).saveLocaleStorage();
            darkWaterStatistic.save();
            rest.stop();
            getMessages().reload();
            getConfiguration().reload();
            localeStorage = new LocaleLoaderImpl(this).getLocaleStorage();
            locales = new PlayerLocaleImpl(this, localeStorage);
            darkWaterStatistic = new DarkWaterStatsImpl(this);
            rest = new RestConnectionImpl(this);
            sendLog(getName() + " reloaded!");
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on reloading " + getName() + "! Message: " + e.getMessage());
        }
    }

    /**
     * Getting information about plugin systems (Indicators, localization, schedulers, statistics) in the form of json
     * {@link JSONObject}
     * @return JSONObject with info
     */
    @Override
    public @NotNull JSONObject getPluginData() {
        var json = new JSONObject();
        json.put("indicators", getIndicators());
        json.put("locales", localeStorage.getAllowedLocalesListString());
        json.put("defaultLocale", localeStorage.getDefaultLocale().toString());
        json.put("schedulers", getSchedulers());
        json.put("statistic", getStatisticData());
        return json;
    }

    private @NotNull JSONObject getIndicators() {
        var indicators = new JSONObject();
        for (var indicator : indicatorManager.getIndicatorsList()) {
            var indicatorJson = new JSONObject();
            indicatorJson.put("plugin", indicator.getPlugin().getName());
            indicatorJson.put("position", indicator.getPosition());
            indicators.put(indicator.getName(), indicatorJson);
        }
        return indicators;
    }

    private @NotNull JSONObject getSchedulers() {
        var schedulers = new JSONObject();
        for (var scheduler : schedulersManager.getAllSchedulers()) {
            var schedulerJson = new JSONObject();
            schedulerJson.put("plugin", scheduler.getPlugin().getName());
            schedulerJson.put("isStarted", scheduler.isStarted());
            schedulers.put(scheduler.getName(), schedulerJson);
        }
        return schedulers;
    }

    private @NotNull JSONObject getStatisticData() {
        var json = new JSONObject();
        for (var player : getServer().getOfflinePlayers()) {
            json.put(player.getName(), darkWaterStatistic.getPlayerStatistic(player.getUniqueId()).toJSONObject().toMap());
        }
        return json;
    }

    private void checkForUpdates() {
        if (!getConfiguration().getBoolean(Config.DISABLE_VERSION_CHECK)) {
            try {
                var latest = Versioning.getLatestGithubVersion("https://github.com/kiinse/DarkWaterAPI");
                if (!latest.isGreaterThan(Versioning.getCurrentDarkWaterVersion())) {
                    sendLog("Latest version of DarkWaterAPI installed, no new versions found =3");
                } else {
                    sendConsole(" &c|====================================UPDATE====================================");
                    sendConsole(" &c| ");
                    sendConsole(" &c| &6New version of DarkWaterAPI found: '&b" + latest.getOriginalValue() + "&6'! Your version is '&b" + getDescription().getVersion() + "&6'");
                    sendConsole(" &c| &6You can download it at &bhttps://github.com/kiinse/DarkWaterAPI/releases");
                    sendConsole(" &c| ");
                    sendConsole(" &c| &6You can &cdisable&6 DarkWaterAPI version checking by entering the line '&bdisable.version.check: true&6' in the &bconfig");
                    sendConsole(" &c| ");
                    sendConsole(" &c|==============================================================================");
                }
            } catch (VersioningException e) {
                sendLog(Level.WARNING, "Error checking DarkWaterAPI version! Message: " + e.getMessage());
            }
        }
    }

    public @NotNull LocaleStorage getLocaleStorage() {
        return localeStorage;
    }

    /**
     * Getting a class to work with player languages
     * @return {@link PlayerLocale}
     */
    public @NotNull PlayerLocale getLocales() {
        return locales;
    }

    /**
     * Getting a class to work with player statistics
     * @return {@link DarkWaterStatistic}
     */
    public @NotNull DarkWaterStatistic getDarkWaterStatistic() {
        return darkWaterStatistic;
    }

    /**
     * Получение менеджера плагинов
     * @return {@link DarkPluginManager}
     */
    public @NotNull DarkPluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * Getting the plugin manager
     * @return {@link DarkPluginManager}
     */
    public @NotNull IndicatorManager getIndicatorManager() {
        return indicatorManager;
    }

    /**
     * Getting scheduler manager
     * @return {@link SchedulersManager}
     */
    public @NotNull SchedulersManager getSchedulersManager() {
        return schedulersManager;
    }

    public @NotNull MoveSchedule getMoveSchedule() {
        return moveSchedule;
    }

    public @NotNull JumpSchedule getJumpSchedule() {
        return jumpSchedule;
    }

    /**
     * Getting DarkWaterAPI instance
     * @return {@link DarkWaterAPI}
     */
    public static @NotNull DarkWaterAPI getInstance() {return instance;}
}

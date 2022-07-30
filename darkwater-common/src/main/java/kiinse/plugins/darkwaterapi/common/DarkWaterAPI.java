// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.darkwaterapi.common;

import com.vdurmont.semver4j.Semver;
import kiinse.plugins.darkwaterapi.api.DarkWaterMain;
import kiinse.plugins.darkwaterapi.api.commands.CommandFailureHandler;
import kiinse.plugins.darkwaterapi.api.exceptions.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.exceptions.exceptions.PluginException;
import kiinse.plugins.darkwaterapi.api.exceptions.exceptions.VersioningException;
import kiinse.plugins.darkwaterapi.api.files.filemanager.YamlFile;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import kiinse.plugins.darkwaterapi.api.files.enums.Config;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.common.initialize.LoadAPI;
import kiinse.plugins.darkwaterapi.common.initialize.RegisterCommands;
import kiinse.plugins.darkwaterapi.common.initialize.RegisterEvents;
import kiinse.plugins.darkwaterapi.core.commands.FailureHandler;
import kiinse.plugins.darkwaterapi.core.files.locale.LocaleStorageImpl;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.IndicatorSchedule;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.JumpSchedule;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.MoveSchedule;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.core.files.locale.PlayerLocalesImpl;
import kiinse.plugins.darkwaterapi.api.files.statistic.DarkWaterStatistic;
import kiinse.plugins.darkwaterapi.core.files.statistic.DarkWaterStatsImpl;
import kiinse.plugins.darkwaterapi.api.indicators.IndicatorManager;
import kiinse.plugins.darkwaterapi.core.indicators.IndicatorManagerImpl;
import kiinse.plugins.darkwaterapi.api.loader.DarkPluginManager;
import kiinse.plugins.darkwaterapi.core.loader.DarkWaterPluginManager;
import kiinse.plugins.darkwaterapi.api.schedulers.SchedulersManager;
import kiinse.plugins.darkwaterapi.core.schedulers.SchedulersManagerImpl;
import kiinse.plugins.darkwaterapi.core.utilities.VersionUtils;
import kiinse.plugins.darkwaterapi.core.files.messages.MessagesImpl;
import kiinse.plugins.darkwaterapi.core.files.messages.MessagesUtilsImpl;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

@SuppressWarnings({"unused"})
public final class DarkWaterAPI extends DarkWaterJavaPlugin implements DarkWaterMain {

    private static DarkWaterAPI instance;
    private DarkPluginManager pluginManager;
    private LocaleStorage localeStorage;
    private PlayerLocales locales;
    private DarkWaterStatistic darkWaterStatistic;
    private IndicatorManager indicatorManager;
    private SchedulersManager schedulersManager;

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
        setDarkWaterAPI(this);
        instance = this;
        super.configuration = new YamlFile(this, getConfigurationFileName());
        new LoadAPI(this);
        localeStorage = new LocaleStorageImpl(this).load();
        locales = new PlayerLocalesImpl(this, localeStorage);
        super.messages = getMessages(this);
        darkWaterStatistic = new DarkWaterStatsImpl(this);
        indicatorManager = new IndicatorManagerImpl(this);
        schedulersManager = new SchedulersManagerImpl(this);
        pluginManager = new DarkWaterPluginManager(this);
        schedulersManager
                .register(new JumpSchedule(this))
                .register(new MoveSchedule(this))
                .register(new IndicatorSchedule(this));
        new RegisterCommands(this);
        new RegisterEvents(this);
    }

    @Override
    public void onStop() throws Exception {
        localeStorage.save();
        darkWaterStatistic.save();
        schedulersManager.stopSchedulers();
    }

    @Override
    public void restart() {
        try {
            sendLog("Reloading " + getName() + "!");
            localeStorage.save();
            darkWaterStatistic.save();
            getMessages().reload();
            getConfiguration().reload();
            localeStorage.load();
            locales = new PlayerLocalesImpl(this, localeStorage);
            darkWaterStatistic = new DarkWaterStatsImpl(this);
            sendLog(getName() + " reloaded!");
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on reloading " + getName() + "! Message: " + e.getMessage());
        }
    }

    private void checkForUpdates() {
        if (!getConfiguration().getBoolean(Config.DISABLE_VERSION_CHECK)) {
            try {
                var latest = VersionUtils.getLatestGithubVersion("https://github.com/kiinse/DarkWaterAPI");
                if (!latest.isGreaterThan(new Semver(getDescription().getVersion()))) {
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

    @Override
    public @NotNull PlayerLocales getPlayerLocales() {
        return locales;
    }

    public @NotNull Messages getMessages(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException {
        return new MessagesImpl(plugin);
    }

    public @NotNull MessagesUtils getMessagesUtils(@NotNull DarkWaterJavaPlugin plugin) {
        return new MessagesUtilsImpl(plugin);
    }

    @Override
    public @NotNull DarkWaterStatistic getDarkWaterStatistic() {
        return darkWaterStatistic;
    }

    @Override
    public @NotNull DarkPluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public @NotNull IndicatorManager getIndicatorManager() {
        return indicatorManager;
    }

    @Override
    public @NotNull SchedulersManager getSchedulersManager() {
        return schedulersManager;
    }

    @Override
    public @NotNull CommandFailureHandler getCommandFailureHandler() {
        return new FailureHandler(this);
    }

    @Override
    public boolean isDebug() {
        if (getConfiguration() != null) {
            return getConfiguration().getBoolean(Config.DEBUG);
        }
        return false;
    }

    public static @NotNull DarkWaterAPI getInstance() {
        return instance;
    }
}

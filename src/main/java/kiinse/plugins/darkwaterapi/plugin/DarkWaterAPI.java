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

package kiinse.plugins.darkwaterapi.plugin;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.DarkWaterMain;
import kiinse.plugins.darkwaterapi.api.commands.CommandFailureHandler;
import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.exceptions.PluginException;
import kiinse.plugins.darkwaterapi.api.exceptions.VersioningException;
import kiinse.plugins.darkwaterapi.api.files.enums.Config;
import kiinse.plugins.darkwaterapi.api.files.filemanager.YamlFile;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.api.files.statistic.StatisticManager;
import kiinse.plugins.darkwaterapi.api.indicators.IndicatorManager;
import kiinse.plugins.darkwaterapi.api.loader.PluginManager;
import kiinse.plugins.darkwaterapi.api.schedulers.SchedulersManager;
import kiinse.plugins.darkwaterapi.api.utilities.TaskType;
import kiinse.plugins.darkwaterapi.common.gui.LocaleFlags;
import kiinse.plugins.darkwaterapi.common.gui.LocaleGUI;
import kiinse.plugins.darkwaterapi.common.initialize.LoadAPI;
import kiinse.plugins.darkwaterapi.common.initialize.RegisterCommands;
import kiinse.plugins.darkwaterapi.common.initialize.RegisterEvents;
import kiinse.plugins.darkwaterapi.core.commands.FailureHandler;
import kiinse.plugins.darkwaterapi.core.files.locale.DarkLocaleStorage;
import kiinse.plugins.darkwaterapi.core.files.locale.DarkPlayerLocales;
import kiinse.plugins.darkwaterapi.core.files.messages.DarkMessages;
import kiinse.plugins.darkwaterapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.darkwaterapi.core.files.statistic.DarkStatisticManager;
import kiinse.plugins.darkwaterapi.core.indicators.DarkIndicatorManager;
import kiinse.plugins.darkwaterapi.core.loader.DarkPluginManager;
import kiinse.plugins.darkwaterapi.core.schedulers.DarkSchedulersManager;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.IndicatorSchedule;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.JumpSchedule;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.MoveSchedule;
import kiinse.plugins.darkwaterapi.core.utilities.DarkUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkVersionUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Level;

@SuppressWarnings({"unused"})
public final class DarkWaterAPI extends DarkWaterJavaPlugin implements DarkWaterMain {

    private static DarkWaterAPI instance;
    private PluginManager pluginManager;
    private LocaleStorage localeStorage;
    private PlayerLocales locales;
    private StatisticManager darkWaterStatistic;
    private IndicatorManager indicatorManager;
    private SchedulersManager schedulersManager;

    public static @NotNull DarkWaterMain getInstance() {
        return instance;
    }

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
        localeStorage = new DarkLocaleStorage(this).load();
        locales = new DarkPlayerLocales(this, localeStorage);
        super.messages = getMessages(this);
        darkWaterStatistic = new DarkStatisticManager(this);
        indicatorManager = new DarkIndicatorManager(this);
        schedulersManager = new DarkSchedulersManager(this);
        pluginManager = new DarkPluginManager(this);
        schedulersManager
                .register(new JumpSchedule(this))
                .register(new MoveSchedule(this))
                .register(new IndicatorSchedule(this));
        LocaleGUI.setLocaleFlags(new LocaleFlags(this));
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
            locales = new DarkPlayerLocales(this, localeStorage);
            darkWaterStatistic = new DarkStatisticManager(this);
            sendLog(getName() + " reloaded!");
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on reloading " + getName() + "! Message: " + e.getMessage());
        }
    }

    private void checkForUpdates() {
        DarkUtils.runTask(TaskType.ASYNC, this, () -> {
            if (!getConfiguration().getBoolean(Config.DISABLE_VERSION_CHECK)) {
                try {
                    var latest = DarkVersionUtils.getLatestGithubVersion("https://github.com/kiinse/DarkWaterAPI");
                    if (!latest.isGreaterThan(DarkVersionUtils.getPluginVersion(this))) {
                        sendLog("Latest version of DarkWaterAPI installed, no new versions found =3");
                        return;
                    }
                    var reader = new BufferedReader(new InputStreamReader(
                            Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("version-message.txt"))));
                    var builder = new StringBuilder("\n");
                    while (reader.ready())
                        builder.append(reader.readLine()).append("\n");
                    sendConsole(DarkUtils.replaceWord(builder.toString(), new String[]{
                            "{NEW_VERSION}:" + latest.getOriginalValue(),
                            "{CURRENT_VERSION}:" + getDescription().getVersion()
                    }));
                } catch (IOException | VersioningException e) {
                    sendLog(Level.WARNING, "Error checking DarkWaterAPI version! Message: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public @NotNull LocaleStorage getLocaleStorage() {
        return localeStorage;
    }

    @Override
    public @NotNull PlayerLocales getPlayerLocales() {
        return locales;
    }

    @Override
    public @NotNull Messages getMessages(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException {
        return new DarkMessages(plugin);
    }

    @Override
    public @NotNull MessagesUtils getMessagesUtils(@NotNull DarkWaterJavaPlugin plugin) {
        return new DarkMessagesUtils(plugin);
    }

    @Override
    public @NotNull StatisticManager getDarkWaterStatistic() {
        return darkWaterStatistic;
    }

    @Override
    public @NotNull PluginManager getPluginManager() {
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
        if (getConfiguration() != null)
            return getConfiguration().getBoolean(Config.DEBUG);
        return false;
    }
}

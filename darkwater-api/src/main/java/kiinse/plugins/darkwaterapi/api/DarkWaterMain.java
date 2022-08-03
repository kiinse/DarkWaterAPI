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

package kiinse.plugins.darkwaterapi.api;

import kiinse.plugins.darkwaterapi.api.commands.CommandFailureHandler;
import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.api.files.statistic.StatisticManager;
import kiinse.plugins.darkwaterapi.api.indicators.IndicatorManager;
import kiinse.plugins.darkwaterapi.api.loader.PluginManager;
import kiinse.plugins.darkwaterapi.api.schedulers.SchedulersManager;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface DarkWaterMain {

    @NotNull LocaleStorage getLocaleStorage();

    @NotNull PlayerLocales getPlayerLocales();

    @NotNull Messages getMessages(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException;

    @NotNull MessagesUtils getMessagesUtils(@NotNull DarkWaterJavaPlugin plugin);

    @NotNull StatisticManager getDarkWaterStatistic();

    @NotNull PluginManager getPluginManager();

    @NotNull IndicatorManager getIndicatorManager();

    @NotNull SchedulersManager getSchedulersManager();

    @NotNull CommandFailureHandler getCommandFailureHandler();

    boolean isDebug();

}

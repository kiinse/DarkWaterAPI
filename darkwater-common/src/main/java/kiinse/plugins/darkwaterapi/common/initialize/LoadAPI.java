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

package kiinse.plugins.darkwaterapi.common.initialize;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.common.placeholders.LocaleExpansion;
import kiinse.plugins.darkwaterapi.common.placeholders.StatisticExpansion;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Level;

public class LoadAPI {

    public LoadAPI(@NotNull DarkWaterJavaPlugin plugin) {
        plugin.sendLog("Registering PlaceHolderAPI...");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            plugin.sendLog(Level.WARNING, "PlaceHolderAPI not found! The indicators above the toolbar are &cdisabled&6.");
        } else {
            Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI")).getLogger().setLevel(Level.WARNING);
            var localeExpansion = new LocaleExpansion(plugin);
            if (!localeExpansion.isRegistered()) {
                localeExpansion.register();
                plugin.sendLog("Locale expansion registered");
            } else {
                plugin.sendLog("&6Locale expansion already registered");
            }
            var statisticExpansion = new StatisticExpansion(plugin);
            if (!statisticExpansion.isRegistered()) {
                statisticExpansion.register();
                plugin.sendLog("Statistic expansion registered");
            } else {
                plugin.sendLog("&6Statistic expansion already registered");
            }
        }
    }
}

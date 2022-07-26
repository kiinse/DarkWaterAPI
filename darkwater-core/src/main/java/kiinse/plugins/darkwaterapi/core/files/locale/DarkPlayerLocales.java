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

package kiinse.plugins.darkwaterapi.core.files.locale;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class DarkPlayerLocales implements PlayerLocales {

    private final DarkWaterJavaPlugin plugin;
    private final LocaleStorage storage;

    public DarkPlayerLocales(@NotNull DarkWaterJavaPlugin plugin, @NotNull LocaleStorage storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    @Override
    public boolean isLocalized(@NotNull Player player) {
        return storage.isLocalesDataContains(player);
    }

    @Override
    public @NotNull PlayerLocale getLocale(@NotNull Player player) {
        if (isLocalized(player)) return storage.getLocalesData(player);
        return storage.getDefaultLocale();
    }

    @Override
    public @NotNull PlayerLocale getLocale(@NotNull CommandSender sender) {
        if (sender instanceof ConsoleCommandSender)
            return storage.getDefaultLocale();
        var player = DarkPlayerUtils.getPlayer(sender);
        if (isLocalized(player))
            return storage.getLocalesData(player);
        return storage.getDefaultLocale();
    }

    @Override
    public @NotNull PlayerLocale convertStringToLocale(@NotNull String locale) {
        return new Locale(locale);
    }

    @Override
    public void setLocale(@NotNull Player player, @NotNull PlayerLocale playerLocale) {
        if (isLocalized(player) && storage.removeLocalesData(player))
            plugin.sendLog(Level.CONFIG, "Player '" + DarkPlayerUtils.getPlayerName(player) + "' locale has been removed");
        if (storage.putInLocalesData(player, storage.isAllowedLocale(playerLocale) ? playerLocale : storage.getDefaultLocale()))
            plugin.sendLog(Level.CONFIG, "Player '" + DarkPlayerUtils.getPlayerName(player) + "' locale has been added. Locale: " + playerLocale);
    }

    @Override
    public @NotNull PlayerLocale getInterfaceLocale(@NotNull Player player) {
        return convertStringToLocale(player.getLocale().split("_")[0]);
    }
}

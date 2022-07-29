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

package kiinse.plugins.api.darkwaterapi.files.locale;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocales;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class PlayerLocalesImpl implements PlayerLocales {

    private final DarkWaterAPI darkWaterAPI;
    private final LocaleStorage storage;

    public PlayerLocalesImpl(@NotNull DarkWaterAPI darkWaterAPI, @NotNull LocaleStorage storage) {
        this.darkWaterAPI = darkWaterAPI;
        this.storage = storage;
    }

    @Override
    public boolean isPlayerLocalized(@NotNull Player player) {
        return storage.isLocalesDataContains(player);
    }

    @Override
    public @NotNull Locale getPlayerLocale(@NotNull Player player) {
        if (isPlayerLocalized(player)) {
            return storage.getLocalesData(player);
        } return storage.getDefaultLocale();
    }

    @Override
    public @NotNull Locale getPlayerLocale(@NotNull CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return storage.getDefaultLocale();
        }
        var player = PlayerUtils.getPlayer(sender);
        if (isPlayerLocalized(player)) {
            return storage.getLocalesData(player);
        } return storage.getDefaultLocale();
    }

    @Override
    public void setPlayerLocale(@NotNull Player player, @NotNull Locale locale) {
        if (isPlayerLocalized(player) && storage.removeLocalesData(player)) {
            darkWaterAPI.sendLog(Level.CONFIG, "Player '" + PlayerUtils.getPlayerName(player) + "' locale has been removed");
        }
        if (storage.putInLocalesData(player, storage.isAllowedLocale(locale) ? locale : storage.getDefaultLocale())) {
            darkWaterAPI.sendLog(Level.CONFIG, "Player '" + PlayerUtils.getPlayerName(player)  + "' locale has been added. Locale: " + locale);
        }
    }

    @Override
    public @NotNull Locale getPlayerInterfaceLocale(@NotNull Player player) {
        return Locale.valueOf(player.locale().getLanguage());
    }
}

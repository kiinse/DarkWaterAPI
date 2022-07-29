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

import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LocaleStorageImpl implements LocaleStorage {

    private final Locale defaultLocale;
    private final List<Locale> allowedLocales;
    private final HashMap<UUID, Locale> locales;

    public LocaleStorageImpl(@NotNull DarkWaterJavaPlugin plugin, @NotNull List<Locale> allowedLocales, @NotNull Map<UUID, Locale> locales, @NotNull Locale defLocale) throws IllegalArgumentException {
        this.allowedLocales = allowedLocales;
        plugin.sendLog("Loaded locales: '&b" + getAllowedLocalesString() + "&a'");
        this.locales = (HashMap<UUID, Locale>) locales;
        if (!isAllowedLocale(defLocale)) {
            throw new IllegalArgumentException("This default locale '" + defLocale + "' is not allowed!");
        }
        this.defaultLocale = defLocale;
        plugin.sendLog("Installed default locale: &b" + defLocale);
    }

    @Override
    public boolean isAllowedLocale(@NotNull Locale locale) {
        for (var loc : allowedLocales) {
            if (locale.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean putInLocalesData(@NotNull UUID uuid, @NotNull Locale locale) {
        locales.put(uuid, locale);
        return locales.containsKey(uuid);
    }

    @Override
    public boolean putInLocalesData(@NotNull Player player, @NotNull Locale locale) {
        var uuid = player.getUniqueId();
        locales.put(uuid, locale);
        return locales.containsKey(uuid);
    }

    @Override
    public boolean isLocalesDataContains(@NotNull UUID uuid) {
        return locales.containsKey(uuid);
    }

    @Override
    public boolean isLocalesDataContains(@NotNull Player player) {
        return locales.containsKey(player.getUniqueId());
    }

    @Override
    public @NotNull Locale getLocalesData(@NotNull UUID uuid) {
        return locales.get(uuid);
    }

    @Override
    public @NotNull Locale getLocalesData(@NotNull Player player) {
        return locales.get(player.getUniqueId());
    }

    @Override
    public boolean removeLocalesData(@NotNull UUID uuid) {
        locales.remove(uuid);
        return !locales.containsKey(uuid);
    }

    @Override
    public boolean removeLocalesData(@NotNull Player player) {
        var uuid = player.getUniqueId();
        locales.remove(uuid);
        return !locales.containsKey(uuid);
    }

    @Override
    public @NotNull Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public @NotNull String getAllowedLocalesString() {
        return allowedLocales.stream().map(Locale::toString).collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public @NotNull List<Locale> getAllowedLocalesList() {
        return new ArrayList<>(allowedLocales);
    }

    @Override
    public @NotNull List<String> getAllowedLocalesListString() {
        var list = new ArrayList<String>();
        for (var locale : getAllowedLocalesList()) {
            list.add(locale.toString());
        }
        return list;
    }

    @Override
    public @NotNull HashMap<UUID, Locale> getLocalesData() {
        return locales;
    }
}

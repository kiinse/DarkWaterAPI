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
import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.exceptions.LocaleException;
import kiinse.plugins.darkwaterapi.api.files.enums.Config;
import kiinse.plugins.darkwaterapi.api.files.enums.Directory;
import kiinse.plugins.darkwaterapi.api.files.enums.File;
import kiinse.plugins.darkwaterapi.api.files.filemanager.FilesManager;
import kiinse.plugins.darkwaterapi.api.files.filemanager.JsonFile;
import kiinse.plugins.darkwaterapi.api.files.locale.Locale;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DarkLocaleStorage extends FilesManager implements LocaleStorage {

    private final DarkWaterJavaPlugin plugin;
    private final JsonFile jsonFile;
    private Locale defaultLocale;
    private Set<Locale> allowedLocales;
    private HashMap<UUID, Locale> locales;

    public DarkLocaleStorage(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
        var directoryName = Directory.MESSAGES;
        if (isFileNotExists(directoryName)) {
            copyFile(directoryName);
        }
        this.plugin = plugin;
        this.jsonFile = new JsonFile(plugin, File.DATA_JSON);
    }

    @Override
    public @NotNull LocaleStorage load() throws LocaleException, JsonFileException {
        this.allowedLocales = parseAllowedLocales(Arrays.stream(Objects.requireNonNull(getFile(Directory.MESSAGES).listFiles())).toList());
        plugin.sendLog("Loaded locales: '&b" + getAllowedLocalesString() + "&a'");
        this.locales = parseLocalesData(jsonFile.getJsonFromFile());
        var defLocale = parseDefaultLocale(plugin.getConfiguration().getString(Config.LOCALE_DEFAULT));
        if (!isAllowedLocale(defLocale)) {
            throw new LocaleException("This default locale '" + defLocale + "' is not allowed!");
        }
        this.defaultLocale = defLocale;
        plugin.sendLog("Installed default locale: &b" + defLocale);
        return this;
    }

    @Override
    public @NotNull LocaleStorage save() throws JsonFileException {
        var json = new JSONObject();
        for (var uuid : getLocalesData().keySet()) {
            json.put(uuid.toString(), getLocalesData(uuid).toString());
        }
        jsonFile.saveJsonToFile(json);
        return this;
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
    public @NotNull Set<Locale> getAllowedLocalesList() {
        return new HashSet<>(allowedLocales);
    }

    @Override
    public @NotNull Set<String> getAllowedLocalesListString() {
        var set = new HashSet<String>();
        for (var locale : getAllowedLocalesList()) {
            set.add(locale.toString());
        }
        return set;
    }

    @Override
    public @NotNull HashMap<UUID, Locale> getLocalesData() {
        return locales;
    }

    private @NotNull Set<Locale> parseAllowedLocales(@NotNull List<java.io.File> locales) throws LocaleException {
        var set = new HashSet<Locale>();
        for (var file : locales) {
            var fileName = file.getName().split("\\.");
            if (Objects.equals(fileName[1], "json")) {
                var locale = Locale.valueOf(fileName[0]);
                if (!isContainsLocale(set, locale)) {
                    set.add(locale);
                } else {
                    plugin.sendLog(Level.WARNING, "Locale '&c" + locale + "&6' is duplicated!");
                }
            }
        }
        if (set.isEmpty()) {
            throw new LocaleException("Allowed locales is empty!");
        }
        return set;
    }

    private @NotNull Locale parseDefaultLocale(@NotNull String locale) throws LocaleException {
        var loc = locale.replace(" ", "");
        if (loc.isEmpty()) {
            throw new LocaleException("Default locale is empty!");
        }
        return Locale.valueOf(loc);
    }

    private @NotNull HashMap<UUID, Locale> parseLocalesData(@NotNull JSONObject json) {
        var map = new HashMap<UUID, Locale>();
        for (var locale : json.keySet()) {
            map.put(UUID.fromString(locale), Locale.valueOf(json.getString(locale)));
        }
        return map;
    }

    private boolean isContainsLocale(@NotNull Set<Locale> allowedLocales, @NotNull Locale locale) {
        for (var loc : allowedLocales) {
            if (loc.equals(locale)) {
                return true;
            }
        }
        return false;
    }
}

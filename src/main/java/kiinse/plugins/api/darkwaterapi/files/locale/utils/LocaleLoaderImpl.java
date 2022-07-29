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

package kiinse.plugins.api.darkwaterapi.files.locale.utils;

import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import kiinse.plugins.api.darkwaterapi.exceptions.LocaleException;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.JsonFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.enums.Directory;
import kiinse.plugins.api.darkwaterapi.files.filemanager.enums.File;
import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.FilesManager;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.locale.LocaleStorageImpl;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleLoader;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;

public class LocaleLoaderImpl extends FilesManager implements LocaleLoader {

    private final DarkWaterJavaPlugin plugin;

    public LocaleLoaderImpl(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
        var directoryName = Directory.MESSAGES;
        if (isFileNotExists(directoryName)) {
            copyFile(directoryName);
        }
        this.plugin = plugin;
    }

    @Override
    public @NotNull LocaleStorage getLocaleStorage() throws LocaleException, JsonFileException {
        var allowedLocales = parseAllowedLocales(Arrays.stream(Objects.requireNonNull(getFile(Directory.MESSAGES).listFiles())).toList());
        var localesData = parseLocalesData(new JsonFile(plugin, File.DATA_JSON).getJsonFromFile());
        var defaultLocale = parseDefaultLocale(plugin.getConfiguration().getString(Config.LOCALE_DEFAULT));
        return new LocaleStorageImpl(plugin, allowedLocales, localesData, defaultLocale);
    }

    @Override
    public @NotNull List<Locale> parseAllowedLocales(@NotNull List<java.io.File> locales) throws LocaleException {
        var list = new ArrayList<Locale>();
        for (var file : locales) {
            var fileName = file.getName().split("\\.");
            if (Objects.equals(fileName[1], "json")) {
                var locale = Locale.valueOf(fileName[0]);
                if (!isContainsLocale(list, locale)) {
                    list.add(locale);
                } else {
                    plugin.sendLog(Level.WARNING, "Locale '&c" + locale + "&6' is duplicated!");
                }
            }
        }
        if (list.isEmpty()) {
            throw new LocaleException("Allowed locales is empty!");
        }
        return list;
    }

    @Override
    public @NotNull Locale parseDefaultLocale(@NotNull String locale) throws LocaleException {
        var loc = locale.replace(" ", "");
        if (loc.isEmpty()) {
            throw new LocaleException("Default locale is empty!");
        }
        return Locale.valueOf(loc);
    }

    @Override
    public @NotNull HashMap<UUID, Locale> parseLocalesData(@NotNull JSONObject json) {
        var map = new HashMap<UUID, Locale>();
        for (var locale : json.keySet()) {
            map.put(UUID.fromString(locale), Locale.valueOf(json.getString(locale)));
        }
        return map;
    }

    private boolean isContainsLocale(@NotNull List<Locale> allowedLocales, @NotNull Locale locale) {
        for (var loc : allowedLocales) {
            if (loc.equals(locale)) {
                return true;
            }
        }
        return false;
    }
}

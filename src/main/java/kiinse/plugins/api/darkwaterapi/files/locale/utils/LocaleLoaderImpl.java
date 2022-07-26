package kiinse.plugins.api.darkwaterapi.files.locale.utils;

import kiinse.plugins.api.darkwaterapi.files.config.utils.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.JsonFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.FilesManager;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.Directory;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.File;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.locale.LocaleStorageImpl;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleLoader;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class LocaleLoaderImpl extends FilesManager implements LocaleLoader {

    private final DarkWaterJavaPlugin plugin;

    public LocaleLoaderImpl(DarkWaterJavaPlugin plugin) {
        super(plugin);
        var directoryName = Directory.MESSAGES;
        if (isFileNotExists(directoryName)) {
            copyFile(directoryName);
        }
        this.plugin = plugin;
    }

    @Override
    public LocaleStorage getLocaleStorage() throws IOException {
        var allowedLocales = parseAllowedLocales(Arrays.stream(Objects.requireNonNull(getFile(Directory.MESSAGES).listFiles())).toList());
        var localesData = parseLocalesData(new JsonFile(plugin, File.DATA_JSON).getJsonFromFile());
        var defaultLocale = parseDefaultLocale(plugin.getConfiguration().getString(Config.LOCALE_DEFAULT));
        return new LocaleStorageImpl(plugin, allowedLocales, localesData, defaultLocale);
    }

    @Override
    public List<kiinse.plugins.api.darkwaterapi.files.locale.Locale> parseAllowedLocales(List<java.io.File> locales) throws IllegalArgumentException {
        if (locales == null) {
            throw new IllegalArgumentException("Files with plugin messages DarkWaterAPI is null. Failed to determine file languages.");
        }
        var list = new ArrayList<kiinse.plugins.api.darkwaterapi.files.locale.Locale>();
        for (var file : locales) {
            var fileName = file.getName().split("\\.");
            if (Objects.equals(fileName[1], "json")) {
                var locale = kiinse.plugins.api.darkwaterapi.files.locale.Locale.valueOf(fileName[0]);
                if (!isContainsLocale(list, locale)) {
                    list.add(locale);
                } else {
                    plugin.sendLog(Level.WARNING, "Locale '&c" + locale + "&6' is duplicated!");
                }
            }
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Allowed locales is empty!");
        }
        return list;
    }

    @Override
    public kiinse.plugins.api.darkwaterapi.files.locale.Locale parseDefaultLocale(String locale) throws IllegalArgumentException {
        if (locale == null) {
            throw new IllegalArgumentException("Default locale is null!");
        }
        var loc = locale.replace(" ", "");
        if (loc.isEmpty()) {
            throw new IllegalArgumentException("Default locale is empty!");
        }
        return kiinse.plugins.api.darkwaterapi.files.locale.Locale.valueOf(loc);
    }

    @Override
    public HashMap<UUID, kiinse.plugins.api.darkwaterapi.files.locale.Locale> parseLocalesData(JSONObject json) {
        var map = new HashMap<UUID, kiinse.plugins.api.darkwaterapi.files.locale.Locale>();
        for (var locale : json.keySet()) {
            map.put(UUID.fromString(locale), kiinse.plugins.api.darkwaterapi.files.locale.Locale.valueOf(json.getString(locale)));
        }
        return map;
    }

    private boolean isContainsLocale(List<kiinse.plugins.api.darkwaterapi.files.locale.Locale> allowedLocales, Locale locale) {
        for (var loc : allowedLocales) {
            if (loc.equals(locale)) {
                return true;
            }
        }
        return false;
    }
}

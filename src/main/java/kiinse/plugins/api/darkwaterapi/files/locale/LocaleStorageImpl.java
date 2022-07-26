package kiinse.plugins.api.darkwaterapi.files.locale;

import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class LocaleStorageImpl implements LocaleStorage {

    private final Locale defaultLocale;
    private final List<Locale> allowedLocales;
    private final HashMap<UUID, Locale> locales;

    public LocaleStorageImpl(DarkWaterJavaPlugin plugin, List<Locale> allowedLocales, Map<UUID, Locale> locales, Locale defLocale) throws IllegalArgumentException {
        this.allowedLocales = allowedLocales;
        plugin.sendLog("Loaded locales: '&b" + getAllowedLocalesString() + "&a'");
        this.locales = (HashMap<UUID, Locale>) locales;
        if (!isAllowedLocale(defLocale)) {
            throw new IllegalArgumentException("This default locale '" + defLocale.toString() + "' is not allowed!");
        }
        this.defaultLocale = defLocale;
        plugin.sendLog("Installed default locale: &b" + defLocale.toString());
    }

    @Override
    public boolean isAllowedLocale(Locale locale) {
        for (var loc : allowedLocales) {
            if (locale.equals(loc)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean putInLocalesData(UUID uuid, Locale locale) {
        locales.put(uuid, locale);
        return locales.containsKey(uuid);
    }

    @Override
    public boolean putInLocalesData(Player player, Locale locale) {
        var uuid = player.getUniqueId();
        locales.put(uuid, locale);
        return locales.containsKey(uuid);
    }

    @Override
    public boolean isLocalesDataContains(UUID uuid) {
        return locales.containsKey(uuid);
    }

    @Override
    public boolean isLocalesDataContains(Player player) {
        return locales.containsKey(player.getUniqueId());
    }

    @Override
    public Locale getLocalesData(UUID uuid) {
        return locales.get(uuid);
    }

    @Override
    public Locale getLocalesData(Player player) {
        return locales.get(player.getUniqueId());
    }

    @Override
    public boolean removeLocalesData(UUID uuid) {
        locales.remove(uuid);
        return !locales.containsKey(uuid);
    }

    @Override
    public boolean removeLocalesData(Player player) {
        var uuid = player.getUniqueId();
        locales.remove(uuid);
        return !locales.containsKey(uuid);
    }

    @Override
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public String getAllowedLocalesString() {
        return allowedLocales.stream().map(Locale::toString).collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public List<Locale> getAllowedLocalesList() {
        return new ArrayList<>(allowedLocales);
    }

    @Override
    public List<String> getAllowedLocalesListString() {
        var list = new ArrayList<String>();
        for (var locale : getAllowedLocalesList()) {
            list.add(locale.toString());
        }
        return list;
    }

    @Override
    public HashMap<UUID, Locale> getLocalesData() {
        return locales;
    }
}

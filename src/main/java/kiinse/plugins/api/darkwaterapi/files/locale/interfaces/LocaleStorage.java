package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface LocaleStorage {

    boolean isAllowedLocale(Locale locale);

    boolean putInLocalesData(UUID uuid, Locale locale);

    boolean putInLocalesData(Player player, Locale locale);

    boolean isLocalesDataContains(UUID uuid);

    boolean isLocalesDataContains(Player player);

    Locale getLocalesData(UUID uuid);

    Locale getLocalesData(Player player);

    boolean removeLocalesData(UUID uuid);

    boolean removeLocalesData(Player player);

    Locale getDefaultLocale();

    HashMap<UUID, Locale> getLocalesData();

    String getAllowedLocalesString();

    List<Locale> getAllowedLocalesList();

    List<String> getAllowedLocalesListString();

}

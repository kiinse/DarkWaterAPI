package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface LocaleStorage {

    boolean isAllowedLocale(@NotNull Locale locale);

    boolean putInLocalesData(@NotNull UUID uuid, @NotNull Locale locale);

    boolean putInLocalesData(@NotNull Player player, @NotNull Locale locale);

    boolean isLocalesDataContains(@NotNull UUID uuid);

    boolean isLocalesDataContains(@NotNull Player player);

    @NotNull Locale getLocalesData(@NotNull UUID uuid);

    @NotNull Locale getLocalesData(@NotNull Player player);

    boolean removeLocalesData(@NotNull UUID uuid);

    boolean removeLocalesData(@NotNull Player player);

    @NotNull Locale getDefaultLocale();

    @NotNull HashMap<UUID, Locale> getLocalesData();

    @NotNull String getAllowedLocalesString();

    @NotNull List<Locale> getAllowedLocalesList();

    @NotNull List<String> getAllowedLocalesListString();

}

package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import kiinse.plugins.api.darkwaterapi.exceptions.LocaleException;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface LocaleLoader {

    @NotNull LocaleStorage getLocaleStorage() throws LocaleException, JsonFileException;

    @NotNull List<Locale> parseAllowedLocales(@NotNull List<File> messages) throws LocaleException;

    @NotNull Locale parseDefaultLocale(@NotNull String locale) throws LocaleException;

    @NotNull HashMap<UUID, Locale> parseLocalesData(@NotNull JSONObject json);

}

package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface LocaleLoader {

    @NotNull LocaleStorage getLocaleStorage() throws IOException;

    @NotNull List<Locale> parseAllowedLocales(@NotNull List<File> messages);

    @NotNull Locale parseDefaultLocale(@NotNull String locale);

    @NotNull HashMap<UUID, Locale> parseLocalesData(@NotNull JSONObject json);

}

package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface LocaleLoader {

    LocaleStorage getLocaleStorage() throws IOException;

    List<Locale> parseAllowedLocales(List<java.io.File> messages);

    Locale parseDefaultLocale(String locale);

    HashMap<UUID, Locale> parseLocalesData(JSONObject json);

}

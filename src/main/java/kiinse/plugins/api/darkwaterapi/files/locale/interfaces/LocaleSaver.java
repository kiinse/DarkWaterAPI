package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public interface LocaleSaver {

    void saveLocaleStorage(@NotNull JSONObject json) throws IOException;

    @NotNull JSONObject parseLocalesData(@NotNull LocaleStorage storage);
}

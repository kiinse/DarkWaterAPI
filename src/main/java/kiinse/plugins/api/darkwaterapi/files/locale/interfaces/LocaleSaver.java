package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public interface LocaleSaver {

    void saveLocaleStorage(@NotNull JSONObject json) throws JsonFileException;

    @NotNull JSONObject parseLocalesData(@NotNull LocaleStorage storage);
}

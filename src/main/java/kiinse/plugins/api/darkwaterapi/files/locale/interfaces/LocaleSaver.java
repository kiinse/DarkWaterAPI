package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import org.json.JSONObject;

import java.io.IOException;

public interface LocaleSaver {

    void saveLocaleStorage(JSONObject json) throws IOException;

    JSONObject parseLocalesData(LocaleStorage storage);
}

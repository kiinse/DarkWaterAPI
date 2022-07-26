package kiinse.plugins.api.darkwaterapi.files.locale.utils;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.filemanager.JsonFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.File;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleSaver;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import org.json.JSONObject;

import java.io.IOException;

public class LocaleSaverImpl implements LocaleSaver {

    private final JsonFile jsonFile;
    private final LocaleStorage storage;

    public LocaleSaverImpl(DarkWaterAPI darkWaterAPI) {
        this.jsonFile = new JsonFile(darkWaterAPI, File.DATA_JSON);
        this.storage = darkWaterAPI.getLocaleStorage();
    }

    public void saveLocaleStorage() throws IOException {
        saveLocaleStorage(parseLocalesData(storage));
    }

    @Override
    public void saveLocaleStorage(JSONObject json) throws IOException {
        jsonFile.saveJsonToFile(json);
    }

    @Override
    public JSONObject parseLocalesData(LocaleStorage storage) {
        var json = new JSONObject();
        for (var uuid : storage.getLocalesData().keySet()) {
            json.put(uuid.toString(), storage.getLocalesData(uuid).toString());
        }
        return json;
    }
}

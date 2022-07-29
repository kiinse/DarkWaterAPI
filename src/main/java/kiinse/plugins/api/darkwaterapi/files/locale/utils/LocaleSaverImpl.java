// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.api.darkwaterapi.files.locale.utils;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import kiinse.plugins.api.darkwaterapi.files.filemanager.JsonFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.enums.File;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleSaver;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class LocaleSaverImpl implements LocaleSaver {

    private final JsonFile jsonFile;
    private final LocaleStorage storage;

    public LocaleSaverImpl(@NotNull DarkWaterAPI darkWaterAPI) {
        this.jsonFile = new JsonFile(darkWaterAPI, File.DATA_JSON);
        this.storage = darkWaterAPI.getLocaleStorage();
    }

    public void saveLocaleStorage() throws JsonFileException {
        saveLocaleStorage(parseLocalesData(storage));
    }

    @Override
    public void saveLocaleStorage(@NotNull JSONObject json) throws JsonFileException {
        jsonFile.saveJsonToFile(json);
    }

    @Override
    public @NotNull JSONObject parseLocalesData(@NotNull LocaleStorage storage) {
        var json = new JSONObject();
        for (var uuid : storage.getLocalesData().keySet()) {
            json.put(uuid.toString(), storage.getLocalesData(uuid).toString());
        }
        return json;
    }
}

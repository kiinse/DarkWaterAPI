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

package kiinse.plugins.darkwaterapi.core.files.messages;

import kiinse.plugins.darkwaterapi.api.files.enums.Directory;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.files.filemanager.FilesManager;
import kiinse.plugins.darkwaterapi.api.files.locale.Locale;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesKeys;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.core.utilities.DarkUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class DarkMessages extends FilesManager implements Messages {

    private final DarkWaterJavaPlugin plugin;
    private final HashMap<String, JSONObject> messages = new HashMap<>();

    public DarkMessages(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException {
        super(plugin);
        this.plugin = plugin;
        var directoryName = Directory.MESSAGES;
        if (isFileNotExists(directoryName) || isDirectoryEmpty(directoryName)) {
            copyFile(directoryName);
        }
        load();
    }

    @Override
    public void reload() throws JsonFileException {
        load();
    }

    private void load() throws JsonFileException {
        var darkWaterApi = this.plugin.getDarkWaterAPI();
        var locales = darkWaterApi.getLocaleStorage().getAllowedLocalesListString();
        for (var file : listFilesInDirectory(Directory.MESSAGES)) {
            var locale = file.getName().split("\\.")[0];
            if (!locales.contains(locale)) {
                plugin.sendLog(Level.WARNING, "Found localization file for language '&c" + locale + "&6' which is not loaded in &bDarkWaterAPI&6. This file will &cnot&6 be loaded into memory.");
            } else {
                messages.put(locale, getJsonFromFile(file));
            }
        }
        for (var locale : darkWaterApi.getLocaleStorage().getAllowedLocalesList()) {
            if (!isContainsLocale(locale)) {
                plugin.sendLog(Level.WARNING, "&cNo localization file found &6 for language '&c" + locale + "&6', which is available in &bDarkWaterAPI&6.");
            }
        }
        for (var entry : messages.entrySet()) {
            var locale = entry.getKey();
            for (var entryKey : entry.getValue().keySet()) {
                for (var checkingEntry : messages.entrySet()) {
                    if (!checkingEntry.getKey().equals(locale) && !checkingEntry.getValue().keySet().contains(entryKey)) {
                        plugin.sendLog(Level.WARNING, "Key '&c" + entryKey + "&6' was not found in localization '&с" + checkingEntry.getKey() + "&6', which was found in localization '&с" + locale + "&6'!");
                    }
                }

            }
        }
    }

    private boolean isContainsLocale(Locale locale) {
        for (var set : messages.keySet()) {
            if (Objects.equals(locale.toString(), set)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getStringMessage(@NotNull Locale locale, @NotNull MessagesKeys message) {
        return DarkUtils.colorize(getString(locale, message));
    }

    @Override
    public @NotNull String getStringMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message) {
        return getPrefix(locale) + DarkUtils.colorize(getString(locale, message));
    }

    @Override
    public @NotNull JSONObject getAllLocaleMessages(@NotNull Locale locale) {
        return messages.get(locale.toString());
    }

    @Override
    public @NotNull HashMap<String, JSONObject> getAllMessages() {
        return messages;
    }

    @Override
    public @NotNull String getPrefix(@NotNull Locale locale) {
        return DarkUtils.colorize(getString(locale, Message.PREFIX));
    }

    private @NotNull String getString(@NotNull Locale locale, @NotNull MessagesKeys message) {
        var json = getAllLocaleMessages(locale);
        var key = message.toString().toLowerCase();
        if (json.has(key)) {
            return json.getString(key);
        }
        return key;
    }

    private @NotNull JSONObject getJsonFromFile(@NotNull File file) throws JsonFileException {
        try (var br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            var line = br.readLine();
            if (line == null) {
                return new JSONObject();
            }
            var json = new JSONObject(Files.readString(Paths.get(file.getAbsolutePath())));
            plugin.sendLog("Messages '&b" + file.getName() + "&a' loaded");
            return json;
        } catch (IOException e) {
            throw new JsonFileException(e);
        }
    }
}

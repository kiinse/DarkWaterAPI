package kiinse.plugins.api.darkwaterapi.files.messages;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import kiinse.plugins.api.darkwaterapi.files.filemanager.enums.Directory;
import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.FilesManager;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.ComponentLabels;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.Messages;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.MessagesKeys;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
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

public class DarkWaterMessages extends FilesManager implements Messages {

    private final ComponentLabels componentLabels = new ParseComponentLabels();
    private final DarkWaterJavaPlugin plugin;
    private final HashMap<String, JSONObject> messages = new HashMap<>();

    public DarkWaterMessages(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException {
        super(plugin);
        this.plugin = plugin;
        var directoryName = Directory.MESSAGES;
        if (isFileNotExists(directoryName)) {
            copyFile(directoryName);
        }
        load();
    }

    @Override
    public void reload() throws JsonFileException {
        load();
    }

    private void load() throws JsonFileException {
        var locales = DarkWaterAPI.getInstance().getLocaleStorage().getAllowedLocalesListString();
        for (var file : listFilesInDirectory(Directory.MESSAGES)) {
            var locale = file.getName().split("\\.")[0];
            if (!locales.contains(locale)) {
                plugin.sendLog(Level.WARNING, "Found localization file for language '&c" + locale + "&6' which is not loaded in &bDarkWaterAPI&6. This file will &cnot&6 be loaded into memory.");
            } else {
                messages.put(locale, getJsonFromFile(file));
            }
        }
        for (var locale : DarkWaterAPI.getInstance().getLocaleStorage().getAllowedLocalesList()) {
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
        return colorize(getString(locale, message));
    }

    @Override
    public @NotNull Component getComponentMessage(@NotNull Locale locale, @NotNull MessagesKeys message) {
        return componentLabels.parseMessage(colorize(getString(locale, message)));
    }

    @Override
    public @NotNull String getStringMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message) {
        return getPrefix(locale) + colorize(getString(locale, message));
    }

    @Override
    public @NotNull Component getComponentMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message) {
        return Component.text(getPrefix(locale)).append(componentLabels.parseMessage(colorize(getString(locale, message))));
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
        return colorize(getString(locale, Message.PREFIX));
    }

    @Override
    public @NotNull String colorize(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
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

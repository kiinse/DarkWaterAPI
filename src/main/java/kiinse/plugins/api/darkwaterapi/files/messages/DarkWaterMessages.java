package kiinse.plugins.api.darkwaterapi.files.messages;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.filemanager.FilesManager;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.Directory;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.ComponentLabels;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.Messages;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.MessagesKeys;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Message;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
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

    public DarkWaterMessages(DarkWaterJavaPlugin plugin) throws IOException {
        super(plugin);
        this.plugin = plugin;
        var directoryName = Directory.MESSAGES;
        if (isFileNotExists(directoryName)) {
            copyFile(directoryName);
        }
        load();
    }

    @Override
    public void reload() throws IOException {
        load();
    }

    private void load() throws IOException {
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
    public String getStringMessage(Locale locale, MessagesKeys message) {
        return colorize(getString(locale, message));
    }

    @Override
    public Component getComponentMessage(Locale locale, MessagesKeys message) {
        return componentLabels.parseMessage(colorize(getString(locale, message)));
    }

    @Override
    public String getStringMessageWithPrefix(Locale locale, MessagesKeys message) {
        return getPrefix(locale) + colorize(getString(locale, message));
    }

    @Override
    public Component getComponentMessageWithPrefix(Locale locale, MessagesKeys message) {
        return Component.text(getPrefix(locale)).append(componentLabels.parseMessage(colorize(getString(locale, message))));
    }

    @Override
    public JSONObject getAllLocaleMessages(Locale locale) {
        return messages.get(locale.toString());
    }

    @Override
    public HashMap<String, JSONObject> getAllMessages() {
        return messages;
    }

    @Override
    public String getPrefix(Locale locale) {
        return colorize(getString(locale, Message.PREFIX));
    }

    @Override
    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String getString(Locale locale, MessagesKeys message) {
        var json = getAllLocaleMessages(locale);
        var key = message.toString().toLowerCase();
        if (json.has(key)) {
            return json.getString(key);
        }
        return key;
    }

    private JSONObject getJsonFromFile(File file) throws IOException {
        try (var br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            var line = br.readLine();
            if (line == null) {
                return new JSONObject();
            }
            var json = new JSONObject(Files.readString(Paths.get(file.getAbsolutePath())));
            plugin.sendLog("Messages '&b" + file.getName() + "&a' loaded");
            return json;
        }
    }
}

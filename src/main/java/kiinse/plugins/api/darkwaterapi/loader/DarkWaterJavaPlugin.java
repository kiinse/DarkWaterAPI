package kiinse.plugins.api.darkwaterapi.loader;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.Configuration;
import kiinse.plugins.api.darkwaterapi.files.config.utils.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.FilesKeys;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.File;
import kiinse.plugins.api.darkwaterapi.files.messages.DarkWaterMessages;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.logging.Level;

/**
 * DarkWaterJavaPlugin plugin class
 */
public abstract class DarkWaterJavaPlugin extends JavaPlugin {

    protected FilesKeys configName = File.CONFIG_YML;

    // TODO: Add DarkWaterAPI version check for plugin. If the version is greater than 0.1.0, then warn the user about possible errors in the work.

    /**
     * Configuration
     */
    protected YamlFile configuration;

    /**
     * Messages
     */
    protected Messages messages;

    @Override
    public void onEnable() {
        start();
    }

    @Override
    public void onDisable() {
        stop();
    }

    protected void start() {
        try {
            getLogger().setLevel(Level.CONFIG);
            sendLog("Loading " + getName() + "...");
            configuration = new Configuration(this);
            messages = new DarkWaterMessages(this);
            onStart();
            getDarkWaterAPI().getPluginManager().registerPlugin(this);
            sendInfo();
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on loading " + getName() + "! Message: " + e.getMessage());
        }
    }

    protected void stop() {
        try {
            sendLog("Disabling " + getName() + "...");
            onStop();
            sendConsole(" &6|==============================");
            sendConsole(" &6|  &f" + getName() + " &cdisabled!");
            sendConsole(" &6|==============================");
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on disabling " + getName() + "! Message: " + e.getMessage());
        }
    }

    /**
     * Actions when the plugin starts
     */
    public abstract void onStart() throws Exception;

    /**
     * Actions when the plugin is turned off
     */
    public abstract void onStop() throws Exception;

    /**
     * Get configuration file name
     */
    public FilesKeys getConfigurationFileName() {
        return configName;
    }

    /**
     * Receiving plugin messages
     * @return Messages plugin {@link Messages}
     */
    public Messages getMessages() {
        return messages;
    }

    /**
     * Get plugin configuration
     * @return Plugin config {@link YamlFile}
     */
    public YamlFile getConfiguration() {
        return configuration;
    }

    /**
     * Get an instance of DarkWaterAPI
     * @return {@link DarkWaterAPI}
     */
    public DarkWaterAPI getDarkWaterAPI() {return DarkWaterAPI.getInstance();}

    /**
     * Plugin Restart (Performs actions on shutdown and then on)
     */
    public void restart() {
        try {
            sendLog("Reloading " + getName() + "...");
            onStop();
            configuration = new Configuration(this);
            messages = new DarkWaterMessages(this);
            onStart();
            sendConsole(" &6|==============================");
            sendConsole(" &6|  &f" + getName() + " &areloaded!");
            sendConsole(" &6|==============================");
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on reloading " + getName() + "! Message: " + e.getMessage());
        }
    }

    /**
     * Getting data from the plugin
     * Default returns plugin version, API version, authors and description
     * @return JSONObject with data
     */
    public JSONObject getPluginData() {
        var description = this.getDescription();
        var map = new HashMap<String, String>();
        map.put("authors", String.valueOf(description.getAuthors()));
        map.put("version", description.getVersion());
        map.put("api", description.getAPIVersion());
        map.put("description", description.getDescription());
        return new JSONObject(map);
    }

    /**
     * Send logs to INFO level console
     * @param msg Message
     */
    public void sendLog(String msg) {
        sendLog(Level.INFO, msg);
    }

    /**
     * Send logs to console
     * @param level Logging level
     * @param msg Message
     */
    public void sendLog(Level level, String msg) {
        if (level == Level.INFO) {
            sendConsole("&6[&b" + getName() + "&6]&a " + msg);
            return;
        }
        if (level == Level.WARNING) {
            sendConsole("&6[&b" + getName() + "&f/&cWARN&6] " + msg);
            return;
        }
        if (level == Level.CONFIG && getDarkWaterAPI().getConfiguration() != null && getDarkWaterAPI().getConfiguration().getBoolean(Config.DEBUG)) {
            sendConsole("&6[&b" + getName() + "&f/&dDEBUG&6] " + msg);
            return;
        }
        getLogger().log(level, msg);
    }

    /**
     * Sending a message to the console
     * @param message Message
     */
    public void sendConsole(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Sending information from the plugin
     */
    protected void sendInfo() {
        sendConsole(" &6|==============================");
        sendConsole(" &6|  &f" + getName() + " &bloaded!");
        sendConsole(" &6|  &bAuthors: &f" + getDescription().getAuthors());
        sendConsole(" &6|  &bWebsite: &f" + getDescription().getWebsite());
        sendConsole(" &6|  &bPlugin version: &f" + getDescription().getVersion());
        sendConsole(" &6|==============================");
    }
}

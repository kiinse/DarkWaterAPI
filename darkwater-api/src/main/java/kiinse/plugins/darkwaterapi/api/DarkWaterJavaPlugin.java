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

package kiinse.plugins.darkwaterapi.api;

import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.exceptions.PluginException;
import kiinse.plugins.darkwaterapi.api.files.enums.File;
import kiinse.plugins.darkwaterapi.api.files.filemanager.FilesKeys;
import kiinse.plugins.darkwaterapi.api.files.filemanager.YamlFile;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Utility;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * DarkWaterJavaPlugin plugin class
 */
@SuppressWarnings("unused")
public abstract class DarkWaterJavaPlugin extends JavaPlugin {

    private static DarkWaterMain darkWaterMain;

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
        try {
            start();
        } catch (PluginException e) {
            sendLog(Level.SEVERE, "Error on loading " + getName() + "! Message: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        try {
            stop();
        } catch (PluginException e) {
            sendLog(Level.SEVERE, "Error on disabling " + getName() + "! Message: " + e.getMessage());
        }
    }

    protected void start() throws PluginException {
        try {
            getLogger().setLevel(Level.CONFIG);
            sendLog("Loading " + getName() + "...");
            configuration = new YamlFile(this, getConfigurationFileName());
            messages = getDarkWaterAPI().getMessages(this);
            onStart();
            if (!getDarkWaterAPI().getPluginManager().hasPlugin(this)) {
                getDarkWaterAPI().getPluginManager().registerPlugin(this);
            }
            sendInfo();
        } catch (Exception e) {
            throw new PluginException(e);
        }
    }

    protected void stop() throws PluginException {
        try {
            sendLog("Disabling " + getName() + "...");
            onStop();
            sendConsole(" &6|==============================");
            sendConsole(" &6|  &f" + getName() + " &cdisabled!");
            sendConsole(" &6|==============================");
        } catch (Exception e) {
            throw new PluginException(e);
        }
    }

    /**
     * Plugin Restart (Performs actions on shutdown and then actions on startup)
     */
    public void restart() {
        try {
            sendLog("Reloading " + getName() + "...");
            onStop();
            reloadConfiguration();
            reloadMessages();
            onStart();
            sendConsole(" &6|==============================");
            sendConsole(" &6|  &f" + getName() + " &areloaded!");
            sendConsole(" &6|==============================");
        } catch (Exception e) {
            sendLog(Level.SEVERE, "Error on reloading " + getName() + "! Message: " + e.getMessage());
        }
    }

    protected void setDarkWaterAPI(DarkWaterMain darkWater) {
        darkWaterMain = darkWater;
    }

    /**
     * Reloading plugin config file
     */
    public void reloadConfiguration() {
        configuration = new YamlFile(this, getConfigurationFileName());
    }

    /**
     * Reloading plugin message files
     */
    public void reloadMessages() throws JsonFileException {
        messages = getDarkWaterAPI().getMessages(this);
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
    public @NotNull FilesKeys getConfigurationFileName() {
        return File.CONFIG_YML;
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
     * @return {@link DarkWaterMain}
     */
    @Utility
    public @NotNull DarkWaterMain getDarkWaterAPI() {
        return darkWaterMain;
    }

    /**
     * Send INFO level logs to console
     * @param msg Message
     */
    public void sendLog(@NotNull String msg) {
        sendLog(Level.INFO, msg);
    }

    /**
     * Send logs to console
     * @param level Logging level
     * @param msg Message
     */
    public void sendLog(@NotNull Level level, @NotNull String msg) {
        if (level.equals(Level.INFO)) {
            sendConsole("&6[&b" + getName() + "&6]&a " + msg);
            return;
        }
        if (level.equals(Level.WARNING)) {
            sendConsole("&6[&b" + getName() + "&f/&cWARN&6] " + msg);
            return;
        }
        if (level.equals(Level.CONFIG) && getDarkWaterAPI().isDebug()) {
            sendConsole("&6[&b" + getName() + "&f/&dDEBUG&6] " + msg);
            return;
        }
        getLogger().log(level, msg);
    }

    /**
     * Sending a message to the console
     * @param message Message
     */
    @Utility
    public void sendConsole(@NotNull String message) {
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

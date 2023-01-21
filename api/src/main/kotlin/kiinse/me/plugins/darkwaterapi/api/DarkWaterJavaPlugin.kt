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
package kiinse.me.plugins.darkwaterapi.api

import kiinse.me.plugins.darkwaterapi.api.exceptions.JsonFileException
import kiinse.me.plugins.darkwaterapi.api.exceptions.PluginException
import kiinse.me.plugins.darkwaterapi.api.files.enums.File
import kiinse.me.plugins.darkwaterapi.api.files.filemanager.FilesKeys
import kiinse.me.plugins.darkwaterapi.api.files.filemanager.YamlFile
import kiinse.me.plugins.darkwaterapi.api.files.messages.Messages
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Utility
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

/**
 * DarkWaterJavaPlugin plugin class
 */
@Suppress("unused")
abstract class DarkWaterJavaPlugin : JavaPlugin() {

    private val textBarrierConst = " &6|=============================="

    /**
     * Configuration
     */
    private var configuration: YamlFile? = null

    /**
     * Messages
     */
    private var messages: Messages? = null

    fun getConfiguration(): YamlFile {
        return configuration!!
    }

    fun getMessages(): Messages {
        return messages!!
    }

    fun setConfiguration(configuration: YamlFile) {
        this.configuration = configuration
    }

    fun setMessages(messages: Messages) {
        this.messages = messages
    }

    override fun onEnable() {
        try {
            start()
        } catch (e: PluginException) {
            sendLog(Level.SEVERE, "Error on loading $name! Message:", e)
        }
    }

    override fun onDisable() {
        try {
            stop()
        } catch (e: PluginException) {
            sendLog(Level.SEVERE, "Error on disabling $name! Message:", e)
        }
    }

    @Throws(PluginException::class)
    protected open fun start() {
        try {
            logger.level = Level.CONFIG
            sendLog("Loading $name...")
            configuration = YamlFile(this, configurationFileName)
            messages = darkWaterAPI.getMessages(this)
            onStart()
            if (!darkWaterAPI.pluginManager.hasPlugin(this)) darkWaterAPI.pluginManager.registerPlugin(this)
            sendInfo()
        } catch (e: Exception) {
            throw PluginException(e)
        }
    }

    @Throws(PluginException::class)
    protected fun stop() {
        try {
            sendLog("Disabling $name...")
            onStop()
            sendConsole(textBarrierConst)
            sendConsole(" &6|  &f$name &cdisabled!")
            sendConsole(textBarrierConst)
        } catch (e: Exception) {
            throw PluginException(e)
        }
    }

    /**
     * Plugin Restart (Performs actions on shutdown and then actions on startup)
     */
    open fun restart() {
        try {
            sendLog("Reloading $name...")
            onStop()
            reloadConfiguration()
            reloadMessages()
            onStart()
            sendConsole(textBarrierConst)
            sendConsole(" &6|  &f$name &areloaded!")
            sendConsole(textBarrierConst)
        } catch (e: Exception) {
            sendLog(Level.SEVERE, "Error on reloading $name! Message:", e)
        }
    }

    /**
     * Reloading plugin config file
     */
    fun reloadConfiguration() {
        configuration = YamlFile(this, configurationFileName)
    }

    /**
     * Reloading plugin message files
     */
    @Throws(JsonFileException::class)
    fun reloadMessages() {
        messages = darkWaterAPI.getMessages(this)
    }

    /**
     * Actions when the plugin starts
     */
    @Throws(Exception::class)
    abstract fun onStart()

    /**
     * Actions when the plugin is turned off
     */
    @Throws(Exception::class)
    abstract fun onStop()

    val configurationFileName: FilesKeys = File.CONFIG_YML

    @get:Utility
    var darkWaterAPI: DarkWaterMain
        /**
         * Get an instance of DarkWaterAPI
         *
         * @return [DarkWaterMain]
         */
        get() = darkWaterMain!!
        protected set(darkWater) {
            darkWaterMain = darkWater
        }

    /**
     * Send INFO level logs to console
     *
     * @param msg Message
     */
    fun sendLog(msg: String) {
        sendLog(Level.INFO, msg)
    }

    /**
     * Send logs and debug stacktrace to console
     *
     * @param throwable Throwable
     */
    fun sendLog(level: Level, message: String, throwable: Throwable) {
        sendLog(level, message + " " + throwable.message)
        if (darkWaterAPI.isDebug) throwable.printStackTrace()
    }

    /**
     * Send WARNING level logs and debug stacktrace to console
     *
     * @param throwable Throwable
     */
    fun sendLog(message: String, throwable: Throwable) {
        sendLog(Level.WARNING, message + " " + throwable.message)
        if (darkWaterAPI.isDebug) throwable.printStackTrace()
    }

    /**
     * Send WARNING level logs and debug stacktrace to console
     *
     * @param throwable Throwable
     */
    fun sendLog(throwable: Throwable) {
        sendLog(Level.WARNING, throwable.message!!)
        if (darkWaterAPI.isDebug) throwable.printStackTrace()
    }

    /**
     * Send logs to console
     *
     * @param level Logging level
     * @param msg   Message
     */
    fun sendLog(level: Level, msg: String) {
        when (level) {
            Level.INFO -> sendConsole("&6[&b$name&6]&a $msg")
            Level.WARNING, Level.SEVERE -> sendConsole("&6[&b$name&f/&c$level&6] $msg")
            Level.CONFIG -> if (darkWaterAPI.isDebug) sendConsole("&6[&b$name&f/&dDEBUG&6] $msg")
            else -> logger.log(level, msg)
        }
    }

    /**
     * Sending a message to the console
     *
     * @param message Message
     */
    @Utility
    fun sendConsole(message: String) {
        Bukkit.getServer().consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    /**
     * Sending information from the plugin
     */
    protected fun sendInfo() {
        sendConsole(textBarrierConst)
        sendConsole(" &6|  &f$name &bloaded!")
        sendConsole(" &6|  &bAuthors: &f${description.authors}")
        sendConsole(" &6|  &bWebsite: &f${description.website}")
        sendConsole(" &6|  &bPlugin version: &f${description.version}")
        sendConsole(textBarrierConst)
    }

    companion object {
        private var darkWaterMain: DarkWaterMain? = null
    }
}
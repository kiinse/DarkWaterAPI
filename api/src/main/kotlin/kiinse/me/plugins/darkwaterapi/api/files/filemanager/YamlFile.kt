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
package kiinse.me.plugins.darkwaterapi.api.files.filemanager

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.YamlFileException
import kiinse.me.plugins.darkwaterapi.api.files.enums.Config
import kiinse.me.plugins.darkwaterapi.api.files.enums.File
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.logging.Level

@Suppress("unused")
class YamlFile(plugin: DarkWaterJavaPlugin, fileName: FilesKeys) : FilesManager(plugin) {
    private val fileName: FilesKeys
    private var file: YamlConfiguration

    init {
        if (isFileNotExists(fileName)) copyFile(fileName)
        this.fileName = fileName
        file = YamlConfiguration.loadConfiguration(getFile(fileName))
        try {
            checkVersion(plugin)
        } catch (e: YamlFileException) {
            plugin.sendLog(Level.WARNING, "An error occurred while copying the new version of the file '&c${getFile(fileName).name}&6'! Message: ", e)
        }
        plugin.sendLog("File '&b" + getFileName(fileName) + "&a' loaded")
    }

    @Throws(YamlFileException::class)
    private fun checkVersion(plugin: DarkWaterJavaPlugin) {
        val cfgVersion = getDouble(Config.CONFIG_VERSION)
        val tmpCfg = File.CONFIG_TMP_YML
        deleteFile(tmpCfg)
        copyFile(fileName, tmpCfg)
        val newVersion: Double = YamlConfiguration.loadConfiguration(getFile(tmpCfg)).getDouble(getKeyString(Config.CONFIG_VERSION))
        if (newVersion > cfgVersion || newVersion < cfgVersion) {
            try {
                val oldCfg = File.CONFIG_OLD_YML
                deleteFile(oldCfg)
                copyFileInFolder(fileName, oldCfg)
                deleteFile(fileName)
                copyFile(fileName)
                file = YamlConfiguration.loadConfiguration(getFile(fileName))
                val cfgName = getFile(fileName).name
                plugin.sendLog(Level.WARNING, "Version mismatch found for file '&c${cfgName}&6'. This file has been renamed to '&c${getFile(oldCfg).name}&6' and a new file '&c${cfgName}&6' has been created")
            } catch (e: Exception) {
                throw YamlFileException(e)
            }
        }
        deleteFile(tmpCfg)
    }

    fun reload() {
        file = YamlConfiguration.loadConfiguration(getFile(fileName))
    }

    fun getString(key: YamlKeys): String {
        val string = file.getString(getKeyString(key))
        return string ?: ""
    }

    fun getObject(key: YamlKeys): Any? {
        return file[getKeyString(key)]
    }

    fun getObject(key: String): Any? {
        return file[key]
    }

    fun getBoolean(key: YamlKeys): Boolean {
        return file.getBoolean(getKeyString(key))
    }

    fun getDouble(key: YamlKeys): Double {
        return file.getDouble(getKeyString(key))
    }

    fun getInt(key: YamlKeys): Int {
        return file.getInt(getKeyString(key))
    }

    fun getItemStack(key: YamlKeys): ItemStack? {
        return file.getItemStack(getKeyString(key))
    }

    fun getStringList(key: YamlKeys): List<String> {
        return file.getStringList(getKeyString(key))
    }

    private fun getKeyString(key: YamlKeys): String {
        return key.toString().lowercase(Locale.getDefault()).replace("_", ".")
    }
}
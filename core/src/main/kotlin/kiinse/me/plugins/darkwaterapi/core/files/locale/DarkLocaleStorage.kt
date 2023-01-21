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
package kiinse.me.plugins.darkwaterapi.core.files.locale

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.JsonFileException
import kiinse.me.plugins.darkwaterapi.api.exceptions.LocaleException
import kiinse.me.plugins.darkwaterapi.api.files.enums.Config
import kiinse.me.plugins.darkwaterapi.api.files.enums.Directory
import kiinse.me.plugins.darkwaterapi.api.files.enums.File
import kiinse.me.plugins.darkwaterapi.api.files.filemanager.FilesManager
import kiinse.me.plugins.darkwaterapi.api.files.filemanager.JsonFile
import kiinse.me.plugins.darkwaterapi.api.files.locale.LocaleStorage
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale
import org.bukkit.entity.Player
import org.json.JSONObject
import java.util.*
import java.util.logging.Level

class DarkLocaleStorage(plugin: DarkWaterJavaPlugin) : FilesManager(plugin), LocaleStorage {

    private val plugin: DarkWaterJavaPlugin
    private val jsonFile: JsonFile
    private var defaultPlayerLocale: PlayerLocale? = null
    private var allowedPlayerLocales: Set<PlayerLocale>? = null
    private var locales: HashMap<UUID, PlayerLocale>? = null

    init {
        val directoryName = Directory.MESSAGES
        if (isFileNotExists(directoryName)) copyFile(directoryName)
        this.plugin = plugin
        jsonFile = JsonFile(plugin, File.DATA_JSON)
    }

    @Throws(LocaleException::class, JsonFileException::class)
    override fun load(): LocaleStorage {
        allowedPlayerLocales = parseAllowedLocales(Arrays.stream(Objects.requireNonNull(getFile(Directory.MESSAGES).listFiles())).toList())
        plugin.sendLog("Loaded locales: '&b$allowedLocalesString&a'")
        locales = parseLocalesData(jsonFile.jsonFromFile)
        val defLocale: PlayerLocale = parseDefaultLocale(plugin.getConfiguration().getString(Config.LOCALE_DEFAULT))
        if (!isAllowedLocale(defLocale)) throw LocaleException("This default locale '$defLocale' is not allowed!")
        defaultPlayerLocale = defLocale
        plugin.sendLog("Installed default locale: &b$defLocale")
        return this
    }

    @Throws(JsonFileException::class)
    override fun save(): LocaleStorage {
        val json = JSONObject()
        localesData.keys.forEach { json.put(it.toString(), getLocalesData(it).toString()) }
        jsonFile.saveJsonToFile(json)
        return this
    }

    override fun isAllowedLocale(playerLocale: PlayerLocale): Boolean {
        allowedPlayerLocales?.forEach { if (it == playerLocale) return true }
        return false
    }

    override fun putInLocalesData(uuid: UUID, playerLocale: PlayerLocale): Boolean {
        locales!![uuid] = playerLocale
        return locales!!.containsKey(uuid)
    }

    override fun putInLocalesData(player: Player, playerLocale: PlayerLocale): Boolean {
        val uuid = player.uniqueId
        locales!![uuid] = playerLocale
        return locales!!.containsKey(uuid)
    }

    override fun isLocalesDataContains(uuid: UUID): Boolean {
        return locales!!.containsKey(uuid)
    }

    override fun isLocalesDataContains(player: Player): Boolean {
        return locales!!.containsKey(player.uniqueId)
    }

    override fun getLocalesData(uuid: UUID): PlayerLocale? {
        return locales!![uuid]
    }

    override fun getLocalesData(player: Player): PlayerLocale? {
        return locales!![player.uniqueId]
    }

    override fun removeLocalesData(uuid: UUID): Boolean {
        locales!!.remove(uuid)
        return !locales!!.containsKey(uuid)
    }

    override fun removeLocalesData(player: Player): Boolean {
        val uuid = player.uniqueId
        locales!!.remove(uuid)
        return !locales!!.containsKey(uuid)
    }

    override val defaultLocale: PlayerLocale
        get() = defaultPlayerLocale!!
    override val allowedLocalesString: String
        get() = allowedPlayerLocales!!.joinToString(", ", "[", "]")
    override val allowedLocalesList: Set<PlayerLocale>
        get() = HashSet(allowedPlayerLocales!!)
    override val allowedLocalesListString: Set<String>
        get() {
            val set = HashSet<String>()
            allowedLocalesList.forEach { set.add(it.toString()) }
            return set
        }
    override val localesData: HashMap<UUID, PlayerLocale>
        get() = locales!!

    @Throws(LocaleException::class)
    private fun parseAllowedLocales(locales: MutableList<java.io.File>): Set<PlayerLocale> {
        val set: HashSet<PlayerLocale> = HashSet<PlayerLocale>()
        for (file in locales) {
            val fileName = file.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (fileName[1] == "json") {
                val locale = Locale(fileName[0])
                if (!isContainsLocale(set, locale)) {
                    set.add(locale)
                } else {
                    plugin.sendLog(Level.WARNING, "Locale '&c$locale&6' is duplicated!")
                }
            }
        }
        if (set.isEmpty()) throw LocaleException("Allowed locales is empty!")
        return set
    }

    @Throws(LocaleException::class)
    private fun parseDefaultLocale(locale: String): PlayerLocale {
        val loc = locale.replace(" ", "")
        if (loc.isEmpty()) throw LocaleException("Default locale is empty!")
        return Locale(loc)
    }

    private fun parseLocalesData(json: JSONObject): HashMap<UUID, PlayerLocale> {
        val map: HashMap<UUID, PlayerLocale> = HashMap<UUID, PlayerLocale>()
        json.keySet().forEach { map[UUID.fromString(it)] = Locale(json.getString(it)) }
        return map
    }

    private fun isContainsLocale(allowedPlayerLocales: Set<PlayerLocale>, playerLocale: PlayerLocale): Boolean {
        allowedPlayerLocales.forEach { if (it == playerLocale) return true }
        return false
    }
}
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
package kiinse.me.plugins.darkwaterapi.core.files.statistic

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.JsonFileException
import kiinse.me.plugins.darkwaterapi.api.files.enums.File
import kiinse.me.plugins.darkwaterapi.api.files.filemanager.JsonFile
import kiinse.me.plugins.darkwaterapi.api.files.statistic.DarkStatistic
import kiinse.me.plugins.darkwaterapi.api.files.statistic.StatisticManager
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.json.JSONObject
import java.util.*

class DarkStatisticManager(plugin: DarkWaterJavaPlugin) : JsonFile(plugin, File.STATISTIC_JSON), StatisticManager {

    private val statistic: HashMap<UUID, DarkStatistic> = HashMap<UUID, DarkStatistic>()

    init {
        load(jsonFromFile)
    }

    private fun load(jsonObject: JSONObject): StatisticManager {
        for (key in jsonObject.keySet()) {
            val uuid = UUID.fromString(key)
            statistic[uuid] = object : Statistic(uuid, jsonObject.getJSONObject(key)), DarkStatistic {}
        }
        return this
    }

    fun hasPlayer(player: Player): Boolean {
        return statistic.containsKey(player.uniqueId)
    }

    fun hasPlayer(player: UUID): Boolean {
        return statistic.containsKey(player)
    }

    private fun putPlayer(player: Player): StatisticManager {
        statistic[player.uniqueId] = object : Statistic(player, JSONObject()) {}
        return this
    }

    private fun putPlayer(player: UUID): StatisticManager {
        statistic[player] = object : Statistic(player, JSONObject()) {}
        return this
    }

    @Throws(JsonFileException::class)
    override fun save(): StatisticManager {
        val json = JSONObject()
        for ((key1, value) in statistic) {
            json.put(key1.toString(), value.toJSONObject())
        }
        saveJsonToFile(json)
        return this
    }

    @Throws(JsonFileException::class)
    override fun reload(): StatisticManager {
        load(jsonFromFile)
        return this
    }

    override fun getPlayerStatistic(player: Player): DarkStatistic {
        if (!hasPlayer(player)) putPlayer(player)
        return statistic[player.uniqueId]!!
    }

    override fun getPlayerStatistic(player: UUID): DarkStatistic {
        if (!hasPlayer(player)) putPlayer(player)
        return statistic[player]!!
    }

    override fun setPlayerStatistic(player: Player, statistic: DarkStatistic): StatisticManager {
        val uuid = player.uniqueId
        this.statistic.remove(uuid)
        this.statistic[uuid] = statistic
        return this
    }

    override fun setPlayerStatistic(player: UUID, statistic: DarkStatistic): StatisticManager {
        this.statistic.remove(player)
        this.statistic[player] = statistic
        return this
    }

    override fun addStatistic(player: Player, type: EntityType): StatisticManager {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type))
        return this
    }

    override fun addStatistic(player: UUID, type: EntityType): StatisticManager {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type))
        return this
    }

    override fun updatePlayer(player: Player, stats: JSONObject): StatisticManager {
        setPlayerStatistic(player, object : Statistic(player, stats) {})
        return this
    }

    override fun updatePlayer(player: UUID, stats: JSONObject): StatisticManager {
        setPlayerStatistic(player, object : Statistic(player, stats) {})
        return this
    }

    override val allPlayersStats: HashMap<UUID, DarkStatistic>
        get() = statistic
}
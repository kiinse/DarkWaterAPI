package kiinse.me.plugins.darkwaterapi.core.files.statistic

import kiinse.me.plugins.darkwaterapi.api.files.statistic.DarkStatistic
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.json.JSONObject
import java.util.*

open class Statistic : DarkStatistic {

    final override val playerUUID: UUID
    private var stats: MutableMap<EntityType, Int> = EnumMap(org.bukkit.entity.EntityType::class.java)

    protected constructor(player: UUID, json: JSONObject) {
        playerUUID = player
        load(json)
    }

    protected constructor(player: Player, json: JSONObject) {
        playerUUID = player.uniqueId
        load(json)
    }

    private fun load(jsonObject: JSONObject) {
        for (key in jsonObject.keySet()) {
            stats[EntityType.valueOf(key)] = jsonObject.getInt(key)
        }
    }

    override val allStatistic: Map<EntityType, Int>
        get() = stats

    override fun getStatistic(type: EntityType): Int {
        return if (stats.containsKey(type)) {
            stats[type]!!
        } else 0
    }

    override fun setStatistic(type: EntityType, amount: Int): DarkStatistic {
        stats.remove(type)
        stats[type] = amount
        return this
    }

    override fun setStatistic(stats: MutableMap<EntityType, Int>): DarkStatistic {
        this.stats = stats
        return this
    }

    override fun addStatistic(type: EntityType): DarkStatistic {
        stats[type] = getStatistic(type) + 1
        return this
    }

    override fun toJSONObject(): JSONObject {
        val json = JSONObject()
        for ((key, value) in stats) {
            json.put(key.toString(), value)
        }
        return json
    }
}
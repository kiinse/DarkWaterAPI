package kiinse.plugins.api.darkwaterapi.files.statistic.interfaces;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public abstract class Statistic {

    private final UUID player;
    private Map<EntityType, Integer> stats = new HashMap<>();

    protected Statistic(UUID player, JSONObject json) {
        this.player = player;
        load(json);
    }

    protected Statistic(Player player, JSONObject json) {
        this.player = player.getUniqueId();
        load(json);
    }

    private void load(JSONObject jsonObject) {
        for (var key : jsonObject.keySet()) {
            stats.put(EntityType.valueOf(key), jsonObject.getInt(key));
        }
    }

    public UUID getPlayerUUID() {
        return player;
    }

    public Map<EntityType, Integer> getAllStatistic() {
        return stats;
    }

    public int getStatistic(EntityType type) {
        if (stats.containsKey(type)) {
            return stats.get(type);
        }
        return 0;
    }

    public Statistic setStatistic(EntityType type, int amount) {
        stats.remove(type);
        stats.put(type, amount);
        return this;
    }

    public Statistic setStatistic(Map<EntityType, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public Statistic addStatistic(EntityType type) {
        stats.put(type, getStatistic(type) + 1);
        return this;
    }

    public JSONObject toJSONObject() {
        var json = new JSONObject();
        for (var stat : stats.entrySet()) {
            json.put(stat.getKey().toString(), stat.getValue());
        }
        return json;
    }

}

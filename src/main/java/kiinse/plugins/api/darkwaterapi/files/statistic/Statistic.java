package kiinse.plugins.api.darkwaterapi.files.statistic;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public abstract class Statistic {

    private final UUID player;
    private Map<EntityType, Integer> stats = new HashMap<>();

    protected Statistic(@NotNull UUID player, @NotNull JSONObject json) {
        this.player = player;
        load(json);
    }

    protected Statistic(@NotNull Player player, @NotNull JSONObject json) {
        this.player = player.getUniqueId();
        load(json);
    }

    private void load(@NotNull JSONObject jsonObject) {
        for (var key : jsonObject.keySet()) {
            stats.put(EntityType.valueOf(key), jsonObject.getInt(key));
        }
    }

    public @NotNull UUID getPlayerUUID() {
        return player;
    }

    public @NotNull Map<EntityType, Integer> getAllStatistic() {
        return stats;
    }

    public int getStatistic(@NotNull EntityType type) {
        if (stats.containsKey(type)) {
            return stats.get(type);
        }
        return 0;
    }

    public @NotNull Statistic setStatistic(@NotNull EntityType type, int amount) {
        stats.remove(type);
        stats.put(type, amount);
        return this;
    }

    public @NotNull Statistic setStatistic(@NotNull Map<EntityType, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public @NotNull Statistic addStatistic(@NotNull EntityType type) {
        stats.put(type, getStatistic(type) + 1);
        return this;
    }

    public @NotNull JSONObject toJSONObject() {
        var json = new JSONObject();
        for (var stat : stats.entrySet()) {
            json.put(stat.getKey().toString(), stat.getValue());
        }
        return json;
    }

}

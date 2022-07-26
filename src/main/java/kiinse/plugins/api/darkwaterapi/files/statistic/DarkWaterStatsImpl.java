package kiinse.plugins.api.darkwaterapi.files.statistic;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.filemanager.JsonFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.File;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.DarkWaterStatistic;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class DarkWaterStatsImpl extends JsonFile implements DarkWaterStatistic {

    private final HashMap<UUID, Statistic> statistic = new HashMap<>();

    public DarkWaterStatsImpl(DarkWaterAPI darkWaterAPI) throws IOException {
        super(darkWaterAPI, File.STATISTIC_JSON);
        load(getJsonFromFile());
    }

    private void load(JSONObject jsonObject) {
        for (var key : jsonObject.keySet()) {
            var uuid = UUID.fromString(key);
            statistic.put(uuid, new Statistic(uuid, jsonObject.getJSONObject(key)) {});
        }
    }

    public boolean hasPlayer(Player player) {
        return statistic.containsKey(player.getUniqueId());
    }

    public boolean hasPlayer(UUID player) {
        return statistic.containsKey(player);
    }

    private void putPlayer(Player player) {
        statistic.put(player.getUniqueId(), new Statistic(player, new JSONObject()) {});
    }

    private void putPlayer(UUID player) {
        statistic.put(player, new Statistic(player, new JSONObject()) {});
    }

    @Override
    public DarkWaterStatistic save() throws IOException {
        var json = new JSONObject();
        for (var key : statistic.entrySet()) {
            json.put(key.getKey().toString(), key.getValue().toJSONObject());
        }
        saveJsonToFile(json);
        return this;
    }

    @Override
    public DarkWaterStatistic reload() throws IOException {
        load(getJsonFromFile());
        return this;
    }

    @Override
    public Statistic getPlayerStatistic(Player player) {
        if (!hasPlayer(player)) {
            putPlayer(player);
        }
        return statistic.get(player.getUniqueId());
    }

    @Override
    public Statistic getPlayerStatistic(UUID player) {
        if (!hasPlayer(player)) {
            putPlayer(player);
        }
        return statistic.get(player);
    }

    @Override
    public DarkWaterStatistic setPlayerStatistic(Player player, Statistic statistic) {
        var uuid = player.getUniqueId();
        this.statistic.remove(uuid);
        this.statistic.put(uuid, statistic);
        return this;
    }

    @Override
    public DarkWaterStatistic setPlayerStatistic(UUID player, Statistic statistic) {
        this.statistic.remove(player);
        this.statistic.put(player, statistic);
        return this;
    }

    @Override
    public DarkWaterStatistic addStatistic(Player player, EntityType type) {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type));
        return this;
    }

    @Override
    public DarkWaterStatistic addStatistic(UUID player, EntityType type) {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type));
        return this;
    }

    @Override
    public DarkWaterStatistic updatePlayer(Player player, JSONObject stats) {
        setPlayerStatistic(player, new Statistic(player, stats) {});
        return this;
    }

    @Override
    public DarkWaterStatistic updatePlayer(UUID player, JSONObject stats) {
        setPlayerStatistic(player, new Statistic(player, stats) {});
        return this;
    }

    @Override
    public HashMap<UUID, Statistic> getAllPlayerStatistic() {
        return statistic;
    }
}

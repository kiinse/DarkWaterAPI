package kiinse.plugins.api.darkwaterapi.files.statistic;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.filemanager.JsonFile;
import kiinse.plugins.api.darkwaterapi.files.filemanager.enums.File;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.DarkWaterStatistic;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("UnusedReturnValue")
public class DarkWaterStatsImpl extends JsonFile implements DarkWaterStatistic {

    private final HashMap<UUID, Statistic> statistic = new HashMap<>();

    public DarkWaterStatsImpl(@NotNull DarkWaterAPI darkWaterAPI) throws IOException {
        super(darkWaterAPI, File.STATISTIC_JSON);
        load(getJsonFromFile());
    }

    private @NotNull DarkWaterStatistic load(@NotNull JSONObject jsonObject) {
        for (var key : jsonObject.keySet()) {
            var uuid = UUID.fromString(key);
            statistic.put(uuid, new Statistic(uuid, jsonObject.getJSONObject(key)) {});
        }
        return this;
    }

    public boolean hasPlayer(@NotNull Player player) {
        return statistic.containsKey(player.getUniqueId());
    }

    public boolean hasPlayer(@NotNull UUID player) {
        return statistic.containsKey(player);
    }

    private @NotNull DarkWaterStatistic putPlayer(@NotNull Player player) {
        statistic.put(player.getUniqueId(), new Statistic(player, new JSONObject()) {});
        return this;
    }

    private @NotNull DarkWaterStatistic putPlayer(@NotNull UUID player) {
        statistic.put(player, new Statistic(player, new JSONObject()) {});
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic save() throws IOException {
        var json = new JSONObject();
        for (var key : statistic.entrySet()) {
            json.put(key.getKey().toString(), key.getValue().toJSONObject());
        }
        saveJsonToFile(json);
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic reload() throws IOException {
        load(getJsonFromFile());
        return this;
    }

    @Override
    public @NotNull Statistic getPlayerStatistic(@NotNull Player player) {
        if (!hasPlayer(player)) {
            putPlayer(player);
        }
        return statistic.get(player.getUniqueId());
    }

    @Override
    public @NotNull Statistic getPlayerStatistic(@NotNull UUID player) {
        if (!hasPlayer(player)) {
            putPlayer(player);
        }
        return statistic.get(player);
    }

    @Override
    public @NotNull DarkWaterStatistic setPlayerStatistic(@NotNull Player player, @NotNull Statistic statistic) {
        var uuid = player.getUniqueId();
        this.statistic.remove(uuid);
        this.statistic.put(uuid, statistic);
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic setPlayerStatistic(@NotNull UUID player, @NotNull Statistic statistic) {
        this.statistic.remove(player);
        this.statistic.put(player, statistic);
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic addStatistic(@NotNull Player player, @NotNull EntityType type) {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type));
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic addStatistic(@NotNull UUID player, @NotNull EntityType type) {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type));
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic updatePlayer(@NotNull Player player, @NotNull JSONObject stats) {
        setPlayerStatistic(player, new Statistic(player, stats) {});
        return this;
    }

    @Override
    public @NotNull DarkWaterStatistic updatePlayer(@NotNull UUID player, @NotNull JSONObject stats) {
        setPlayerStatistic(player, new Statistic(player, stats) {});
        return this;
    }

    @Override
    public @NotNull HashMap<UUID, Statistic> getAllPlayerStatistic() {
        return statistic;
    }
}

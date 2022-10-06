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

package kiinse.plugins.darkwaterapi.core.files.statistic;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.files.enums.File;
import kiinse.plugins.darkwaterapi.api.files.filemanager.JsonFile;
import kiinse.plugins.darkwaterapi.api.files.statistic.DarkStatistic;
import kiinse.plugins.darkwaterapi.api.files.statistic.StatisticManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("UnusedReturnValue")
public class DarkStatisticManager extends JsonFile implements StatisticManager {

    private final HashMap<UUID, DarkStatistic> statistic = new HashMap<>();

    public DarkStatisticManager(@NotNull DarkWaterJavaPlugin plugin) throws JsonFileException {
        super(plugin, File.STATISTIC_JSON);
        load(getJsonFromFile());
    }

    private @NotNull StatisticManager load(@NotNull JSONObject jsonObject) {
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

    private @NotNull StatisticManager putPlayer(@NotNull Player player) {
        statistic.put(player.getUniqueId(), new Statistic(player, new JSONObject()) {});
        return this;
    }

    private @NotNull StatisticManager putPlayer(@NotNull UUID player) {
        statistic.put(player, new Statistic(player, new JSONObject()) {});
        return this;
    }

    @Override
    public @NotNull StatisticManager save() throws JsonFileException {
        var json = new JSONObject();
        for (var key : statistic.entrySet()) {
            json.put(key.getKey().toString(), key.getValue().toJSONObject());
        }
        saveJsonToFile(json);
        return this;
    }

    @Override
    public @NotNull StatisticManager reload() throws JsonFileException {
        load(getJsonFromFile());
        return this;
    }

    @Override
    public @NotNull DarkStatistic getPlayerStatistic(@NotNull Player player) {
        if (!hasPlayer(player)) putPlayer(player);
        return statistic.get(player.getUniqueId());
    }

    @Override
    public @NotNull DarkStatistic getPlayerStatistic(@NotNull UUID player) {
        if (!hasPlayer(player)) putPlayer(player);
        return statistic.get(player);
    }

    @Override
    public @NotNull StatisticManager setPlayerStatistic(@NotNull Player player, @NotNull DarkStatistic statistic) {
        var uuid = player.getUniqueId();
        this.statistic.remove(uuid);
        this.statistic.put(uuid, statistic);
        return this;
    }

    @Override
    public @NotNull StatisticManager setPlayerStatistic(@NotNull UUID player, @NotNull DarkStatistic statistic) {
        this.statistic.remove(player);
        this.statistic.put(player, statistic);
        return this;
    }

    @Override
    public @NotNull StatisticManager addStatistic(@NotNull Player player, @NotNull EntityType type) {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type));
        return this;
    }

    @Override
    public @NotNull StatisticManager addStatistic(@NotNull UUID player, @NotNull EntityType type) {
        setPlayerStatistic(player, getPlayerStatistic(player).addStatistic(type));
        return this;
    }

    @Override
    public @NotNull StatisticManager updatePlayer(@NotNull Player player, @NotNull JSONObject stats) {
        setPlayerStatistic(player, new Statistic(player, stats) {});
        return this;
    }

    @Override
    public @NotNull StatisticManager updatePlayer(@NotNull UUID player, @NotNull JSONObject stats) {
        setPlayerStatistic(player, new Statistic(player, stats) {});
        return this;
    }

    @Override
    public @NotNull HashMap<UUID, DarkStatistic> getAllPlayerStatistic() {
        return statistic;
    }
}

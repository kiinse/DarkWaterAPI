package kiinse.plugins.api.darkwaterapi.files.statistic.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import kiinse.plugins.api.darkwaterapi.files.statistic.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface DarkWaterStatistic {

    @NotNull DarkWaterStatistic save() throws JsonFileException;

    @NotNull DarkWaterStatistic reload() throws JsonFileException;

    @NotNull Statistic getPlayerStatistic(@NotNull Player player);

    @NotNull Statistic getPlayerStatistic(@NotNull UUID player);

    @NotNull DarkWaterStatistic setPlayerStatistic(@NotNull Player player, @NotNull Statistic statistic);

    @NotNull DarkWaterStatistic setPlayerStatistic(@NotNull UUID player, @NotNull Statistic statistic);

    @NotNull DarkWaterStatistic addStatistic(@NotNull Player player, @NotNull EntityType type);

    @NotNull DarkWaterStatistic addStatistic(@NotNull UUID player, @NotNull EntityType type);

    @NotNull DarkWaterStatistic updatePlayer(@NotNull Player player, @NotNull JSONObject stats);

    @NotNull DarkWaterStatistic updatePlayer(@NotNull UUID player, @NotNull JSONObject stats);

    @NotNull HashMap<UUID, Statistic> getAllPlayerStatistic();
}

package kiinse.plugins.api.darkwaterapi.files.statistic.interfaces;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public interface DarkWaterStatistic {

    DarkWaterStatistic save() throws IOException;

    DarkWaterStatistic reload() throws IOException;

    Statistic getPlayerStatistic(Player player);

    Statistic getPlayerStatistic(UUID player);

    DarkWaterStatistic setPlayerStatistic(Player player, Statistic statistic);

    DarkWaterStatistic setPlayerStatistic(UUID player, Statistic statistic);

    DarkWaterStatistic addStatistic(Player player, EntityType type);

    DarkWaterStatistic addStatistic(UUID player, EntityType type);

    DarkWaterStatistic updatePlayer(Player player, JSONObject stats);

    DarkWaterStatistic updatePlayer(UUID player, JSONObject stats);

    HashMap<UUID, Statistic> getAllPlayerStatistic();
}

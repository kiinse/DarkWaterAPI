package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestUtils;
import org.bukkit.Server;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class DataAction implements Action {

    private final DarkWaterAPI darkWaterAPI;

    public DataAction(DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @Override
    public Object handler(Context context) {
        var server = darkWaterAPI.getServer();
        var json = new JSONObject();
        json.put("tps", server.getTPS());
        json.put("online", server.getOnlinePlayers().size());
        json.put("maxOnline", server.getMaxPlayers());
        json.put("playersOnline", server.getOnlinePlayers());
        json.put("worlds", getWorlds(server));
        json.put("isAllowEnd", server.getAllowEnd());
        json.put("isAllowNether", server.getAllowNether());
        json.put("isAllowFlight", server.getAllowFlight());
        json.put("viewDistance", server.getViewDistance());
        json.put("playersBanned", server.getBannedPlayers());
        json.put("tick", server.getCurrentTick());
        json.put("isWhitelist", server.hasWhitelist());
        json.put("playersWhitelist", server.getWhitelistedPlayers());
        json.put("gamemode", server.getDefaultGameMode());
        json.put("version", server.getVersion());
        json.put("ip", server.getIp() + ":" + server.getPort());
        json.put("name", server.getName());
        json.put("onlineMode", server.getOnlineMode());
        json.put("worldType", server.getWorldType());
        return RestUtils.createAnswer(RestStatus.SUCCESS, json);
    }

    private JSONObject getWorlds(Server server) {
        var map = new HashMap<String, JSONObject>();
        for (var world : server.getWorlds()) {
            var info = new JSONObject();
            var spawn = world.getSpawnLocation();
            info.put("time", world.getTime());
            info.put("fullTime", world.getFullTime());
            info.put("allowAnimals", world.getAllowAnimals());
            info.put("allowMonsters", world.getAllowMonsters());
            info.put("difficulty", world.getDifficulty());
            info.put("weatherDuration", world.getWeatherDuration());
            info.put("weatherClearDuration", world.getClearWeatherDuration());
            info.put("isThundering", world.isThundering());
            info.put("isRaining", !world.isThundering() && !world.isClearWeather());
            info.put("isClearWeather", world.isClearWeather());
            info.put("isDaytime", world.isDayTime());
            info.put("isFixedTime", world.isFixedTime());
            info.put("players", world.getPlayers());
            info.put("online", world.getPlayerCount());
            info.put("gameTime", world.getGameTime());
            info.put("isPvp", world.getPVP());
            info.put("seaLevel", world.getSeaLevel());
            info.put("viewDistance", world.getViewDistance());
            info.put("spawn", spawn.getBlockX() + ", " + spawn.getBlockY() + ", " + spawn.getBlockZ());
            info.put("moonPhase", world.getMoonPhase());
            info.put("seed", world.getSeed());
            map.put(world.getName(), info);
        }
        return new JSONObject(map);
    }
}

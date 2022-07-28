package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.rest.enums.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

import java.net.InetAddress;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;

public class PingAction implements Action {

    private final DarkWaterAPI darkWaterAPI;

    public PingAction(@NotNull DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @Override
    public @NotNull Object handler(@NotNull Context context) {
        try {
            var inet = InetAddress.getByName(context.params.get("ip", "localhost"));
            var start = new GregorianCalendar().getTimeInMillis();
            var map = new HashMap<String,String>();
            if (inet.isReachable(5000)){
                map.put("ping", String.valueOf(new GregorianCalendar().getTimeInMillis() - start));
                return RestUtils.createAnswer(RestStatus.SUCCESS, new JSONObject(map));
            }
            map.put("ping", "unreachable");
            return RestUtils.createAnswer(RestStatus.ERROR_PING_RECEIVE, new JSONObject(map));
        } catch (Exception e) {
            darkWaterAPI.sendLog(Level.WARNING, "An error occurred while receiving a ping: " + e.getMessage());
            return RestUtils.createAnswer(RestStatus.ERROR_PING_RECEIVE, e);
        }
    }

}

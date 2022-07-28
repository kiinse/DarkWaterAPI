package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.rest.enums.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestUtils;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class PluginsAction implements Action {

    private final Server server;

    public PluginsAction(@NotNull DarkWaterAPI darkWaterAPI) {
        this.server = darkWaterAPI.getServer();
    }

    @Override
    public @NotNull Object handler(@NotNull Context context) {
        var map = new HashMap<String, JSONObject>();
        for (var plugin : server.getPluginManager().getPlugins()) {
            var info = new JSONObject();
            var description = plugin.getDescription();
            info.put("isEnabled", plugin.isEnabled());
            info.put("version", description.getVersion());
            info.put("authors", description.getAuthors());
            info.put("website", description.getWebsite());
            info.put("apiVersion", description.getAPIVersion());
            info.put("depend", description.getDepend());
            info.put("softdepend", description.getSoftDepend());
            info.put("description", description.getDescription());
            map.put(plugin.getName(), info);
        }
        return RestUtils.createAnswer(RestStatus.SUCCESS, new JSONObject(map));
    }
}

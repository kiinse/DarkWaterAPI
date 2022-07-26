package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.utils.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestUtils;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

@SuppressWarnings("unchecked")
public class DarkWaterAction implements Action {

    private final DarkWaterAPI darkWaterAPI;
    private final YamlFile config;

    public DarkWaterAction(DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
        this.config = darkWaterAPI.getConfiguration();
    }

    @Override
    public Object handler(Context context) {
        if (config.getBoolean(Config.REST_AUTH_ENABLE)) {
            var json = new JSONObject();
            for (var plugin : darkWaterAPI.getPluginManager().getPluginsList()) {
                json.put(plugin.getName(), plugin.getPluginData());
            }
            return RestUtils.createAnswer(RestStatus.SUCCESS, json);
        }
        return RestUtils.createAnswer(RestStatus.ERROR_AUTHENTICATION);
    }
}

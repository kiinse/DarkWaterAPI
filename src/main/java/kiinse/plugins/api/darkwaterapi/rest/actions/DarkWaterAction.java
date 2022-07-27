package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.rest.enums.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestAnswer;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

@SuppressWarnings("unchecked")
public class DarkWaterAction implements Action {

    private final DarkWaterAPI darkWaterAPI;
    private final YamlFile config;

    public DarkWaterAction(@NotNull DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
        this.config = darkWaterAPI.getConfiguration();
    }

    @Override
    public @NotNull Object handler(@NotNull Context context) {
        if (config.getBoolean(Config.REST_AUTH_ENABLE)) {
            var json = new JSONObject();
            for (var plugin : darkWaterAPI.getPluginManager().getPluginsList()) {
                json.put(plugin.getName(), plugin.getPluginData());
            }
            return RestAnswer.createAnswer(RestStatus.SUCCESS, json);
        }
        return RestAnswer.createAnswer(RestStatus.ERROR_AUTHENTICATION);
    }
}

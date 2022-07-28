package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.rest.enums.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.ExecuteCMD;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestAnswer;
import kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces.RSADarkWater;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

import java.util.logging.Level;

@SuppressWarnings("unchecked")
public class CommandsAction implements Action {

    private final DarkWaterAPI darkWaterAPI;
    private final RSADarkWater rsa;
    private final YamlFile config;
    private final ExecuteCMD executor;

    public CommandsAction(@NotNull DarkWaterAPI darkWaterAPI, @NotNull RSADarkWater rsa) {
        this.darkWaterAPI = darkWaterAPI;
        this.rsa = rsa;
        this.executor = new ExecuteCMD(darkWaterAPI.getServer());
        this.config = darkWaterAPI.getConfiguration();
    }

    @Override
    public @NotNull Object handler(@NotNull Context context) throws Exception {

        if (!config.getBoolean(Config.REST_SERVICE_CODE)) {
            return RestAnswer.createAnswer(RestStatus.ERROR_SERVICE_DISABLED);
        }

        if (!config.getBoolean(Config.REST_AUTH_ENABLE)) {
            return RestAnswer.createAnswer(RestStatus.ERROR_AUTHENTICATION_DISABLED);
        }

        var cmd = context.params.get("cmd", "");
        if (cmd.isBlank()) {
            var json = new JSONObject();
            json.put("publicKey", new JSONObject(rsa.getPublicKeyJson().toMap()));
            return RestAnswer.createAnswer(RestStatus.SUCCESS, json);
        }

        try {
            var cmdFinal = config.getBoolean(Config.REST_ENCRYPTED_DATA) ? rsa.decryptMessage(cmd) : cmd;
            var isSuccess = Bukkit.getScheduler().callSyncMethod(darkWaterAPI, () -> Bukkit.dispatchCommand(executor, cmdFinal)).get();
            return RestAnswer.createAnswer(RestStatus.SUCCESS, String.valueOf(isSuccess));
        } catch (InterruptedException e) {
            darkWaterAPI.sendLog(Level.SEVERE, "Error on execute command from Rest! Message:\n" + e.getMessage());
            Thread.currentThread().interrupt();
            return RestAnswer.createAnswer(RestStatus.ERROR, e);
        }
    }
}

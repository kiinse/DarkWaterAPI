package kiinse.plugins.api.darkwaterapi.rest.actions;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.files.messages.SendMessagesImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Replace;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.SendMessages;
import kiinse.plugins.api.darkwaterapi.rest.enums.RestStatus;
import kiinse.plugins.api.darkwaterapi.rest.utils.RestAnswer;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces.RSADarkWater;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import services.moleculer.context.Context;
import services.moleculer.service.Action;

import java.util.UUID;
import java.util.logging.Level;

@SuppressWarnings("unchecked")
public class SendCodeAction implements Action {
    private final DarkWaterAPI darkWaterAPI;
    private final YamlFile config;
    private final RSADarkWater rsa;

    public SendCodeAction(@NotNull DarkWaterAPI darkWaterAPI, @NotNull RSADarkWater rsa) {
        this.darkWaterAPI = darkWaterAPI;
        this.rsa = rsa;
        this.config = darkWaterAPI.getConfiguration();
    }

    @Override
    public @NotNull Object handler(@NotNull Context context) {

        if (!config.getBoolean(Config.REST_SERVICE_CODE)) {
            return RestAnswer.createAnswer(RestStatus.ERROR_SERVICE_DISABLED);
        }

        if (!config.getBoolean(Config.REST_AUTH_ENABLE)) {
            return RestAnswer.createAnswer(RestStatus.ERROR_AUTHENTICATION_DISABLED);
        }

        var exponent = context.params.get("exponent", "");
        var modulus = context.params.get("modulus", "");
        var playerName = context.params.get("player", "");
        var uuid = context.params.get("uuid", "");
        if (!playerName.isBlank() || !uuid.isBlank()) {
            if (!exponent.isBlank() && !modulus.isBlank()) {
                try {
                    if (playerName.isBlank() && !uuid.isBlank()) {
                        var uuidFinal = config.getBoolean(Config.REST_ENCRYPTED_DATA) ? rsa.decryptMessage(uuid) : uuid;
                        return sendCodeToPlayer(darkWaterAPI.getServer().getPlayer(UUID.fromString(uuidFinal)), exponent, modulus);
                    } else {
                        var playerFinal = config.getBoolean(Config.REST_ENCRYPTED_DATA) ? rsa.decryptMessage(playerName) : playerName;
                        return sendCodeToPlayer(darkWaterAPI.getServer().getPlayer(playerFinal), exponent, modulus);
                    }
                } catch (Exception e) {
                    darkWaterAPI.sendLog(Level.SEVERE, "Error on sending code to player! Message:\n" + e.getMessage());
                    return RestAnswer.createAnswer(RestStatus.ERROR, e);
                }
            } else {
                return RestAnswer.createAnswer(RestStatus.ERROR_RSA_EMPTY);
            }
        }
        var json = new JSONObject();
        json.put("publicKey", new JSONObject(rsa.getPublicKeyJson().toMap()));
        return RestAnswer.createAnswer(RestStatus.SUCCESS, json);
    }

    private @NotNull JSONObject sendCodeToPlayer(@Nullable Player player, @NotNull String exponent, @NotNull String modulus) throws Exception {
        if (player != null && player.isOnline()) {
            SendMessages sendMessages = new SendMessagesImpl(darkWaterAPI);
            var randomCode = DarkWaterUtils.getRandomASCII(120);
            sendMessages.sendMessage(player, Message.GENERATED_CODE, Replace.CODE, randomCode);
            PlayerUtils.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            var json = new JSONObject();
            var pl = new JSONObject();
            pl.put("name", PlayerUtils.getPlayerName(player));
            pl.put("uuid", player.getUniqueId().toString());
            json.put("player", pl);
            json.put("code", rsa.encryptMessage(randomCode, rsa.recreatePublicKey(exponent, modulus)));
            return RestAnswer.createAnswer(RestStatus.SUCCESS, json);
        }
        return RestAnswer.createAnswer(RestStatus.NOT_FOUND);
    }
}

package kiinse.plugins.api.darkwaterapi.rest.services;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.rest.actions.*;
import kiinse.plugins.api.darkwaterapi.utilities.cryptography.RSAEncrypt;
import kiinse.plugins.api.darkwaterapi.utilities.cryptography.interfaces.RSADarkWater;
import org.jetbrains.annotations.NotNull;
import services.moleculer.service.Action;
import services.moleculer.service.Service;

@SuppressWarnings("unused")
public class DarkWaterService extends Service {

    public final Action ping;
    public final Action data;
    public final Action plugins;
    public final Action darkwater;
    public final Action execute;
    public final Action code;

    public DarkWaterService(@NotNull String name, @NotNull DarkWaterAPI darkWaterAPI) throws Exception {
        RSADarkWater rsa = new RSAEncrypt();
        this.name = name;
        ping = new PingAction(darkWaterAPI);
        data = new DataAction(darkWaterAPI);
        plugins = new PluginsAction(darkWaterAPI);
        darkwater = new DarkWaterAction(darkWaterAPI);
        execute = new CommandsAction(darkWaterAPI, rsa);
        code = new SendCodeAction(darkWaterAPI, rsa);

        darkWaterAPI.sendLog("See the config to find out the lines for accessing the rest");
        if (darkWaterAPI.getConfiguration().getBoolean(Config.REST_ENCRYPTED_DATA)) {
            darkWaterAPI.sendLog( "&6Encrypted data option enabled! &bTo transfer data to the plugin - you must get the public key of the plugin, encrypt the data and send the already encrypted data.");
        }
    }
}

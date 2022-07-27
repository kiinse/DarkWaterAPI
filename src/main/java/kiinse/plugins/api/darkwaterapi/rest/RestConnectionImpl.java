package kiinse.plugins.api.darkwaterapi.rest;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.rest.interfaces.RestConnection;
import kiinse.plugins.api.darkwaterapi.rest.services.DarkWaterService;
import kiinse.plugins.api.darkwaterapi.rest.enums.AuthTypes;
import kiinse.plugins.api.darkwaterapi.rest.utils.DarkWaterAuthProvider;
import org.jetbrains.annotations.NotNull;
import services.moleculer.ServiceBroker;
import services.moleculer.web.ApiGateway;
import services.moleculer.web.middleware.BasicAuthenticator;
import services.moleculer.web.middleware.ResponseTimeout;
import services.moleculer.web.netty.NettyServer;
import services.moleculer.web.router.Route;

public class RestConnectionImpl implements RestConnection {

    private final YamlFile config;
    private final DarkWaterAPI darkWaterAPI;
    private ServiceBroker broker;

    public RestConnectionImpl(@NotNull DarkWaterAPI darkWaterAPI) throws Exception {
        this.darkWaterAPI = darkWaterAPI;
        this.config = darkWaterAPI.getConfiguration();
        if (config.getBoolean(Config.REST_ENABLE)) {
            darkWaterAPI.sendLog("Starting Rest...");
            this.broker = new ServiceBroker();
            var gateway = new ApiGateway();
            var route = new Route();
            var name = config.getString(Config.REST_NAME);
            if (config.getBoolean(Config.REST_AUTH_ENABLE)) {
                if (AuthTypes.valueOf(config.getString(Config.REST_AUTH_TYPE).toUpperCase()) == AuthTypes.BEARER) {
                    route.use(new DarkWaterAuthProvider(darkWaterAPI));
                } else {
                    route.use(new BasicAuthenticator(config.getString(Config.REST_BASIC_LOGIN), config.getString(Config.REST_BASIC_PASSWORD)));
                }
            }
            route.use(new ResponseTimeout(1000L * 30));
            route.addToWhiteList("**");
            gateway.addRoute(route);
            broker
                    .createService(new NettyServer(config.getInt(Config.REST_PORT)))
                    .createService(new DarkWaterService(name, darkWaterAPI))
                    .createService(gateway)
                    .start();
            darkWaterAPI.sendLog("Rest started on port '" + config.getInt(Config.REST_PORT) + "' and name '" + config.getString(Config.REST_NAME) + "' ");
        }
    }

    @Override
    public boolean stop() {
        if (config.getBoolean(Config.REST_ENABLE) && broker != null) {
            darkWaterAPI.sendLog("Stopping Rest...");
            broker.stop();
            darkWaterAPI.sendLog("Rest stopped!");
            return true;
        }
        return false;
    }
}

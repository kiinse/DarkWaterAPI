package kiinse.plugins.api.darkwaterapi.rest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.datatree.Tree;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import services.moleculer.web.RequestProcessor;
import services.moleculer.web.WebRequest;
import services.moleculer.web.WebResponse;
import services.moleculer.web.common.HttpConstants;
import services.moleculer.web.middleware.AbstractRequestProcessor;
import services.moleculer.web.middleware.HttpMiddleware;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class DarkWaterAuthProvider extends HttpMiddleware implements HttpConstants {

    protected String realm = "DarkWaterAPI Web";
    private final YamlFile config;
    private final DarkWaterJavaPlugin plugin;
    private final HashMap<String, String> users;
    private final HashMap<String, String> tokens = new HashMap<>();

    public DarkWaterAuthProvider(@NotNull DarkWaterJavaPlugin plugin) {
        this.config = plugin.getConfiguration();
        this.plugin = plugin;
        this.users = getUsers();
    }

    private @NotNull HashMap<String, String> getUsers() {
        var map = new HashMap<String, String>();
        for (var value : config.getStringList(Config.REST_BEARER_USERS)) {
            var split = value.split(":");
            map.put(split[0], split[1]);
        }
        return map;
    }

    public @NotNull RequestProcessor install(final RequestProcessor next, Tree config) {
        return new AbstractRequestProcessor(next) {
            public void service(WebRequest req, WebResponse rsp) throws Exception {
                var authorization = req.getHeader("Authorization");
                if (authorization == null) {
                    var user = req.getHeader("user");
                    var password = req.getHeader("password");
                    if (user != null && password != null) {
                        var token = generateToken(user, password);
                        if (token != null) {
                            sendToken(rsp, token);
                        } else {
                            DarkWaterAuthProvider.this.sendUnauthorized(rsp);
                        }
                    } else {
                        DarkWaterAuthProvider.this.sendUnauthorized(rsp);
                    }
                } else {
                    try {
                        if (!DarkWaterAuthProvider.this.authenticate(authorization)) {
                            DarkWaterAuthProvider.this.sendUnauthorized(rsp);
                            var str = "Unknown REST connection attempt!\nAddress: &c" + req.getAddress() + "&6\nBody: &c" + req.getBody() + "&6\nProtocol: &c" + req.getProtocol() + "&6\nPath: &c" + req.getPath() + "&6\nQuery: &c" + req.getQuery().replace("&", "-") + "&6\nAuthorization header: &c" + authorization.replace("&", "-");
                            plugin.sendLog(Level.WARNING, str);
                            return;
                        }
                        rsp.setProperty("user", "DarkWaterAPI");
                    } catch (Exception e) {
                        plugin.sendLog(Level.SEVERE, "Error on REST connection! Message: " + e.getMessage());
                        DarkWaterAuthProvider.this.sendUnauthorized(rsp);
                        return;
                    }
                    next.service(req, rsp);
                }
            }
        };
    }

    protected void sendUnauthorized(@NotNull WebResponse rsp) {
        try {
            rsp.setStatus(401);
            rsp.setHeader("Status", "401");
        } finally {
            rsp.end();
        }
    }

    protected void sendToken(@NotNull WebResponse rsp, @NotNull String token) {
        try {
            rsp.setStatus(200);
            rsp.setHeader("Token_type", "bearer");
            rsp.setHeader("Expires_in", String.valueOf(JWT.decode(token).getExpiresAt().getTime()));
            rsp.setHeader("Access_token", token);
        } finally {
            rsp.end();
        }
    }

    protected @Nullable String generateToken(@NotNull String user, @NotNull String password) {
        if (tokens.containsKey(user) && !new Date().before(JWT.decode(tokens.get(user)).getExpiresAt())) {
            tokens.remove(user);
        }
        if (users.containsKey(user) && Objects.equals(users.get(user), password) && !tokens.containsKey(user)) {
            var token = JWT.create()
                    .withSubject(user)
                    .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(config.getInt(Config.REST_BEARER_EXPIRE))))
                    .sign(Algorithm.HMAC256(config.getString(Config.REST_BEARER_SECRET)));
            tokens.put(user, token);
            return token;
        }
        return null;
    }

    protected boolean isTokenRegistered(@NotNull String token) {
        for (var entry : tokens.entrySet()) {
            if (Objects.equals(entry.getValue(), token)) {
                return true;
            }
        }
        return false;
    }

    protected boolean authenticate(@NotNull String token) {
        var tkn = token.split(" ")[1];
        return isBearerAuth(token) && isTokenNotExpired(tkn) && isTokenRegistered(tkn);
    }

    protected boolean isBearerAuth(@Nullable String token) {
        return token != null && !token.isBlank() && token.startsWith("Bearer");
    }

    protected boolean isTokenNotExpired(@Nullable String token) {
        if (DarkWaterUtils.isStringEmpty(token)) {
            return false;
        }
        var tkn = JWT.decode(token);
        var subject = tkn.getSubject();
        if (tokens.containsKey(subject) && users.containsKey(subject)) {
            if (new Date().before(tkn.getExpiresAt())) {
                return true;
            } else {
                tokens.remove(subject);
            }
        }
        return false;
    }

}

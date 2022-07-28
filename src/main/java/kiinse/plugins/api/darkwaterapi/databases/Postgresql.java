package kiinse.plugins.api.darkwaterapi.databases;

import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

@SuppressWarnings("unused")
public abstract class Postgresql {

    private Connection connection;

    private DSLContext context;

    private final DarkWaterJavaPlugin plugin;

    protected Postgresql(@NotNull DarkWaterJavaPlugin plugin) throws SQLException {
        connect();
        this.plugin = plugin;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            try {
                plugin.sendLog("Connecting to database...");
                System.getProperties().setProperty("org.jooq.no-logo", "true");
                System.getProperties().setProperty("org.jooq.no-tips", "true");
                connection = registerConnection(getSettings(plugin));
                context = DSL.using(connection, SQLDialect.POSTGRES);
                createDataBases(context);
                plugin.sendLog("Database connected.");
            } catch (Exception e) {
                throw new SQLException(e);
            }
        } else {
            throw new SQLException("Database already connected!");
        }
    }

    public @NotNull SQLConnectionSettings getSettings(@NotNull DarkWaterJavaPlugin plugin) {
        var settings = new SQLConnectionSettings();
        var config = plugin.getConfiguration();
        settings.setHost(config.getString(Config.PG_HOST));
        settings.setPort(config.getString(Config.PG_PORT));
        settings.setDbName(config.getString(Config.PG_DBNAME));
        settings.setLogin(config.getString(Config.PG_LOGIN));
        settings.setPassword(config.getString(Config.PG_PASSWORD));
        settings.setURLDriver("postgresql");
        return settings;
    }

    public abstract @NotNull Connection registerConnection(@NotNull SQLConnectionSettings settings) throws Exception;

    public abstract void createDataBases(@NotNull DSLContext context);

    public @NotNull DSLContext getContext() {
        return this.context;
    }

    public @NotNull Connection getConnection() {return this.connection;}

    public @NotNull String getURL(@NotNull SQLConnectionSettings settings) {
        var url = "jdbc:" + settings.getUrlDriver() + "://" + settings.getHost() + ":" + settings.getPort() + "/";
        plugin.sendLog(Level.CONFIG, "Database connection url: &d" + url);
        return url;
    }

    public @NotNull Properties getProperties(@NotNull SQLConnectionSettings settings) {
        var connInfo = new Properties();
        connInfo.setProperty("user", settings.getLogin());
        connInfo.setProperty("password", settings.getPassword());
        connInfo.setProperty("useUnicode", "true");
        connInfo.setProperty("characterEncoding", "UTF-8");
        plugin.sendLog(Level.CONFIG, "Database user: &d" + settings.getLogin());
        plugin.sendLog(Level.CONFIG, "Database password: &d****" + settings.getLogin().substring(4));
        return connInfo;
    }

    public boolean isConnected() {
        return (connection != null);
    }

}

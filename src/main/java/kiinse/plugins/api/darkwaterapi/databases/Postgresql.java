package kiinse.plugins.api.darkwaterapi.databases;

import kiinse.plugins.api.darkwaterapi.databases.SQLConnectionSettings;
import kiinse.plugins.api.darkwaterapi.files.config.utils.Config;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
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

    protected Postgresql(DarkWaterJavaPlugin plugin) throws Exception {
        connect();
        this.plugin = plugin;
    }

    public void connect() throws Exception {
        if (!isConnected()) {
            plugin.sendLog("Connecting to database...");
            System.getProperties().setProperty("org.jooq.no-logo", "true");
            System.getProperties().setProperty("org.jooq.no-tips", "true");
            connection = registerConnection(getSettings(plugin));
            context = DSL.using(connection, SQLDialect.POSTGRES);
            createDataBases(context);
            plugin.sendLog("Database connected.");
        } else {
            throw new SQLException("Database already connected!");
        }
    }

    public SQLConnectionSettings getSettings(DarkWaterJavaPlugin plugin) {
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

    public abstract Connection registerConnection(SQLConnectionSettings settings) throws Exception;

    public abstract void createDataBases(DSLContext context);

    public DSLContext getContext() {
        return this.context;
    }

    public Connection getConnection() {return this.connection;}

    public String getURL(SQLConnectionSettings settings) {
        var url = "jdbc:" + settings.getUrlDriver() + "://" + settings.getHost() + ":" + settings.getPort() + "/";
        plugin.sendLog(Level.CONFIG, "Database connection url: &d" + url);
        return url;
    }

    public Properties getProperties(SQLConnectionSettings settings) {
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

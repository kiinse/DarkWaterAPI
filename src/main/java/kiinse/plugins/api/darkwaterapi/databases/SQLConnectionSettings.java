package kiinse.plugins.api.darkwaterapi.databases;

import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SQLConnectionSettings {

    private String host = "localhost";

    private String port = "5432";

    private String login = "postgres";

    private String password = "postgres";

    private String urlDriver = "postgresql";

    private String dbName = "darkwaterapi";

    public SQLConnectionSettings setHost(String sqlHost) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlHost)) {
            throw new IllegalArgumentException("SQL host is empty");
        }
        this.host = sqlHost;
        return this;
    }

    public SQLConnectionSettings setPort(String sqlPort) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlPort)) {
            throw new IllegalArgumentException("SQL port is empty");
        }
        this.port = sqlPort;
        return this;
    }

    public SQLConnectionSettings setLogin(String sqlLogin) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlLogin)) {
            throw new IllegalArgumentException("SQL login is empty");
        }
        this.login = sqlLogin;
        return this;
    }

    public SQLConnectionSettings setPassword(String sqlPassword) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlPassword)) {
            throw new IllegalArgumentException("SQL password is empty");
        }
        this.password = sqlPassword;
        return this;
    }

    public SQLConnectionSettings setDbName(String sqldbName) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqldbName)) {
            throw new IllegalArgumentException("SQL database name is empty");
        }
        this.dbName = sqldbName;
        return this;
    }

    public SQLConnectionSettings setURLDriver(String urlDriver) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(urlDriver)) {
            throw new IllegalArgumentException("SQL database url driver is empty");
        }
        this.urlDriver = urlDriver;
        return this;
    }

    public String getHost() {
        return this.host;
    }

    public String getPort() {
        return this.port;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUrlDriver() {
        return this.urlDriver;
    }

    public String getDbName() {
        return this.dbName;
    }
}

// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.api.darkwaterapi.databases;

import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SQLConnectionSettings {

    private String host = "localhost";

    private String port = "5432";

    private String login = "postgres";

    private String password = "postgres";

    private String urlDriver = "postgresql";

    private String dbName = "darkwaterapi";

    public @NotNull SQLConnectionSettings setHost(@NotNull String sqlHost) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlHost)) {
            throw new IllegalArgumentException("SQL host is empty");
        }
        this.host = sqlHost;
        return this;
    }

    public @NotNull SQLConnectionSettings setPort(@NotNull String sqlPort) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlPort)) {
            throw new IllegalArgumentException("SQL port is empty");
        }
        this.port = sqlPort;
        return this;
    }

    public @NotNull SQLConnectionSettings setLogin(@NotNull String sqlLogin) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlLogin)) {
            throw new IllegalArgumentException("SQL login is empty");
        }
        this.login = sqlLogin;
        return this;
    }

    public @NotNull SQLConnectionSettings setPassword(@NotNull String sqlPassword) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqlPassword)) {
            throw new IllegalArgumentException("SQL password is empty");
        }
        this.password = sqlPassword;
        return this;
    }

    public @NotNull SQLConnectionSettings setDbName(@NotNull String sqldbName) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(sqldbName)) {
            throw new IllegalArgumentException("SQL database name is empty");
        }
        this.dbName = sqldbName;
        return this;
    }

    public @NotNull SQLConnectionSettings setURLDriver(@NotNull String urlDriver) throws IllegalArgumentException {
        if (DarkWaterUtils.isStringEmpty(urlDriver)) {
            throw new IllegalArgumentException("SQL database url driver is empty");
        }
        this.urlDriver = urlDriver;
        return this;
    }

    public @NotNull String getHost() {
        return this.host;
    }

    public @NotNull String getPort() {
        return this.port;
    }

    public @NotNull String getLogin() {
        return this.login;
    }

    public @NotNull String getPassword() {
        return this.password;
    }

    public @NotNull String getUrlDriver() {
        return this.urlDriver;
    }

    public @NotNull String getDbName() {
        return this.dbName;
    }
}

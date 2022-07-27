package kiinse.plugins.api.darkwaterapi.databases;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class SQLCloseConnection {

    /**
     * Закрытие подключения к базе данных
     * @param connection Подключение к БД
     * @throws SQLException При ошибках во время закрытия подключения
     */
    public SQLCloseConnection(@Nullable Connection connection) throws SQLException {
        var plugin = DarkWaterAPI.getInstance();
        plugin.sendLog("Closing connection '&b" + connection + "&a'...");
        if (connection != null && !connection.isClosed()) {
            connection.close();
            plugin.sendLog("Connection closed.");
            return;
        }
        plugin.sendLog("&6Connection already closed!");
    }
}

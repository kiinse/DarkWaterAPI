package kiinse.plugins.api.darkwaterapi.files.messages.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("unused")
public interface Messages {

    /**
     * Перезагрузка файла с сообщениями
     */
    void reload() throws IOException;

    /**
     * Получение сообщения из файла messages.json
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Строка с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    @NotNull String getStringMessage(@NotNull Locale locale, @NotNull MessagesKeys message);

    /**
     * Получение сообщения из файла messages.json
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Компонент с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    @NotNull Component getComponentMessage(@NotNull Locale locale, @NotNull MessagesKeys message);

    /**
     * Получение сообщения из файла messages.json с префиксом перед ним
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Строка с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    @NotNull String getStringMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message);

    /**
     * Получение сообщения из файла messages.json с префиксом перед ним
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Компонент с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    @NotNull Component getComponentMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message);

    /**
     * Получения JSON объекта со всеми сообщениями
     * @param locale Язык сообщений
     * @return JSON объект со всеми сообщениями на указанном языке
     */
    @NotNull JSONObject getAllLocaleMessages(@NotNull Locale locale);

    @NotNull HashMap<String, JSONObject> getAllMessages();

    /**
     * Получение префикса из файла с сообщениями
     * @return Префикс
     */
    @NotNull String getPrefix(@NotNull Locale locale);

    /**
     * Метод форматирования цветов для отображения
     * @param message Входящая строка с необработанными цветами (Начинаются на &)
     * @return Строка с отформатированными цветами
     */
    @NotNull String colorize(@NotNull String message);
}

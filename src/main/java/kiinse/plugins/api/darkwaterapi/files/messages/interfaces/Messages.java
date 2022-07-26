package kiinse.plugins.api.darkwaterapi.files.messages.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import net.kyori.adventure.text.Component;
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
    String getStringMessage(Locale locale, MessagesKeys message);

    /**
     * Получение сообщения из файла messages.json
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Компонент с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    Component getComponentMessage(Locale locale, MessagesKeys message);

    /**
     * Получение сообщения из файла messages.json с префиксом перед ним
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Строка с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    String getStringMessageWithPrefix(Locale locale, MessagesKeys message);

    /**
     * Получение сообщения из файла messages.json с префиксом перед ним
     * @param locale Язык сообщения
     * @param message Сообщение
     * @return Компонент с сообщением на определённом языке. Если в сообщении были цвета (К примеру &6), то они уже отформатированы для отображения.
     */
    Component getComponentMessageWithPrefix(Locale locale, MessagesKeys message);

    /**
     * Получения JSON объекта со всеми сообщениями
     * @param locale Язык сообщений
     * @return JSON объект со всеми сообщениями на указанном языке
     */
    JSONObject getAllLocaleMessages(Locale locale);

    HashMap<String, JSONObject> getAllMessages();

    /**
     * Получение префикса из файла с сообщениями
     * @return Префикс
     */
    String getPrefix(Locale locale);

    /**
     * Метод форматирования цветов для отображения
     * @param message Входящая строка с необработанными цветами (Начинаются на &)
     * @return Строка с отформатированными цветами
     */
    String colorize(String message);
}

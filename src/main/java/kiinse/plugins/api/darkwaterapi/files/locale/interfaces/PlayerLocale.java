package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public interface PlayerLocale {

    /**
     * Проверка на то, есть ли у игрока локализация {@link Locale}
     * @param player Игрок
     * @return true если есть
     */
    boolean isPlayerLocalized(Player player);

    /**
     * Получение локализации игрока
     * @param player Игрок
     * @return Locale игрока. Если игрок не локализирован, то возвращает default Locale, указанный в locales.yml DarkWaterAPI {@link Locale}
     */
    Locale getPlayerLocale(Player player);

    /**
     * Получение локализации игрока
     * @param sender Игрок
     * @return Locale игрока. Если игрок не локализирован, то возвращает default Locale, указанный в locales.yml DarkWaterAPI {@link Locale}
     */
    Locale getPlayerLocale(CommandSender sender);

    /**
     * Установка локализации игрока
     * @param player Игрок
     * @param locale Locale {@link Locale}
     */
    void setPlayerLocale(Player player, Locale locale);

    /**
     * Получение языка интерфейса у игрока
     * @param player Игрок
     * @return Locale интерфейса {@link Locale}
     */
    Locale getPlayerInterfaceLocale(Player player);
}

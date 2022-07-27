package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface PlayerLocale {

    /**
     * Проверка на то, есть ли у игрока локализация {@link Locale}
     * @param player Игрок
     * @return true если есть
     */
    boolean isPlayerLocalized(@NotNull Player player);

    /**
     * Получение локализации игрока
     * @param player Игрок
     * @return Locale игрока. Если игрок не локализирован, то возвращает default Locale, указанный в locales.yml DarkWaterAPI {@link Locale}
     */
    @NotNull Locale getPlayerLocale(@NotNull Player player);

    /**
     * Получение локализации игрока
     * @param sender Игрок
     * @return Locale игрока. Если игрок не локализирован, то возвращает default Locale, указанный в locales.yml DarkWaterAPI {@link Locale}
     */
    @NotNull Locale getPlayerLocale(@NotNull CommandSender sender);

    /**
     * Установка локализации игрока
     * @param player Игрок
     * @param locale Locale {@link Locale}
     */
    void setPlayerLocale(@NotNull Player player, @NotNull Locale locale);

    /**
     * Получение языка интерфейса у игрока
     * @param player Игрок
     * @return Locale интерфейса {@link Locale}
     */
    @NotNull Locale getPlayerInterfaceLocale(@NotNull Player player);
}

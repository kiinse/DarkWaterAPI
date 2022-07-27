package kiinse.plugins.api.darkwaterapi.indicators.interfaces;

import kiinse.plugins.api.darkwaterapi.indicators.Indicator;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public interface IndicatorManager {

    /**
     * Регистрация индикатора в actionbar
     * @param plugin Плагин
     * @param indicator Индикатор {@link Indicator}
     */
    @NotNull IndicatorManager registerIndicator(@NotNull DarkWaterJavaPlugin plugin, @NotNull Indicator indicator);

    /**
     * Отправка списка индикаторов в консоль
     */
    void setIndicatorListToConsole();

    /**
     * Получение индикаторов для отображения
     * @return Строка для отправки в actionbar
     */
    @NotNull String getIndicators();

    /**
     * Получение индикаторов
     * @return Список индикаторов
     */
    @NotNull List<Indicator> getIndicatorsList();

    /**
     * Получение самой дальней позиции из всех индикаторов
     * @return Номер позиции
     */
    int getMaxPosition();

    /**
     * Удаление индикатора
     * @param indicator Индикатор {@link Indicator}
     * @return True если индикатор был удалён
     */
    boolean removeIndicator(@NotNull Indicator indicator);

    /**
     * Проверка на существование зарегистрированного индикатора
     * @param indicator Индикатор {@link Indicator}
     * @return True если уже зарегистрирован
     */
    boolean hasIndicator(@NotNull Indicator indicator);

    /**
     * Проверка на позицию индикатора
     * @param indicator Индикатор {@link Indicator}
     * @return True если индикатор с такой позицией был зарегистрирован
     */
    boolean hasPosition(@NotNull Indicator indicator);

    /**
     * Получение индикатора по позиции
     * @param position Позиция
     * @return Индикатор {@link Indicator}
     */
    @Nullable Indicator getIndicatorByPosition(int position);
}

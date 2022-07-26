package kiinse.plugins.api.darkwaterapi.indicators.interfaces;

import kiinse.plugins.api.darkwaterapi.indicators.Indicator;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;

import java.util.List;

@SuppressWarnings("unused")
public interface IndicatorManager {

    /**
     * Регистрация индикатора в actionbar
     * @param plugin Плагин
     * @param indicator Индикатор {@link Indicator}
     */
    void registerIndicator(DarkWaterJavaPlugin plugin, Indicator indicator);

    /**
     * Отправка списка индикаторов в консоль
     */
    void indicatorsList();

    /**
     * Получение индикаторов для отображения
     * @return Строка для отправки в actionbar
     */
    String getIndicators();

    /**
     * Получение индикаторов
     * @return Список индикаторов
     */
    List<Indicator> getIndicatorsList();

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
    boolean removeIndicator(Indicator indicator);

    /**
     * Проверка на существование зарегистрированного индикатора
     * @param indicator Индикатор {@link Indicator}
     * @return True если уже зарегистрирован
     */
    boolean hasIndicator(Indicator indicator);

    /**
     * Проверка на позицию индикатора
     * @param indicator Индикатор {@link Indicator}
     * @return True если индикатор с такой позицией был зарегистрирован
     */
    boolean hasPosition(Indicator indicator);

    /**
     * Получение индикатора по позиции
     * @param position Позиция
     * @return Индикатор {@link Indicator}
     */
    Indicator getIndicatorByPosition(int position);
}

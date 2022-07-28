package kiinse.plugins.api.darkwaterapi.indicators.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.IndicatorException;
import kiinse.plugins.api.darkwaterapi.indicators.Indicator;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public interface IndicatorManager {

    @NotNull IndicatorManager registerIndicator(@NotNull DarkWaterJavaPlugin plugin, @NotNull Indicator indicator) throws IndicatorException;

    void setIndicatorListToConsole();

    @NotNull String getIndicators();

    @NotNull List<Indicator> getIndicatorsList();

    int getMaxPosition();

    boolean removeIndicator(@NotNull Indicator indicator);

    boolean hasIndicator(@NotNull Indicator indicator);

    boolean hasPosition(@NotNull Indicator indicator);

    @Nullable Indicator getIndicatorByPosition(int position);
}

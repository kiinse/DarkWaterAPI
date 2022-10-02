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

package kiinse.plugins.darkwaterapi.core.indicators;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.exceptions.IndicatorException;
import kiinse.plugins.darkwaterapi.api.indicators.Indicator;
import kiinse.plugins.darkwaterapi.api.indicators.IndicatorManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

public class DarkIndicatorManager implements IndicatorManager {

    private final DarkWaterJavaPlugin mainPlugin;

    private final HashMap<String, Indicator> indicators = new HashMap<>();

    public DarkIndicatorManager(@NotNull DarkWaterJavaPlugin mainPlugin) {
        this.mainPlugin = mainPlugin;
    }

    @Override
    public @NotNull IndicatorManager register(@NotNull DarkWaterJavaPlugin plugin, @NotNull Indicator indicator) throws IndicatorException {
        if (hasIndicator(indicator)) {
            throw new IndicatorException("Indicator '" + indicator.getName() + "' already registered by '" + indicators.get(indicator.getName()).getPlugin().getName() + "'");
        }
        if (!indicator.getName().startsWith("%") || !indicator.getName().endsWith("%")) {
            throw new IndicatorException("The '" + indicator.getName() + "' indicator must be a placeholder, i.e. start and end with % ");
        }
        if (hasPosition(indicator)) {
            var pos = getMaxPosition() + 1;
            plugin.sendLog(Level.WARNING,
                           "Indicator position &c" + indicator.getPosition() + " is already used by '&b" + Objects.requireNonNull(
                                   getIndicatorByPosition(indicator.getPosition())).getPlugin().getName() + "&6'\nUsing last position: &b" + pos);
            registerIndicator(plugin, Indicator.valueOf(indicator.getPlugin(), indicator.getName(), pos));
            setIndicatorListToConsole();
            return this;
        }
        registerIndicator(plugin, indicator);
        setIndicatorListToConsole();
        return this;
    }

    @Override
    public void setIndicatorListToConsole() {
        mainPlugin.sendLog(Level.CONFIG, "Indicators list: &d");
        var j = 0;
        for (int i = 0; i <= getMaxPosition(); i++) {
            var indicator = getIndicatorByPosition(i);
            if (indicator != null) {
                mainPlugin.sendLog(Level.CONFIG,
                                   "Position: '&d" + j + "&6' (Registered &d" + i + "&6) | Indicator: '&d" + indicator.getName() + "&6' | Plugin: '&d" + indicator.getPlugin() + "&6'");
                j++;
            }
        }
    }

    @Override
    public @NotNull String getIndicators() {
        var result = new StringBuilder();
        for (int i = 0; i <= getMaxPosition(); i++) {
            var indicator = getIndicatorByPosition(i);
            if (indicator != null) {
                result.append(indicator.getName());
            }
        }
        return result.toString();
    }

    @Override
    public @NotNull Set<Indicator> getIndicatorsSet() {
        var set = new HashSet<Indicator>();
        for (var indicator : indicators.entrySet()) {
            set.add(indicator.getValue());
        }
        return set;
    }

    private void registerIndicator(@NotNull DarkWaterJavaPlugin plugin, @NotNull Indicator indicator) {
        indicators.put(indicator.getName(), indicator);
        plugin.sendLog("Registered indicator '&b" + indicator.getName() + "&a' by '&b" + plugin.getName() + "&6' on position &b" + indicator.getPosition());
    }

    @Override
    public int getMaxPosition() {
        var position = 0;
        for (var key : indicators.entrySet()) {
            var indicatorPos = key.getValue().getPosition();
            if (indicatorPos > position) {
                position = indicatorPos;
            }
        }
        return position;
    }

    @Override
    public boolean removeIndicator(@NotNull Indicator indicator) {
        mainPlugin.sendLog("Removing indicator '&b" + indicator.getName() + "&6'...");
        for (var entry : indicators.entrySet()) {
            if (entry.getValue().equals(indicator)) {
                indicators.remove(entry.getKey());
                mainPlugin.sendLog("Indicator '&b" + indicator.getName() + "&a' on position &b" + indicator.getPosition() + "&a has been removed!");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasIndicator(@NotNull Indicator indicator) {
        for (var entry : indicators.entrySet()) {
            if (entry.getValue().equals(indicator)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPosition(@NotNull Indicator indicator) {
        for (var entry : indicators.entrySet()) {
            if (Objects.equals(entry.getValue().getPosition(), indicator.getPosition())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable Indicator getIndicatorByPosition(int position) {
        for (var entry : indicators.entrySet()) {
            var indicator = entry.getValue();
            if (Objects.equals(indicator.getPosition(), position)) {
                return indicator;
            }
        }
        return null;
    }
}

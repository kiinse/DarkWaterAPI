package kiinse.plugins.api.darkwaterapi.indicators;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.indicators.interfaces.IndicatorManager;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class IndicatorManagerImpl implements IndicatorManager {

    private final DarkWaterAPI darkWaterAPI;

    public IndicatorManagerImpl(DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    private final HashMap<String, Indicator> indicators = new HashMap<>();

    @Override
    public void registerIndicator(DarkWaterJavaPlugin plugin, Indicator indicator) {
        if (hasIndicator(indicator)) {
            throw new IllegalArgumentException("Indicator '" + indicator.getName() + "' already registered by '" + indicators.get(indicator.getName()).getPlugin().getName() + "'");
        }
        if (!indicator.getName().startsWith("%") || !indicator.getName().endsWith("%")) {
            throw new IllegalArgumentException("The '" + indicator.getName() + "' indicator must be a placeholder, i.e. start and end with % ");
        }
        if (hasPosition(indicator)) {
            var pos = getMaxPosition() + 1;
            plugin.sendLog(Level.WARNING, "Indicator position &c" + indicator.getPosition() + " is already used by '&b" + getIndicatorByPosition(indicator.getPosition()).getPlugin().getName() + "&6'\nUsing last position: &b" + pos);
            register(plugin, Indicator.valueOf(indicator.getPlugin(), indicator.getName(), pos));
            indicatorsList();
            return;
        }
        register(plugin, indicator);
        indicatorsList();
    }

    @Override
    public void indicatorsList() {
        darkWaterAPI.sendLog(Level.CONFIG, "Indicators list: &d");
        var j = 0;
        for (int i = 0; i <= getMaxPosition(); i++) {
            var indicator = getIndicatorByPosition(i);
            if (indicator != null) {
                darkWaterAPI.sendLog(Level.CONFIG, "Position: '&d" + j + "&6' (Registered &d" + i + "&6) | Indicator: '&d" + indicator.getName() + "&6' | Plugin: '&d" + indicator.getPlugin() + "&6'");
                j++;
            }
        }
    }

    @Override
    public String getIndicators() {
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
    public List<Indicator> getIndicatorsList() {
        var list = new ArrayList<Indicator>();
        for (var indicator : indicators.entrySet()) {
            list.add(indicator.getValue());
        }
        return list;
    }

    private void register(DarkWaterJavaPlugin plugin, Indicator indicator) {
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
    public boolean removeIndicator(Indicator indicator) {
        darkWaterAPI.sendLog("Removing indicator '&b" + indicator.getName() + "&6'...");
        for (var entry : indicators.entrySet()) {
            if (entry.getValue().equals(indicator)) {
                indicators.remove(entry.getKey());
                darkWaterAPI.sendLog("Indicator '&b" + indicator.getName() + "&a' on position &b" + indicator.getPosition() + "&a has been removed!");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasIndicator(Indicator indicator) {
        for (var entry : indicators.entrySet()) {
            if (entry.getValue().equals(indicator)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPosition(Indicator indicator) {
        for (var entry : indicators.entrySet()) {
            if (Objects.equals(entry.getValue().getPosition(), indicator.getPosition())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Indicator getIndicatorByPosition(int position) {
        for (var entry : indicators.entrySet()) {
            var indicator = entry.getValue();
            if (Objects.equals(indicator.getPosition(), position)) {
                return indicator;
            }
        }
        return null;
    }
}

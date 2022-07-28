package kiinse.plugins.api.darkwaterapi.indicators;

import kiinse.plugins.api.darkwaterapi.exceptions.IndicatorException;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Indicator {

    private final int position;
    private final String name;
    private final Plugin plugin;

    protected Indicator(@NotNull Plugin plugin, @NotNull String name, int position) {
        this.position = position;
        this.name = name;
        this.plugin = plugin;
    }

    public int getPosition() {
        return position;
    }

    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    public @NotNull String getName() {
        return name;
    }

    public static @NotNull Indicator valueOf(@NotNull Plugin plugin, @NotNull String name, int position) throws IndicatorException {
        if (DarkWaterUtils.isStringEmpty(name)) {
            throw new IndicatorException("Indicator name is empty!");
        }
        if (!name.startsWith("%") || !name.endsWith("%")) {
            throw new IndicatorException("Invalid indicator format! Please, user placeholder %indicator%!");
        }
        if (position < 0) {
            throw new IndicatorException("Position can't be < 0!");
        }
        return new Indicator(plugin, name, position) {};
    }

    public boolean equals(@NotNull Indicator indicator) {
        return Objects.equals(this.getName(), indicator.getName()) && this.getPosition() == indicator.getPosition();
    }

    public static boolean equals(@NotNull Indicator indicator1, @NotNull Indicator indicator2) {
        return Objects.equals(indicator1.getName(), indicator2.getName()) && indicator1.getPosition() == indicator2.getPosition();
    }
}

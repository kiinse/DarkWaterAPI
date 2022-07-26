package kiinse.plugins.api.darkwaterapi.indicators;

import org.bukkit.plugin.Plugin;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Indicator {

    private final int position;
    private final String name;
    private final Plugin plugin;

    protected Indicator(Plugin plugin, String name, int position) {
        this.position = position;
        this.name = name;
        this.plugin = plugin;
    }

    public int getPosition() {
        return position;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    /**
     * Получение нового индикатора
     * @param plugin Плагин
     * @param name Индикатор (Placeholder)
     * @param position Позиция
     * @return Индикатор {@link Indicator}
     * @throws IllegalArgumentException Если плагин или name null
     * @throws IllegalArgumentException Если в name пусто
     * @throws IllegalArgumentException Если name не начинается или не заканчивается на %
     * @throws IllegalArgumentException Если позиция меньше нуля
     */
    public static Indicator valueOf(Plugin plugin, String name, int position) throws IllegalArgumentException {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin is null!");
        }
        if (name == null) {
            throw new IllegalArgumentException("Indicator name is null!");
        }
        if (name.replace(" ", "").isEmpty()) {
            throw new IllegalArgumentException("Indicator name is empty!");
        }
        if (!name.startsWith("%") || !name.endsWith("%")) {
            throw new IllegalArgumentException("Invalid indicator format! Please, user placeholder %indicator%!");
        }
        if (position < 0) {
            throw new IllegalArgumentException("Position can't be < 0!");
        }
        return new Indicator(plugin, name, position) {};
    }

    public boolean equals(Indicator indicator) {
        return Objects.equals(this.getName(), indicator.getName()) && this.getPosition() == indicator.getPosition();
    }

    public static boolean equals(Indicator indicator1, Indicator indicator2) {
        return Objects.equals(indicator1.getName(), indicator2.getName()) && indicator1.getPosition() == indicator2.getPosition();
    }
}

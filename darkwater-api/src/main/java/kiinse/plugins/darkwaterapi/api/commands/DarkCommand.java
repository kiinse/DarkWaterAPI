package kiinse.plugins.darkwaterapi.api.commands;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class DarkCommand {

    private final DarkWaterJavaPlugin plugin;

    protected DarkCommand(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull DarkWaterJavaPlugin getPlugin() {
        return plugin;
    }
}

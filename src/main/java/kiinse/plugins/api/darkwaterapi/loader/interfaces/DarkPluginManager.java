package kiinse.plugins.api.darkwaterapi.loader.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.PluginException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public interface DarkPluginManager {

    boolean hasPlugin(@NotNull DarkWaterJavaPlugin plugin);

    boolean hasPlugin(@NotNull String plugin);

    @NotNull List<DarkWaterJavaPlugin> getPluginsList();

    void registerPlugin(@NotNull DarkWaterJavaPlugin plugin);

    void unregisterPlugin(@NotNull DarkWaterJavaPlugin plugin) throws PluginException;

    void enablePlugin(@NotNull String plugin) throws PluginException;

    void disablePlugin(@NotNull String plugin) throws PluginException;

    void reloadPlugin(@NotNull String plugin) throws PluginException;

}

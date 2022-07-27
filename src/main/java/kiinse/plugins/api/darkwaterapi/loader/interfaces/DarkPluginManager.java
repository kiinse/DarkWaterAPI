package kiinse.plugins.api.darkwaterapi.loader.interfaces;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public interface DarkPluginManager {

    boolean hasPlugin(@NotNull DarkWaterJavaPlugin plugin);

    boolean hasPlugin(@NotNull String plugin);

    @NotNull List<DarkWaterJavaPlugin> getPluginsList();

    void registerPlugin(@NotNull DarkWaterJavaPlugin plugin) throws IllegalArgumentException;

    void unregisterPlugin(@NotNull DarkWaterJavaPlugin plugin) throws Exception;

    void enablePlugin(@NotNull String plugin) throws IllegalArgumentException;

    void disablePlugin(@NotNull String plugin) throws IllegalArgumentException;

    void reloadPlugin(@NotNull String plugin);

}

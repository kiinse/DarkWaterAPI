package kiinse.plugins.api.darkwaterapi.loader.interfaces;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;

import java.util.List;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public interface DarkPluginManager {

    boolean hasPlugin(DarkWaterJavaPlugin plugin);

    boolean hasPlugin(String plugin);

    List<DarkWaterJavaPlugin> getPluginsList();

    void registerPlugin(DarkWaterJavaPlugin plugin) throws IllegalArgumentException;

    void unregisterPlugin(DarkWaterJavaPlugin plugin) throws Exception;

    void enablePlugin(String plugin) throws IllegalArgumentException;

    void disablePlugin(String plugin) throws IllegalArgumentException;

    void reloadPlugin(String plugin);

}

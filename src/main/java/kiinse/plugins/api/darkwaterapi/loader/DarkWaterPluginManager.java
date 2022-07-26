package kiinse.plugins.api.darkwaterapi.loader;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkPluginManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DarkWaterPluginManager implements DarkPluginManager {

    private final List<DarkWaterJavaPlugin> plugins = new ArrayList<>();
    private final DarkWaterAPI darkWaterAPI;

    public DarkWaterPluginManager(DarkWaterAPI darkWaterAPI) {
        this.darkWaterAPI = darkWaterAPI;
    }

    @Override
    public boolean hasPlugin(DarkWaterJavaPlugin plugin) {
        return hasPlugin(plugin.getName());
    }

    @Override
    public boolean hasPlugin(String plugin) {
        for (var plug : plugins) {
            if (plug.getName().equals(plugin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<DarkWaterJavaPlugin> getPluginsList() {
        return new ArrayList<>(plugins);
    }

    private DarkWaterJavaPlugin getPlugin(String plugin) {
        for (var plug : plugins) {
            if (plug.getName().equals(plugin)) {
                return plug;
            }
        }
        return null;
    }

    private void remove(DarkWaterJavaPlugin plugin) throws Exception {
        remove(plugin.getName());
    }

    private void remove(String plugin) throws Exception {
        var iterator = plugins.listIterator();
        while (iterator.hasNext()) {
            var plug = iterator.next();
            if (Objects.equals(plug.getName(), plugin)) {
                plug.onStop();
                iterator.remove();
            }
        }
    }

    @Override
    public void registerPlugin(DarkWaterJavaPlugin plugin) throws IllegalArgumentException {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin is null!");
        }
        if (!hasPlugin(plugin)) {
            plugins.add(plugin);
            this.darkWaterAPI.sendLog("Plugin '&b" + plugin.getName() + "&a' registered");
        } else {
            this.darkWaterAPI.sendLog("Plugin '&b" + plugin.getName() + "&a' already registered! Skipping register...");
        }
    }

    @Override
    public void unregisterPlugin(DarkWaterJavaPlugin plugin) throws Exception {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin is null!");
        }
        if (!hasPlugin(plugin)) {
            throw new IllegalArgumentException("This plugin '" + plugin.getName() + "' not registered!");
        }
        remove(plugin);
        this.darkWaterAPI.sendLog("Plugin '&b" + plugin.getName() + "&a' unregistered");
    }

    @Override
    public void enablePlugin(String plugin) throws IllegalArgumentException {
        var plug = getPlugin(plugin);
        if (!hasPlugin(plugin)|| plug == null) {
            throw new IllegalArgumentException("This plugin '" + plugin + "' not found!");
        }
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            throw new IllegalArgumentException("This plugin '" + plugin + "' already enabled!");
        }
        Bukkit.getPluginManager().enablePlugin(plug);
        this.darkWaterAPI.sendLog("Plugin '&b" + plug.getName() + "&a' started");
    }

    @Override
    public void disablePlugin(String plugin) throws IllegalArgumentException {
        var plug = getPlugin(plugin);
        if (!hasPlugin(plugin) || plug == null) {
            throw new IllegalArgumentException("This plugin '" + plugin + "' not found!");
        }
        if (!Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            throw new IllegalArgumentException("This plugin '" + plugin + "' already disabled!");
        }
        Bukkit.getPluginManager().disablePlugin(plug);
        this.darkWaterAPI.sendLog("Plugin '&b" + plug.getName() + "&a' stopped");
    }

    @Override
    public void reloadPlugin(String plugin) {
        var plug = getPlugin(plugin);
        if (!hasPlugin(plugin) || plug == null) {
            throw new IllegalArgumentException("This plugin '" + plugin + "' not found!");
        }
        plug.restart();
        this.darkWaterAPI.sendLog("Plugin '&b" + plug.getName() + "&a' restarted");
    }
}

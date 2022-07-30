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

package kiinse.plugins.darkwaterapi.core.loader;

import kiinse.plugins.darkwaterapi.api.exceptions.exceptions.PluginException;
import kiinse.plugins.darkwaterapi.api.loader.DarkPluginManager;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DarkWaterPluginManager implements DarkPluginManager {

    private final Set<DarkWaterJavaPlugin> plugins = new HashSet<>();
    private final DarkWaterJavaPlugin mainPlugin;

    public DarkWaterPluginManager(@NotNull DarkWaterJavaPlugin mainPlugin) {
        this.mainPlugin = mainPlugin;
    }

    @Override
    public boolean hasPlugin(@NotNull DarkWaterJavaPlugin plugin) {
        return hasPlugin(plugin.getName());
    }

    @Override
    public boolean hasPlugin(@NotNull String plugin) {
        for (var plug : plugins) {
            if (plug.getName().equals(plugin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull Set<DarkWaterJavaPlugin> getPluginsList() {
        return new HashSet<>(plugins);
    }

    private @Nullable DarkWaterJavaPlugin getPlugin(@NotNull String plugin) {
        for (var plug : plugins) {
            if (plug.getName().equals(plugin)) {
                return plug;
            }
        }
        return null;
    }

    private void remove(@NotNull DarkWaterJavaPlugin plugin) throws PluginException {
        remove(plugin.getName());
    }

    private void remove(@NotNull String plugin) throws PluginException {
        var iterator = plugins.iterator();
        while (iterator.hasNext()) {
            var plug = iterator.next();
            if (Objects.equals(plug.getName(), plugin)) {
                try {
                    plug.onStop();
                    iterator.remove();
                } catch (Exception e) {
                    throw new PluginException(e);
                }
            }
        }
    }

    @Override
    public void registerPlugin(@NotNull DarkWaterJavaPlugin plugin) {
        if (!hasPlugin(plugin)) {
            plugins.add(plugin);
            this.mainPlugin.sendLog("Plugin '&b" + plugin.getName() + "&a' registered");
        } else {
            this.mainPlugin.sendLog("Plugin '&b" + plugin.getName() + "&a' already registered! Skipping register...");
        }
    }

    @Override
    public void unregisterPlugin(@NotNull DarkWaterJavaPlugin plugin) throws PluginException {
        if (!hasPlugin(plugin)) {
            throw new PluginException("This plugin '" + plugin.getName() + "' not registered!");
        }
        remove(plugin);
        this.mainPlugin.sendLog("Plugin '&b" + plugin.getName() + "&a' unregistered");
    }

    @Override
    public void enablePlugin(@NotNull String plugin) throws PluginException {
        var plug = getPlugin(plugin);
        if (!hasPlugin(plugin)|| plug == null) {
            throw new PluginException("This plugin '" + plugin + "' not found!");
        }
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            throw new PluginException("This plugin '" + plugin + "' already enabled!");
        }
        Bukkit.getPluginManager().enablePlugin(plug);
        this.mainPlugin.sendLog("Plugin '&b" + plug.getName() + "&a' started");
    }

    @Override
    public void disablePlugin(@NotNull String plugin) throws PluginException {
        var plug = getPlugin(plugin);
        if (!hasPlugin(plugin) || plug == null) {
            throw new PluginException("This plugin '" + plugin + "' not found!");
        }
        if (!Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            throw new PluginException("This plugin '" + plugin + "' already disabled!");
        }
        Bukkit.getPluginManager().disablePlugin(plug);
        this.mainPlugin.sendLog("Plugin '&b" + plug.getName() + "&a' stopped");
    }

    @Override
    public void reloadPlugin(@NotNull String plugin) throws PluginException {
        var plug = getPlugin(plugin);
        if (!hasPlugin(plugin) || plug == null) {
            throw new PluginException("This plugin '" + plugin + "' not found!");
        }
        plug.restart();
        this.mainPlugin.sendLog("Plugin '&b" + plug.getName() + "&a' restarted");
    }
}

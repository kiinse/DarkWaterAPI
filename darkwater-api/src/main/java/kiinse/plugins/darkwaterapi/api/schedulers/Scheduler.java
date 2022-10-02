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

package kiinse.plugins.darkwaterapi.api.schedulers;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

@SuppressWarnings("UnusedReturnValue")
public abstract class Scheduler {

    protected final @NotNull DarkWaterJavaPlugin plugin;
    private String name = null;
    private long delay = -1;
    private long period = -1;
    private int schedulerID;

    protected Scheduler(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean canStart() {
        return true;
    }

    public void start() {
        if (canStart()) {
            schedulerID = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::run, delay, period);
            plugin.sendLog("Scheduler '&b" + name + "&a' started!");
        } else {
            plugin.sendLog(Level.CONFIG, "Scheduler '&d" + name + "&6' cannot be started because the '&dcanStart()&6' method returns &cfalse");
        }
    }

    public void stop() {
        if (isStarted()) {
            plugin.getServer().getScheduler().cancelTask(schedulerID);
            plugin.sendLog("Scheduler '&b" + name + "&a' stopped!");
        }
    }

    public abstract void run();

    public boolean isStarted() {
        return plugin.getServer().getScheduler().isCurrentlyRunning(schedulerID);
    }

    public @NotNull DarkWaterJavaPlugin getPlugin() {
        return this.plugin;
    }

    public @Nullable String getName() {
        return name;
    }

    public @NotNull Scheduler setName(@Nullable String name) {
        if (name == null || name.isBlank()) {
            this.name = RandomStringUtils.randomAscii(60).replace(">", "");
        } else {
            this.name = name;
        }
        return this;
    }

    public long getDelay() {
        return delay;
    }

    public @NotNull Scheduler setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public @NotNull Scheduler setPeriod(long period) {
        this.period = period;
        return this;
    }
}

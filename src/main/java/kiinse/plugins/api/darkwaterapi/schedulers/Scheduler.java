package kiinse.plugins.api.darkwaterapi.schedulers;

import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

@SuppressWarnings("UnusedReturnValue")
public abstract class Scheduler {

    private String name = null;
    private long delay = -1;
    private long period = -1;
    protected final @NotNull DarkWaterJavaPlugin plugin;
    private int schedulerID;

    protected Scheduler(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull Scheduler setName(@Nullable String name) {
        if (DarkWaterUtils.isStringEmpty(name)) {
            this.name = DarkWaterUtils.getRandomASCII(60);
        } else {
            this.name = name;
        }
        return this;
    }

    public @NotNull Scheduler setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public @NotNull Scheduler setPeriod(long period) {
        this.period = period;
        return this;
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

    public long getDelay() {
        return delay;
    }

    public long getPeriod() {
        return period;
    }
}

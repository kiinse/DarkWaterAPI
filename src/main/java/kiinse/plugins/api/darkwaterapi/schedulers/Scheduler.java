package kiinse.plugins.api.darkwaterapi.schedulers;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

@SuppressWarnings("UnusedReturnValue")
public abstract class Scheduler {

    private String name = null;
    private long delay = -1;
    private long period = -1;
    protected final DarkWaterJavaPlugin plugin;
    private int schedulerID;

    protected Scheduler(DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Scheduler setName(String name) {
        if (name == null || name.isBlank()) {
            this.name = DarkWaterUtils.getRandomASCII(60);
        } else {
            this.name = name;
        }
        return this;
    }

    public Scheduler setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public Scheduler setPeriod(long period) {
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

    public DarkWaterJavaPlugin getPlugin() {
        return this.plugin;
    }

    public String getName() {
        return name;
    }

    public long getDelay() {
        return delay;
    }

    public long getPeriod() {
        return period;
    }
}

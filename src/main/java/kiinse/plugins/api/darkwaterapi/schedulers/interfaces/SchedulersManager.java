package kiinse.plugins.api.darkwaterapi.schedulers.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.SchedulerException;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class SchedulersManager {

    private final @NotNull List<Scheduler> schedulers = new ArrayList<>();
    private final @NotNull DarkWaterJavaPlugin plugin;

    protected SchedulersManager(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void registerSchedule(@NotNull Scheduler scheduler) throws SchedulerException;

    public void startScheduler(@NotNull Scheduler scheduler) throws SchedulerException {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (schedule.isStarted()) {
                    throw new SchedulerException("This scheduler '" + scheduler.getName() + "' already started!");
                } else {
                    schedule.start();
                }
            }
        }
    }

    public void stopScheduler(@NotNull Scheduler scheduler) throws SchedulerException {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (!schedule.isStarted()) {
                    throw new SchedulerException("This scheduler '" + scheduler.getName() + "' already stopped!");
                } else {
                    schedule.stop();
                }
            }
        }
    }

    public void stopSchedules() {
        for (var scheduler : schedulers) {
            scheduler.stop();
        }
    }

    public boolean hasScheduler(@NotNull Scheduler scheduler) {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                return true;
            }
        }
        return false;
    }

    public @Nullable Scheduler getSchedulerByName(@NotNull String name) {
        for (var scheduler : schedulers) {
            if (Objects.equals(scheduler.getName(), name)) {
                return scheduler;
            }
        }
        return null;
    }

    public void unregister(@NotNull Scheduler scheduler) throws SchedulerException {
        if (!hasScheduler(scheduler)) {
            throw new SchedulerException("This scheduler '" + scheduler.getName() + "' not found!");
        }
        var iterator = schedulers.listIterator();
        while (iterator.hasNext()) {
            var schedule = iterator.next();
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (schedule.isStarted()) {
                    schedule.stop();
                }
                iterator.remove();
            }
        }
        plugin.sendLog("Scheduler '&b" + scheduler.getName() + "&a' by plugin '&b" + scheduler.getPlugin().getName() + "&a' has been unregistered!");
    }

    protected void register(@NotNull Scheduler scheduler) {
        schedulers.add(scheduler);
        plugin.sendLog("Scheduler '&b" + scheduler.getName() + "&a' by plugin '&b" + scheduler.getPlugin().getName() + "&a' has been registered!");
        scheduler.start();
    }

    public @NotNull List<Scheduler> getAllSchedulers() {
        return new ArrayList<>(schedulers);
    }
}

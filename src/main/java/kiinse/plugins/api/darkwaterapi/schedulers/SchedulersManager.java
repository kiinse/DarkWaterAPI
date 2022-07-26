package kiinse.plugins.api.darkwaterapi.schedulers;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class SchedulersManager {

    private final List<Scheduler> schedulers = new ArrayList<>();
    private final DarkWaterJavaPlugin plugin;

    protected SchedulersManager(DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void registerSchedule(Scheduler scheduler);

    public void startScheduler(Scheduler scheduler) throws Exception {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (!schedule.isStarted()) {
                    schedule.start();
                } else {
                    throw new Exception("This scheduler '" + scheduler.getName() + "' already started!");
                }
            }
        }
    }

    public void stopScheduler(Scheduler scheduler) throws Exception {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (schedule.isStarted()) {
                    schedule.stop();
                } else {
                    throw new Exception("This scheduler '" + scheduler.getName() + "' already stopped!");
                }
            }
        }
    }

    public void stopSchedules() {
        for (var scheduler : schedulers) {
            scheduler.stop();
        }
    }

    public boolean hasScheduler(Scheduler scheduler) {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                return true;
            }
        }
        return false;
    }

    public Scheduler getSchedulerByName(String name) {
        for (var scheduler : schedulers) {
            if (Objects.equals(scheduler.getName(), name)) {
                return scheduler;
            }
        }
        return null;
    }

    public void unregister(Scheduler scheduler) throws Exception {
        if (!hasScheduler(scheduler)) {
            throw new Exception("This scheduler '" + scheduler.getName() + "' not found!");
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

    protected void register(Scheduler scheduler) {
        schedulers.add(scheduler);
        plugin.sendLog("Scheduler '&b" + scheduler.getName() + "&a' by plugin '&b" + scheduler.getPlugin().getName() + "&a' has been registered!");
        scheduler.start();
    }

    public List<Scheduler> getAllSchedulers() {
        return new ArrayList<>(schedulers);
    }
}

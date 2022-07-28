package kiinse.plugins.api.darkwaterapi.schedulers;

import kiinse.plugins.api.darkwaterapi.exceptions.SchedulerException;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;
import kiinse.plugins.api.darkwaterapi.schedulers.interfaces.SchedulersManager;
import org.jetbrains.annotations.NotNull;

public class SchedulersManagerImpl extends SchedulersManager {

    public SchedulersManagerImpl(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerSchedule(@NotNull Scheduler scheduler) throws SchedulerException {
        var schedule = checkSchedulerData(scheduler);
        if (hasScheduler(schedule)) {
            throw new SchedulerException("Scheduler with same name '" + schedule.getName() + "' already exist!");
        }
        register(schedule);
    }

    private @NotNull Scheduler checkSchedulerData(@NotNull Scheduler scheduler) {
        var schedule = scheduler;
        var annotations = schedule.getClass().getAnnotation(SchedulerData.class);
        if (schedule.getName() == null) {
            schedule = schedule.setName(annotations.name());
        }
        if (schedule.getDelay() == -1) {
            schedule = schedule.setDelay(annotations.delay());
        }
        if (schedule.getPeriod() == -1) {
            schedule = schedule.setPeriod(annotations.period());
        }
        return schedule;
    }
}

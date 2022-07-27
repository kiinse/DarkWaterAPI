package kiinse.plugins.api.darkwaterapi.schedulers;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;
import org.jetbrains.annotations.NotNull;

public class SchedulersManagerImpl extends SchedulersManager {

    public SchedulersManagerImpl(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerSchedule(@NotNull Scheduler scheduler) throws IllegalArgumentException {
        var schedule = checkSchedulerData(scheduler);
        if (hasScheduler(schedule)) {
            throw new IllegalArgumentException("Scheduler with same name '" + schedule.getName() + "' already exist!");
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

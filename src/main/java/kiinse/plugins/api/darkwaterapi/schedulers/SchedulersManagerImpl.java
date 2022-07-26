package kiinse.plugins.api.darkwaterapi.schedulers;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;

public class SchedulersManagerImpl extends SchedulersManager {

    public SchedulersManagerImpl(DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerSchedule(Scheduler scheduler) throws IllegalArgumentException {
        if (scheduler == null) {
            throw new IllegalArgumentException("Scheduler is null!");
        }
        if (scheduler.getPlugin() == null) {
            throw new IllegalArgumentException("Scheduler plugin is null!");
        }
        var schedule = checkSchedulerData(scheduler);
        if (hasScheduler(schedule)) {
            throw new IllegalArgumentException("Scheduler with same name '" + schedule.getName() + "' already exist!");
        }
        register(schedule);
    }

    private Scheduler checkSchedulerData(Scheduler scheduler) {
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

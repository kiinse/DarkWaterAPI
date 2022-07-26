package kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SchedulerData(
        name = "MoveSchedule",
        delay = 1L
)
public class MoveSchedule extends Scheduler {

    private final Map<UUID, Integer> notMovingMap = new HashMap<>();

    public MoveSchedule(DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        for (var entry : notMovingMap.entrySet()) {
            entry.setValue(entry.getValue() + 1);
        }
    }

    public Map<UUID, Integer> getNotMovingMap() {
        return notMovingMap;
    }
}

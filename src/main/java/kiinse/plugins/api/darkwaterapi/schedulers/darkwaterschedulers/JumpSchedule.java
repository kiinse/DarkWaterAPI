package kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SchedulerData(
        name = "JumpSchedule",
        delay = 1L
)
public class JumpSchedule extends Scheduler {

    private final Map<UUID, Integer> jumpingMap = new HashMap<>();

    public JumpSchedule(DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        var iterator = jumpingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            entry.setValue(entry.getValue() + 1);
            if (entry.getValue() >= 2) {
                iterator.remove();
            }
        }
    }

    public Map<UUID, Integer> getJumpingMap() {
        return jumpingMap;
    }
}

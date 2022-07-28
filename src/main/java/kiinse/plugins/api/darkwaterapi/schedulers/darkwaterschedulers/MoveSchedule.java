package kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers;

import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SchedulerData(
        name = "MoveSchedule",
        delay = 1L
)
public class MoveSchedule extends Scheduler {

    private final Map<UUID, Integer> notMovingMap = new HashMap<>();

    public MoveSchedule(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        for (var entry : notMovingMap.entrySet()) {
            entry.setValue(entry.getValue() + 1);
        }
    }

    public @NotNull Map<UUID, Integer> getNotMovingMap() {
        return notMovingMap;
    }
}

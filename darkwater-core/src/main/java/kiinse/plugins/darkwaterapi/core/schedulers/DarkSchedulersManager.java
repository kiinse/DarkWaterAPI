// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.darkwaterapi.core.schedulers;

import kiinse.plugins.darkwaterapi.api.exceptions.SchedulerException;
import kiinse.plugins.darkwaterapi.api.schedulers.Scheduler;
import kiinse.plugins.darkwaterapi.api.schedulers.SchedulersManager;
import kiinse.plugins.darkwaterapi.api.schedulers.SchedulerData;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class DarkSchedulersManager implements SchedulersManager {

    private final @NotNull Set<Scheduler> schedulers = new HashSet<>();
    private final @NotNull DarkWaterJavaPlugin plugin;

    public DarkSchedulersManager(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull SchedulersManager register(@NotNull Scheduler scheduler) throws SchedulerException {
        var schedule = checkSchedulerData(scheduler);
        if (hasScheduler(schedule)) {
            throw new SchedulerException("Scheduler with same name '" + schedule.getName() + "' already exist!");
        }
        schedulers.add(scheduler);
        plugin.sendLog("Scheduler '&b" + scheduler.getName() + "&a' by plugin '&b" + scheduler.getPlugin().getName() + "&a' has been registered!");
        scheduler.start();
        return this;
    }

    public @NotNull SchedulersManager startScheduler(@NotNull Scheduler scheduler) throws SchedulerException {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (schedule.isStarted()) {
                    throw new SchedulerException("This scheduler '" + scheduler.getName() + "' already started!");
                } else {
                    schedule.start();
                }
            }
        }
        return this;
    }

    public @NotNull SchedulersManager stopScheduler(@NotNull Scheduler scheduler) throws SchedulerException {
        for (var schedule : schedulers) {
            if (Objects.equals(schedule.getName(), scheduler.getName())) {
                if (!schedule.isStarted()) {
                    throw new SchedulerException("This scheduler '" + scheduler.getName() + "' already stopped!");
                } else {
                    schedule.stop();
                }
            }
        }
        return this;
    }

    public @NotNull SchedulersManager stopSchedulers() {
        for (var scheduler : schedulers) {
            scheduler.stop();
        }
        return this;
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

    public @NotNull SchedulersManager unregister(@NotNull Scheduler scheduler) throws SchedulerException {
        if (!hasScheduler(scheduler)) {
            throw new SchedulerException("This scheduler '" + scheduler.getName() + "' not found!");
        }
        var iterator = schedulers.iterator();

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
        return this;
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

    public @NotNull Set<Scheduler> getAllSchedulers() {
        return new HashSet<>(schedulers);
    }
}

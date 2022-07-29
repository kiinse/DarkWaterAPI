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

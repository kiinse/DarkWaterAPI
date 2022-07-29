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

package kiinse.plugins.api.darkwaterapi.schedulers.darkwaterschedulers;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.indicators.interfaces.IndicatorManager;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.schedulers.Scheduler;
import kiinse.plugins.api.darkwaterapi.schedulers.annotation.SchedulerData;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@SchedulerData(
        name = "IndicatorSchedule"
)
public class IndicatorSchedule extends Scheduler {
    private final IndicatorManager indicators = DarkWaterAPI.getInstance().getIndicatorManager();

    public IndicatorSchedule(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean canStart() {
        return plugin.getConfiguration().getBoolean(Config.ACTIONBAR_INDICATORS) && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public void run() {
        for (var player : Bukkit.getOnlinePlayers()) {
            if (PlayerUtils.isSurvivalAdventure(player)) {
                PlayerUtils.sendActionBar(player, PlaceholderAPI.setPlaceholders(player, indicators.getIndicators()));
            }
        }
    }

}

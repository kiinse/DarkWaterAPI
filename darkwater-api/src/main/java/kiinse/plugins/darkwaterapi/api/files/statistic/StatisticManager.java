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

package kiinse.plugins.darkwaterapi.api.files.statistic;

import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface StatisticManager {

    @NotNull StatisticManager save() throws JsonFileException;

    @NotNull StatisticManager reload() throws JsonFileException;

    @NotNull DarkStatistic getPlayerStatistic(@NotNull Player player);

    @NotNull DarkStatistic getPlayerStatistic(@NotNull UUID player);

    @NotNull StatisticManager setPlayerStatistic(@NotNull Player player, @NotNull DarkStatistic statistic);

    @NotNull StatisticManager setPlayerStatistic(@NotNull UUID player, @NotNull DarkStatistic statistic);

    @NotNull StatisticManager addStatistic(@NotNull Player player, @NotNull EntityType type);

    @NotNull StatisticManager addStatistic(@NotNull UUID player, @NotNull EntityType type);

    @NotNull StatisticManager updatePlayer(@NotNull Player player, @NotNull JSONObject stats);

    @NotNull StatisticManager updatePlayer(@NotNull UUID player, @NotNull JSONObject stats);

    @NotNull HashMap<UUID, DarkStatistic> getAllPlayerStatistic();
}

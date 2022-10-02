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

package kiinse.plugins.darkwaterapi.common.listeners;

import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.JumpSchedule;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.MoveSchedule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MoveListener implements Listener {

    @EventHandler
    public void moveEvent(@NotNull PlayerMoveEvent event) {
        var player = event.getPlayer();
        if (!player.isInsideVehicle()) {
            MoveSchedule.getNotMovingMap().put(event.getPlayer().getUniqueId(), 0);
            if (!player.isClimbing() && !player.isFlying() && !player.isSwimming() && event.getFrom().getY() < Objects.requireNonNull(event.getTo()).getY() && event.getPlayer().getLocation().subtract(
                    0,
                    1,
                    0).getBlock().getType() != Material.AIR) {
                JumpSchedule.getJumpingMap().put(event.getPlayer().getUniqueId(), 0);
            }
        }
    }
}

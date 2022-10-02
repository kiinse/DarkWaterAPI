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

package kiinse.plugins.darkwaterapi.common.commands.statistic;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.commands.Command;
import kiinse.plugins.darkwaterapi.api.commands.DarkCommand;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.api.files.statistic.StatisticManager;
import kiinse.plugins.darkwaterapi.common.files.Replace;
import kiinse.plugins.darkwaterapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StatisticCommands implements DarkCommand {

    private final MessagesUtils messagesUtils;
    private final StatisticManager darkWaterStatistic;

    public StatisticCommands(@NotNull DarkWaterJavaPlugin plugin) {
        this.messagesUtils = new DarkMessagesUtils(plugin);
        this.darkWaterStatistic = plugin.getDarkWaterAPI().getDarkWaterStatistic();
    }

    @Override
    @Command(command = "statistic",
             permission = "darkwater.statistic",
             disallowNonPlayer = true)
    public void command(@NotNull CommandSender sender, @NotNull String[] args) {
        var stats = darkWaterStatistic.getPlayerStatistic(DarkPlayerUtils.getPlayer(sender)).getAllStatistic();
        String msg;
        if (!stats.isEmpty()) {
            var message = new StringBuilder();
            for (var stat : stats.entrySet()) {
                message.append(stat.getKey().toString()).append("&b: &a").append(stat.getValue()).append("\n");
            }
            msg = message.toString();
        } else {
            msg = "&a&lN/A";
        }
        messagesUtils.sendMessage(sender, Message.STATISTIC, Replace.STATISTIC, DarkUtils.colorize(msg));
    }
}

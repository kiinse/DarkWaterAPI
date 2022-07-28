package kiinse.plugins.api.darkwaterapi.commands.statistic;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandClass;
import kiinse.plugins.api.darkwaterapi.files.messages.MessagesUtilsImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Replace;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.MessagesUtils;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StatisticCommands implements CommandClass {

    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final MessagesUtils messagesUtils = new MessagesUtilsImpl(darkWaterAPI);

    @Override
    @Command(command = "/statistic", permission = "darkwater.statistic", disallowNonPlayer = true)
    public void mainCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        var stats = darkWaterAPI.getDarkWaterStatistic().getPlayerStatistic(PlayerUtils.getPlayer(sender)).getAllStatistic();
        String msg;
        if (!stats.isEmpty()) {
            var message = new StringBuilder();
            for (var stat : stats.entrySet()) {
                message.append(stat.getKey().toString()).append("&b:&a").append(stat.getValue()).append("\n");
            }
            msg = message.toString();
        } else {
            msg = "&a&lN/A";
        }
        messagesUtils.sendMessage(sender, Message.STATISTIC, Replace.STATISTIC, msg);
    }
}

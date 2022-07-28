package kiinse.plugins.api.darkwaterapi.commands.manager;

import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandFailureHandler;
import kiinse.plugins.api.darkwaterapi.commands.manager.reasons.CommandFailReason;
import kiinse.plugins.api.darkwaterapi.files.messages.SendMessagesImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.SendMessages;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FailureHandler implements CommandFailureHandler {
    private final SendMessages sendMessages;

    public FailureHandler(@NotNull DarkWaterJavaPlugin plugin) {
        this.sendMessages = new SendMessagesImpl(plugin.getDarkWaterAPI());
    }

    @Override
    public void handleFailure(@NotNull CommandFailReason reason, @NotNull CommandSender sender, @Nullable RegisteredCommand command) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(DarkWaterUtils.colorize("&cYou cannot execute this command outside of the game!"));
        } else {
            sendMessages.sendMessageWithPrefix(sender, reason);
        }
    }
}

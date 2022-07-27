package kiinse.plugins.api.darkwaterapi.commands.manager.interfaces;

import kiinse.plugins.api.darkwaterapi.commands.manager.RegisteredCommand;
import kiinse.plugins.api.darkwaterapi.commands.manager.reasons.CommandFailReason;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommandFailureHandler {

    /**
     * Error handler when using commands
     * @param reason Error reason
     * @param sender Command sender
     * @param command Command used
     */
    void handleFailure(@NotNull CommandFailReason reason, @NotNull CommandSender sender, @Nullable RegisteredCommand command);

}

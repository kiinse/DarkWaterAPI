package kiinse.plugins.api.darkwaterapi.commands.manager.interfaces;

import kiinse.plugins.api.darkwaterapi.commands.manager.CommandManager;
import kiinse.plugins.api.darkwaterapi.commands.manager.reasons.CommandFailReason;
import org.bukkit.command.CommandSender;

public interface CommandFailureHandler {

    /**
     * Error handler when using commands
     * @param reason Error reason
     * @param sender Command sender
     * @param command Command used
     */
    void handleFailure(CommandFailReason reason, CommandSender sender, CommandManager.RegisteredCommand command);

}

package kiinse.plugins.api.darkwaterapi.commands.manager.interfaces;

import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
public interface CommandClass {

    /**
     * Standard command method. Executed when a player enters a command into the chat
     * @param sender Command sender
     * @param args Array of arguments
     */
    void mainCommand(CommandSender sender, String[] args);

}

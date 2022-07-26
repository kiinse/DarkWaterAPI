package kiinse.plugins.api.darkwaterapi.commands.manager.reasons;

import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.MessagesKeys;

/**
 * Причины ошибок при использовании команд
 */
public enum CommandFailReason implements MessagesKeys {

    /**
     * Few parameters
     */
    INSUFFICIENT_PARAMETER,

    /**
     * A large number of parameters
     */
    REDUNDANT_PARAMETER,

    /**
     * No rights to use
     */
    NO_PERMISSION,

    /**
     * Used not by the player, but for example the console
     */
    NOT_PLAYER,

    /**
     * Command not found
     */
    COMMAND_NOT_FOUND,

    /**
     * Unidentified error
     */
    REFLECTION_ERROR

}

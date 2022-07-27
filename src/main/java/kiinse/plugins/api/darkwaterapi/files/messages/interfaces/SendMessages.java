package kiinse.plugins.api.darkwaterapi.files.messages.interfaces;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface SendMessages {

    /**
     * Send message to all players
     * @param message Message
     */
    void sendMessageToAll(@NotNull MessagesKeys message);

    /**
     * Send message with prefix to all players
     * @param message Message
     */
    void sendMessageWithPrefixToAll(@NotNull MessagesKeys message);

    /**
     * Sending a message to all players with the replacement of a word in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Sending a message to all players with the replacement of a word in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Sending a message to the player
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message);

    /**
     * Sending a message with a prefix to the player
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message);

    /**
     * Sending a message to the player
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message);

    /**
     * Sending a message with a prefix to the player
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send a message to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words);

    /**
     * Sending a message with a prefix to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words);

    /**
     * Send a message to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words);

    /**
     * Sending a message with a prefix to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words);
}

package kiinse.plugins.api.darkwaterapi.files.messages.interfaces;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public interface SendMessages {

    /**
     * Send message to all players
     * @param message Message
     */
    void sendMessageToAll(MessagesKeys message);

    /**
     * Send message with prefix to all players
     * @param message Message
     */
    void sendMessageWithPrefixToAll(MessagesKeys message);

    /**
     * Sending a message to all players with the replacement of a word in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageToAllWithReplace(MessagesKeys message, String from, String to);

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageWithPrefixToAllWithReplace(MessagesKeys message, String from, String to);

    /**
     * Sending a message to all players with the replacement of a word in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageToAllWithReplace(MessagesKeys message, ReplaceKeys from, String to);

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     * @param message Message
     * @param from Word to replace
     * @param to What the word will be replaced with
     */
    void sendMessageWithPrefixToAllWithReplace(MessagesKeys message, ReplaceKeys from, String to);

    /**
     * Sending a message to the player
     * @param player Player
     * @param message Message
     */
    void sendMessage(Player player, MessagesKeys message);

    /**
     * Sending a message with a prefix to the player
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(Player player, MessagesKeys message);

    /**
     * Sending a message to the player
     * @param player Player
     * @param message Message
     */
    void sendMessage(CommandSender player, MessagesKeys message);

    /**
     * Sending a message with a prefix to the player
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(CommandSender player, MessagesKeys message);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(Player player, MessagesKeys message, String from, String to);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(Player player, MessagesKeys message, ReplaceKeys from, String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(Player player, MessagesKeys message, String from, String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(Player player, MessagesKeys message, ReplaceKeys from, String to);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(CommandSender player, MessagesKeys message, String from, String to);

    /**
     * Send message to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessage(CommandSender player, MessagesKeys message, ReplaceKeys from, String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(CommandSender player, MessagesKeys message, String from, String to);

    /**
     * Send message with prefix to player with word replacement
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(CommandSender player, MessagesKeys message, ReplaceKeys from, String to);

    /**
     * Send a message to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessage(Player player, MessagesKeys message, String[] words);

    /**
     * Sending a message with a prefix to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(Player player, MessagesKeys message, String[] words);

    /**
     * Send a message to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessage(CommandSender player, MessagesKeys message, String[] words);

    /**
     * Sending a message with a prefix to the player with the replacement of words
     * @param player Player
     * @param message Message
     */
    void sendMessageWithPrefix(CommandSender player, MessagesKeys message, String[] words);
}

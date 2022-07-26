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

package kiinse.plugins.darkwaterapi.api.files.messages;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface MessagesUtils {

    /**
     * Send message to all players
     *
     * @param message Message
     */
    void sendMessageToAll(@NotNull MessagesKeys message);

    /**
     * Send message with prefix to all players
     *
     * @param message Message
     */
    void sendMessageWithPrefixToAll(@NotNull MessagesKeys message);

    /**
     * Sending a message to all players with the replacement of a word in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Sending a message to all players with the replacement of a word in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Sending a message to the player
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message);

    /**
     * Sending a message with a prefix to the player
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message);

    /**
     * Sending a message to the player
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message);

    /**
     * Sending a message with a prefix to the player
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message);

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to);

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to);

    /**
     * Send a message to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words);

    /**
     * Sending a message with a prefix to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words);

    /**
     * Send a message to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words);

    /**
     * Sending a message with a prefix to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words);
}

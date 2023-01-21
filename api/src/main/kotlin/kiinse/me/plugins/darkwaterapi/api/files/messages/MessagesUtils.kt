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
package kiinse.me.plugins.darkwaterapi.api.files.messages

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Suppress("unused")
interface MessagesUtils {
    /**
     * Send message to all players
     *
     * @param message Message
     */
    fun sendMessageToAll(message: MessagesKeys)

    /**
     * Send message with prefix to all players
     *
     * @param message Message
     */
    fun sendMessageWithPrefixToAll(message: MessagesKeys)

    /**
     * Sending a message to all players with the replacement of a word in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    fun sendMessageToAllWithReplace(message: MessagesKeys, from: String, to: String)

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    fun sendMessageWithPrefixToAllWithReplace(message: MessagesKeys, from: String, to: String)

    /**
     * Sending a message to all players with the replacement of a word in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    fun sendMessageToAllWithReplace(message: MessagesKeys, from: ReplaceKeys, to: String)

    /**
     * Sending a message with a prefix to all players with a word replacement in the message
     *
     * @param message Message
     * @param from    Word to replace
     * @param to      What the word will be replaced with
     */
    fun sendMessageWithPrefixToAllWithReplace(message: MessagesKeys, from: ReplaceKeys, to: String)

    /**
     * Sending a message to the player
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: Player, message: MessagesKeys)

    /**
     * Sending a message with a prefix to the player
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: Player, message: MessagesKeys)

    /**
     * Sending a message to the player
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: CommandSender, message: MessagesKeys)

    /**
     * Sending a message with a prefix to the player
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys)

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: Player, message: MessagesKeys, from: String, to: String)

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: Player, message: MessagesKeys, from: ReplaceKeys, to: String)

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: Player, message: MessagesKeys, from: String, to: String)

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: Player, message: MessagesKeys, from: ReplaceKeys, to: String)

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: CommandSender, message: MessagesKeys, from: String, to: String)

    /**
     * Send message to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: CommandSender, message: MessagesKeys, from: ReplaceKeys, to: String)

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys, from: String, to: String)

    /**
     * Send message with prefix to player with word replacement
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys, from: ReplaceKeys, to: String)

    /**
     * Send a message to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: Player, message: MessagesKeys, words: Array<String>)

    /**
     * Sending a message with a prefix to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: Player, message: MessagesKeys, words: Array<String>)

    /**
     * Send a message to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessage(player: CommandSender, message: MessagesKeys, words: Array<String>)

    /**
     * Sending a message with a prefix to the player with the replacement of words
     *
     * @param player  Player
     * @param message Message
     */
    fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys, words: Array<String>)
}
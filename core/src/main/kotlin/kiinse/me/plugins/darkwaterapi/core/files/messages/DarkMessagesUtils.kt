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
package kiinse.me.plugins.darkwaterapi.core.files.messages

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocales
import kiinse.me.plugins.darkwaterapi.api.files.messages.Messages
import kiinse.me.plugins.darkwaterapi.api.files.messages.MessagesKeys
import kiinse.me.plugins.darkwaterapi.api.files.messages.MessagesUtils
import kiinse.me.plugins.darkwaterapi.api.files.messages.ReplaceKeys
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DarkMessagesUtils(plugin: DarkWaterJavaPlugin) : MessagesUtils {

    private val locale: PlayerLocales = plugin.darkWaterAPI.playerLocales
    private val messages: Messages = plugin.getMessages()

    override fun sendMessageToAll(message: MessagesKeys) {
        Bukkit.getOnlinePlayers().forEach {
            execute(it, messages.getStringMessage(locale.getLocale(it), message))
        }
    }

    override fun sendMessageWithPrefixToAll(message: MessagesKeys) {
        Bukkit.getOnlinePlayers().forEach {
            execute(it, messages.getStringMessageWithPrefix(locale.getLocale(it), message))
        }
    }

    override fun sendMessageToAllWithReplace(message: MessagesKeys, from: String, to: String) {
        Bukkit.getOnlinePlayers().forEach {
            execute(it, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(it), message), from, to))
        }
    }

    override fun sendMessageToAllWithReplace(message: MessagesKeys, from: ReplaceKeys, to: String) {
        Bukkit.getOnlinePlayers().forEach {
            execute(it, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(it), message), from, to))
        }
    }

    override fun sendMessageWithPrefixToAllWithReplace(message: MessagesKeys, from: String, to: String) {
        Bukkit.getOnlinePlayers().forEach {
            execute(it, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(it), message), from, to))
        }
    }

    override fun sendMessageWithPrefixToAllWithReplace(message: MessagesKeys, from: ReplaceKeys, to: String) {
        Bukkit.getOnlinePlayers().forEach {
            execute(it, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(it), message), from, to))
        }
    }

    override fun sendMessage(player: Player, message: MessagesKeys) {
        execute(player, messages.getStringMessage(locale.getLocale(player), message))
    }

    override fun sendMessageWithPrefix(player: Player, message: MessagesKeys) {
        execute(player, messages.getStringMessageWithPrefix(locale.getLocale(player), message))
    }

    override fun sendMessage(player: CommandSender, message: MessagesKeys) {
        execute(player, messages.getStringMessage(locale.getLocale(player), message))
    }

    override fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys) {
        execute(player, messages.getStringMessageWithPrefix(locale.getLocale(player), message))
    }

    override fun sendMessage(player: Player, message: MessagesKeys, from: String, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to))
    }

    override fun sendMessage(player: Player, message: MessagesKeys, from: ReplaceKeys, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to))
    }

    override fun sendMessageWithPrefix(player: Player, message: MessagesKeys, from: String, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to))
    }

    override fun sendMessageWithPrefix(player: Player, message: MessagesKeys, from: ReplaceKeys, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to))
    }

    override fun sendMessage(player: CommandSender, message: MessagesKeys, from: String, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to))
    }

    override fun sendMessage(player: CommandSender, message: MessagesKeys, from: ReplaceKeys, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to))
    }

    override fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys, from: String, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to))
    }

    override fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys, from: ReplaceKeys, to: String) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to))
    }

    override fun sendMessage(player: Player, message: MessagesKeys, words: Array<String>) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), words))
    }

    override fun sendMessageWithPrefix(player: Player, message: MessagesKeys, words: Array<String>) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), words))
    }

    override fun sendMessage(player: CommandSender, message: MessagesKeys, words: Array<String>) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), words))
    }

    override fun sendMessageWithPrefix(player: CommandSender, message: MessagesKeys, words: Array<String>) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), words))
    }

    private fun execute(player: Player, msg: String) {
        player.spigot().sendMessage(*ComponentTags.parseMessage(msg))
    }

    private fun execute(player: CommandSender, msg: String) {
        player.spigot().sendMessage(*ComponentTags.parseMessage(msg))
    }
}
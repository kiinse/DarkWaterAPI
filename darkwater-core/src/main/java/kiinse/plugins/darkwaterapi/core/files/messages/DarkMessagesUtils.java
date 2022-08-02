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

package kiinse.plugins.darkwaterapi.core.files.messages;

import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.core.utilities.DarkUtils;
import kiinse.plugins.darkwaterapi.api.files.messages.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DarkMessagesUtils implements MessagesUtils {

    private final PlayerLocales locale;
    private final Messages messages;

    public DarkMessagesUtils(@NotNull DarkWaterJavaPlugin plugin) {
        messages = plugin.getMessages();
        locale = plugin.getDarkWaterAPI().getPlayerLocales();
    }

    @Override
    public void sendMessageToAll(@NotNull MessagesKeys message) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, messages.getStringMessage(locale.getLocale(player), message));
        }
    }

    @Override
    public void sendMessageWithPrefixToAll(@NotNull MessagesKeys message) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, messages.getStringMessageWithPrefix(locale.getLocale(player), message));
        }
    }

    @Override
    public void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessage(locale.getLocale(player), message));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessageWithPrefix(locale.getLocale(player), message));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessage(locale.getLocale(player), message));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessageWithPrefix(locale.getLocale(player), message));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), words));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), words));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessage(locale.getLocale(player), message), words));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getLocale(player), message), words));
    }

    private void execute(@NotNull Player player, @NotNull String msg) {
        player.spigot().sendMessage(ComponentTags.parseMessage(msg));
    }

    private void execute(@NotNull CommandSender player, @NotNull String msg) {
        player.spigot().sendMessage(ComponentTags.parseMessage(msg));
    }
}

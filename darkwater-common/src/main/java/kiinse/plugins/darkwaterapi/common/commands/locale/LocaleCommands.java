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

package kiinse.plugins.darkwaterapi.common.commands.locale;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.commands.Command;
import kiinse.plugins.darkwaterapi.api.commands.DarkCommand;
import kiinse.plugins.darkwaterapi.api.commands.SubCommand;
import kiinse.plugins.darkwaterapi.api.files.locale.Locale;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.api.files.messages.Messages;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.common.files.Replace;
import kiinse.plugins.darkwaterapi.common.gui.LocaleGUI;
import kiinse.plugins.darkwaterapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class LocaleCommands implements DarkCommand {

    private final DarkWaterJavaPlugin plugin;
    private final PlayerLocales locales;
    private final LocaleStorage storage;
    private final Messages messages;
    private final MessagesUtils messagesUtils;

    public LocaleCommands(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.locales = plugin.getDarkWaterAPI().getPlayerLocales();
        this.storage = plugin.getDarkWaterAPI().getLocaleStorage();
        this.messagesUtils = new DarkMessagesUtils(plugin);
    }

    @Override
    @Command(command = "locale", permission = "locale.help", disallowNonPlayer = true)
    public void command(@NotNull CommandSender sender, @NotNull String[] args) {
        messagesUtils.sendMessage(sender, Message.INFO_COMMAND);
        DarkPlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @SubCommand(command = "change", permission = "locale.change", disallowNonPlayer = true)
    public void change(@NotNull CommandSender sender, @NotNull String[] args) {
        var senderLocale = locales.getLocale(sender);
        DarkPlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_STEP);
        new LocaleGUI(plugin)
                .setPage(1)
                .setName(DarkUtils.replaceWord(messages.getStringMessage(senderLocale, Message.LOCALES_GUI), Replace.LOCALE, senderLocale.toString()))
                .setRows(4)
                .open(sender);
    }

    @SubCommand(command = "help", permission = "locale.help", disallowNonPlayer = true)
    public void help(@NotNull CommandSender sender, @NotNull String[] args) {
        messagesUtils.sendMessage(sender, Message.INFO_COMMAND);
        DarkPlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @SubCommand(command = "list", permission = "locale.list", disallowNonPlayer = true)
    public void list(@NotNull CommandSender sender, @NotNull String[] args) {
        messagesUtils.sendMessageWithPrefix(sender, Message.LOCALES_LIST, Replace.LOCALES, storage.getAllowedLocalesString());
        DarkPlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @SubCommand(command = "set", permission = "locale.change", parameters = 1, disallowNonPlayer = true)
    public void set(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args[0].isEmpty() || !storage.isAllowedLocale(Locale.valueOf(args[0].toLowerCase()))) {
            messagesUtils.sendMessageWithPrefix(sender, Message.LOCALE_NOT_FOUND, new String[] {
                    "{LOCALE}:" + (args[1].isEmpty() ? "NaN" : args[0].toLowerCase()),
                    "{LOCALES}:" + storage.getAllowedLocalesString()
            });
            DarkPlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
        } else {
            locales.setLocale(DarkPlayerUtils.getPlayer(sender), Locale.valueOf(args[0].toLowerCase()));
            messagesUtils.sendMessageWithPrefix(sender, Message.LOCALE_CHANGED, Replace.LOCALE, args[0].toLowerCase());
            plugin.sendLog("The player '&b" + DarkPlayerUtils.getPlayerName(sender) + "&a' has changed his language. Now his language is: '&b" + args[0].toLowerCase() + "&a'");
        }
    }

    @SubCommand(command = "get", permission = "locale.get", parameters = 1, disallowNonPlayer = true)
    public void get(@NotNull CommandSender sender, @NotNull String[] args) {
        var player = DarkPlayerUtils.getPlayer(args[0]);
        if (player == null) {
            messagesUtils.sendMessageWithPrefix(sender, Message.PLAYER_NOT_FOUND, Replace.PLAYER, args[0]);
            DarkPlayerUtils.playSound(sender, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE);
        } else {
            messagesUtils.sendMessageWithPrefix(sender, Message.GET_COMMAND, new String[] {
                "{PLAYER}:" + args[0],
                "{LOCALE}:" + locales.getLocale(player)
            });
            DarkPlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
        }
    }
}

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

package kiinse.plugins.api.darkwaterapi.commands.locale;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandClass;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocales;
import kiinse.plugins.api.darkwaterapi.files.messages.MessagesUtilsImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Replace;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.Messages;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.MessagesUtils;
import kiinse.plugins.api.darkwaterapi.gui.darkwatergui.LocaleGUI;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class LocaleCommands implements CommandClass {

    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final PlayerLocales locale = darkWaterAPI.getPlayerLocales();
    private final Messages messages = darkWaterAPI.getMessages();
    private final MessagesUtils messagesUtils = new MessagesUtilsImpl(darkWaterAPI);

    @Override
    @Command(command = "/locale change", permission = "locale.change", disallowNonPlayer = true)
    public void mainCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        var senderLocale = locale.getPlayerLocale(sender);
        PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_STEP);
        new LocaleGUI(1, DarkWaterUtils.replaceWord(messages.getStringMessage(senderLocale, Message.LOCALES_GUI), Replace.LOCALE, senderLocale.toString()), senderLocale).open(PlayerUtils.getPlayer(sender));
    }

    @Command(command = "/locale help", permission = "locale.help", disallowNonPlayer = true)
    public void help(@NotNull CommandSender sender) {
        messagesUtils.sendMessage(sender, Message.INFO_COMMAND);
        PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @Command(command = "/locale list", permission = "locale.list", disallowNonPlayer = true)
    public void list(@NotNull CommandSender sender) {
        messagesUtils.sendMessageWithPrefix(sender, Message.LOCALES_LIST, Replace.LOCALES, darkWaterAPI.getLocaleStorage().getAllowedLocalesString());
        PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @Command(command = "/locale set", permission = "locale.change", parameters = 1, disallowNonPlayer = true)
    public void set(@NotNull CommandSender sender, @NotNull String[] args) {
        var storage = darkWaterAPI.getLocaleStorage();
        if (args[0].isEmpty() || !storage.isAllowedLocale(Locale.valueOf(args[0].toLowerCase()))) {
            messagesUtils.sendMessageWithPrefix(sender, Message.LOCALE_NOT_FOUND, new String[] {
                    "{LOCALE}:" + (args[1].isEmpty() ? "NaN" : args[0].toLowerCase()),
                    "{LOCALES}:" + storage.getAllowedLocalesString()
            });
            PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
        } else {
            locale.setPlayerLocale(PlayerUtils.getPlayer(sender), Locale.valueOf(args[0].toLowerCase()));
            messagesUtils.sendMessageWithPrefix(sender, Message.LOCALE_CHANGED, Replace.LOCALE, args[0].toLowerCase());
            darkWaterAPI.sendLog("The player '&b" + PlayerUtils.getPlayerName(sender) + "&a' has changed his language. Now his language is: '&b" + args[0].toLowerCase() + "&a'");
        }
    }

    @Command(command = "/locale get", permission = "locale.get", parameters = 1, disallowNonPlayer = true)
    public void get(@NotNull CommandSender sender, @NotNull String[] args) {
        var player = PlayerUtils.getPlayer(args[0]);
        if (player == null) {
            messagesUtils.sendMessageWithPrefix(sender, Message.PLAYER_NOT_FOUND, Replace.PLAYER, args[0]);
            PlayerUtils.playSound(sender, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE);
        } else {
            messagesUtils.sendMessageWithPrefix(sender, Message.GET_COMMAND, new String[] {
                "{PLAYER}:" + args[0],
                "{LOCALE}:" + locale.getPlayerLocale(player)
            });
            PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
        }
    }
}

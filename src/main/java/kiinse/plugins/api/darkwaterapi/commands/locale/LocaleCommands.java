package kiinse.plugins.api.darkwaterapi.commands.locale;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandClass;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.files.messages.SendMessagesImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.Messages;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.SendMessages;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Replace;
import kiinse.plugins.api.darkwaterapi.gui.darkwatergui.LocaleGUI;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;

public class LocaleCommands implements CommandClass {

    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final PlayerLocale locale = darkWaterAPI.getLocales();
    private final Messages messages = darkWaterAPI.getMessages();
    private final SendMessages sendMessages = new SendMessagesImpl(darkWaterAPI);

    @Override
    @Command(command = "/locale change", permission = "locale.change", disallowNonPlayer = true)
    public void mainCommand(CommandSender sender, String[] args) {
        var senderLocale = locale.getPlayerLocale(sender);
        PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_STEP);
        new LocaleGUI(1, DarkWaterUtils.replaceWord(messages.getStringMessage(senderLocale, Message.LOCALES_GUI), Replace.LOCALE, senderLocale.toString()), senderLocale).open(PlayerUtils.getPlayer(sender));
    }

    @Command(command = "/locale help", permission = "locale.help", disallowNonPlayer = true)
    public void help(CommandSender sender, String[] args) {
        sendMessages.sendMessage(sender, Message.INFO_COMMAND);
        PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @Command(command = "/locale list", permission = "locale.list", disallowNonPlayer = true)
    public void list(CommandSender sender, String[] args) {
        sendMessages.sendMessageWithPrefix(sender, Message.LOCALES_LIST, Replace.LOCALES, darkWaterAPI.getLocaleStorage().getAllowedLocalesString());
        PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
    }

    @Command(command = "/locale set", permission = "locale.change", parameters = 1, disallowNonPlayer = true)
    public void set(CommandSender sender, String[] args) {
        var storage = darkWaterAPI.getLocaleStorage();
        if (args[0].isEmpty() || !storage.isAllowedLocale(Locale.valueOf(args[0].toLowerCase()))) {
            sendMessages.sendMessageWithPrefix(sender, Message.LOCALE_NOT_FOUND, new String[] {
                    "{LOCALE}:" + (args[1].isEmpty() ? "NaN" : args[0].toLowerCase()),
                    "{LOCALES}:" + storage.getAllowedLocalesString()
            });
            PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
        } else {
            locale.setPlayerLocale(PlayerUtils.getPlayer(sender), Locale.valueOf(args[0].toLowerCase()));
            sendMessages.sendMessageWithPrefix(sender, Message.LOCALE_CHANGED, Replace.LOCALE, args[0].toLowerCase());
            darkWaterAPI.sendLog("The player '&b" + PlayerUtils.getPlayerName(sender) + "&a' has changed his language. Now his language is: '&b" + args[0].toLowerCase() + "&a'");
        }
    }

    @Command(command = "/locale get", permission = "locale.get", parameters = 1, disallowNonPlayer = true)
    public void get(CommandSender sender, String[] args) {
        var player = PlayerUtils.getPlayer(args[0]);
        if (player == null) {
            sendMessages.sendMessageWithPrefix(sender, Message.PLAYER_NOT_FOUND, Replace.PLAYER, args[0]);
            PlayerUtils.playSound(sender, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE);
        } else {
            sendMessages.sendMessageWithPrefix(sender, Message.GET_COMMAND, new String[] {
                "{PLAYER}:" + args[0],
                "{LOCALE}:" + locale.getPlayerLocale(player).toString()
            });
            PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
        }
    }
}

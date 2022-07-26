package kiinse.plugins.api.darkwaterapi.files.messages;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.*;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendMessagesImpl implements SendMessages {

    private final PlayerLocale locale = DarkWaterAPI.getInstance().getLocales();
    private final ComponentLabels componentLabels = new ParseComponentLabels();
    private final Messages messages;

    public SendMessagesImpl(DarkWaterJavaPlugin plugin) {
        messages = plugin.getMessages();
    }

    @Override
    public void sendMessageToAll(MessagesKeys message) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, messages.getStringMessage(locale.getPlayerLocale(player), message));
        }
    }

    @Override
    public void sendMessageWithPrefixToAll(MessagesKeys message) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message));
        }
    }

    @Override
    public void sendMessageToAllWithReplace(MessagesKeys message, String from, String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageToAllWithReplace(MessagesKeys message, ReplaceKeys from, String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageWithPrefixToAllWithReplace(MessagesKeys message, String from, String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageWithPrefixToAllWithReplace(MessagesKeys message, ReplaceKeys from, String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessage(Player player, MessagesKeys message) {
        execute(player, messages.getStringMessage(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessageWithPrefix(Player player, MessagesKeys message) {
        execute(player, messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessage(CommandSender player, MessagesKeys message) {
        execute(player, messages.getStringMessage(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessageWithPrefix(CommandSender player, MessagesKeys message) {
        execute(player, messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessage(Player player, MessagesKeys message, String from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(Player player, MessagesKeys message, ReplaceKeys from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(Player player, MessagesKeys message, String from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(Player player, MessagesKeys message, ReplaceKeys from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(CommandSender player, MessagesKeys message, String from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(CommandSender player, MessagesKeys message, ReplaceKeys from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(CommandSender player, MessagesKeys message, String from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(CommandSender player, MessagesKeys message, ReplaceKeys from, String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(Player player, MessagesKeys message, String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), words));
    }

    @Override
    public void sendMessageWithPrefix(Player player, MessagesKeys message, String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), words));
    }

    @Override
    public void sendMessage(CommandSender player, MessagesKeys message, String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), words));
    }

    @Override
    public void sendMessageWithPrefix(CommandSender player, MessagesKeys message, String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), words));
    }

    private void execute(Player player, String msg) {
        player.sendMessage(componentLabels.parseMessage(msg));
    }

    private void execute(CommandSender player, String msg) {
        player.sendMessage(componentLabels.parseMessage(msg));
    }
}

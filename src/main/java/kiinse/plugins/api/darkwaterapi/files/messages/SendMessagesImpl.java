package kiinse.plugins.api.darkwaterapi.files.messages;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.*;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendMessagesImpl implements SendMessages {

    private final PlayerLocale locale = DarkWaterAPI.getInstance().getLocales();
    private final ComponentLabels componentLabels = new ParseComponentLabels();
    private final Messages messages;

    public SendMessagesImpl(@NotNull DarkWaterJavaPlugin plugin) {
        messages = plugin.getMessages();
    }

    @Override
    public void sendMessageToAll(@NotNull MessagesKeys message) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, messages.getStringMessage(locale.getPlayerLocale(player), message));
        }
    }

    @Override
    public void sendMessageWithPrefixToAll(@NotNull MessagesKeys message) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message));
        }
    }

    @Override
    public void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessageWithPrefixToAllWithReplace(@NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        for (var player : Bukkit.getOnlinePlayers()) {
            execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
        }
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessage(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessage(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message) {
        execute(player, messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull ReplaceKeys from, @NotNull String to) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), from, to));
    }

    @Override
    public void sendMessage(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), words));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull Player player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), words));
    }

    @Override
    public void sendMessage(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessage(locale.getPlayerLocale(player), message), words));
    }

    @Override
    public void sendMessageWithPrefix(@NotNull CommandSender player, @NotNull MessagesKeys message, @NotNull String[] words) {
        execute(player, DarkWaterUtils.replaceWord(messages.getStringMessageWithPrefix(locale.getPlayerLocale(player), message), words));
    }

    private void execute(@NotNull Player player, @NotNull String msg) {
        player.sendMessage(componentLabels.parseMessage(msg));
    }

    private void execute(@NotNull CommandSender player, @NotNull String msg) {
        player.sendMessage(componentLabels.parseMessage(msg));
    }
}

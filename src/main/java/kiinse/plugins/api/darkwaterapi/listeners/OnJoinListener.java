package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.config.enums.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.files.messages.SendMessagesImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.SendMessages;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Replace;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class OnJoinListener implements Listener {

    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final PlayerLocale locale = darkWaterAPI.getLocales();
    private final YamlFile config = darkWaterAPI.getConfiguration();
    private final SendMessages sendMessages = new SendMessagesImpl(darkWaterAPI);

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(darkWaterAPI, () -> setLocale(event.getPlayer()), 200);
    }

    private void setLocale(@NotNull Player player) {
        var interfaceLocale = player.locale().getLanguage();
        if (!locale.isPlayerLocalized(player)) {
            locale.setPlayerLocale(player, Locale.valueOf(interfaceLocale));
            if (config.getBoolean(Config.FIRST_JOIN_MESSAGE)) {
                sendMessages.sendMessage(player, Message.FIRST_JOIN, Replace.LOCALE, interfaceLocale);
                PlayerUtils.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            darkWaterAPI.sendLog(Level.CONFIG, "The player &d" + PlayerUtils.getPlayerName(player) + "&6 has been added to the plugin. His language is defined as " + interfaceLocale);
        }
        darkWaterAPI.sendLog(Level.CONFIG, "Player &d" + PlayerUtils.getPlayerName(player) + "&6 joined. His locale is " + locale.getPlayerLocale(player));
    }
}

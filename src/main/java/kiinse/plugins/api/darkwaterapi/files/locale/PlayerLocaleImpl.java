package kiinse.plugins.api.darkwaterapi.files.locale;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class PlayerLocaleImpl implements PlayerLocale {

    private final DarkWaterAPI darkWaterAPI;
    private final LocaleStorage storage;

    public PlayerLocaleImpl(DarkWaterAPI darkWaterAPI, LocaleStorage storage) {
        this.darkWaterAPI = darkWaterAPI;
        this.storage = storage;
    }

    @Override
    public boolean isPlayerLocalized(Player player) {
        return storage.isLocalesDataContains(player);
    }

    @Override
    public Locale getPlayerLocale(Player player) {
        if (isPlayerLocalized(player)) {
            return storage.getLocalesData(player);
        } return storage.getDefaultLocale();
    }

    @Override
    public Locale getPlayerLocale(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return storage.getDefaultLocale();
        }
        var player = PlayerUtils.getPlayer(sender);
        if (isPlayerLocalized(player)) {
            return storage.getLocalesData(player);
        } return storage.getDefaultLocale();
    }

    @Override
    public void setPlayerLocale(Player player, Locale locale) {
        if (isPlayerLocalized(player) && storage.removeLocalesData(player)) {
            darkWaterAPI.sendLog(Level.CONFIG, "Player '" + PlayerUtils.getPlayerName(player) + "' locale has been removed");
        }
        if (storage.putInLocalesData(player, storage.isAllowedLocale(locale) ? locale : storage.getDefaultLocale())) {
            darkWaterAPI.sendLog(Level.CONFIG, "Player '" + PlayerUtils.getPlayerName(player)  + "' locale has been added. Locale: " + locale.toString());
        }
    }

    @Override
    public Locale getPlayerInterfaceLocale(Player player) {
        return Locale.valueOf(player.locale().getLanguage());
    }
}

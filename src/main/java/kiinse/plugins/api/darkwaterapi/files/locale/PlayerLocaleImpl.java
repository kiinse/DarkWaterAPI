package kiinse.plugins.api.darkwaterapi.files.locale;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.PlayerLocale;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class PlayerLocaleImpl implements PlayerLocale {

    private final DarkWaterAPI darkWaterAPI;
    private final LocaleStorage storage;

    public PlayerLocaleImpl(@NotNull DarkWaterAPI darkWaterAPI, @NotNull LocaleStorage storage) {
        this.darkWaterAPI = darkWaterAPI;
        this.storage = storage;
    }

    @Override
    public boolean isPlayerLocalized(@NotNull Player player) {
        return storage.isLocalesDataContains(player);
    }

    @Override
    public @NotNull Locale getPlayerLocale(@NotNull Player player) {
        if (isPlayerLocalized(player)) {
            return storage.getLocalesData(player);
        } return storage.getDefaultLocale();
    }

    @Override
    public @NotNull Locale getPlayerLocale(@NotNull CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return storage.getDefaultLocale();
        }
        var player = PlayerUtils.getPlayer(sender);
        if (isPlayerLocalized(player)) {
            return storage.getLocalesData(player);
        } return storage.getDefaultLocale();
    }

    @Override
    public void setPlayerLocale(@NotNull Player player, @NotNull Locale locale) {
        if (isPlayerLocalized(player) && storage.removeLocalesData(player)) {
            darkWaterAPI.sendLog(Level.CONFIG, "Player '" + PlayerUtils.getPlayerName(player) + "' locale has been removed");
        }
        if (storage.putInLocalesData(player, storage.isAllowedLocale(locale) ? locale : storage.getDefaultLocale())) {
            darkWaterAPI.sendLog(Level.CONFIG, "Player '" + PlayerUtils.getPlayerName(player)  + "' locale has been added. Locale: " + locale);
        }
    }

    @Override
    public @NotNull Locale getPlayerInterfaceLocale(@NotNull Player player) {
        return Locale.valueOf(player.locale().getLanguage());
    }
}

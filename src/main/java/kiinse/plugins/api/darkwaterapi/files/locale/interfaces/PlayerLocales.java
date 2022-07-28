package kiinse.plugins.api.darkwaterapi.files.locale.interfaces;

import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface PlayerLocales {

    boolean isPlayerLocalized(@NotNull Player player);

    @NotNull Locale getPlayerLocale(@NotNull Player player);

    @NotNull Locale getPlayerLocale(@NotNull CommandSender sender);

    void setPlayerLocale(@NotNull Player player, @NotNull Locale locale);

    @NotNull Locale getPlayerInterfaceLocale(@NotNull Player player);
}

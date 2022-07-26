package kiinse.plugins.api.darkwaterapi.placeholders;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LocaleExpansion extends PlaceholderExpansion {

    private final DarkWaterAPI darkWaterAPI;

    // %locale_player%
    // %locale_list%

    public LocaleExpansion(DarkWaterAPI darkWaterAPI){
        this.darkWaterAPI = darkWaterAPI;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getAuthor(){
        return darkWaterAPI.getDescription().getAuthors().get(0);
    }


    @Override
    public @NotNull String getIdentifier(){
        return "locale";
    }


    @Override
    public @NotNull String getVersion(){
        return darkWaterAPI.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier){
        if(player == null){
            return "";
        }
        return switch (identifier) {
            case "player" -> darkWaterAPI.getLocales().getPlayerLocale(player).toString();
            case "list" -> darkWaterAPI.getLocaleStorage().getAllowedLocalesString();
            default -> null;
        };
    }
}

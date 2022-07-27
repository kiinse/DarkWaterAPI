package kiinse.plugins.api.darkwaterapi.placeholders;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.statistic.interfaces.DarkWaterStatistic;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatisticExpansion extends PlaceholderExpansion {

    private final DarkWaterAPI darkWaterAPI;
    private final DarkWaterStatistic darkWaterStatistic;

    // %statistic_MOB%

    public StatisticExpansion(@NotNull DarkWaterAPI darkWaterAPI){
        this.darkWaterStatistic = darkWaterAPI.getDarkWaterStatistic();
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
        return "statistic";
    }

    @Override
    public @NotNull String getVersion(){
        return darkWaterAPI.getDescription().getVersion();
    }

    @Override
    public @NotNull String onPlaceholderRequest(@Nullable Player player, @NotNull String identifier){
        if(player == null){
            return "";
        }
        return String.valueOf(darkWaterStatistic.getPlayerStatistic(player).getStatistic(EntityType.valueOf(identifier)));
    }
}

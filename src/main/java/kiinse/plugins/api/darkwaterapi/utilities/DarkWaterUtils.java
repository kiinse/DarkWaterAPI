package kiinse.plugins.api.darkwaterapi.utilities;

import com.google.common.base.Strings;
import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.ReplaceKeys;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.enums.TaskType;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class DarkWaterUtils {

    private enum Word {
        FIRST,
        SECOND
    }

    public static @NotNull String getProgressBar(int current, int max, int totalBars, @NotNull String char1, @NotNull String char2) {
        int progressBars = (int) (totalBars * ((float) current / max));
        return Strings.repeat("" + char1, progressBars) + Strings.repeat("" + char2, totalBars - progressBars);
    }

    public static @NotNull String colorize(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static boolean isClickAction(@NotNull Action action) {
        return action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR);
    }

    public static boolean isStringEmpty(@Nullable String string) {
        return string == null || string.isBlank();
    }

    public static @NotNull String replaceWord(@NotNull String text, @NotNull String from, @NotNull String to) {
        return text.replace(from, to);
    }

    public static @NotNull String replaceWord(@NotNull String text, @NotNull ReplaceKeys from, @NotNull String to) {
        return text.replace(formatReplaceKeys(from), to);
    }

    public static @NotNull String replaceWord(@NotNull String text, @NotNull String[] words) {
        var result = text;
        for (var word : words) {
            result = replaceWord(result, getWord(word, Word.FIRST), getWord(word, Word.SECOND));
        }
        return result;
    }

    public static BukkitTask runTask(@NotNull TaskType taskType, @NotNull Runnable task) {
        if (taskType == TaskType.ASYNC) {
            return Bukkit.getScheduler().runTaskAsynchronously(DarkWaterAPI.getInstance(), task);
        } else {
            return Bukkit.getScheduler().runTask(DarkWaterAPI.getInstance(), task);
        }
    }

    public static BukkitTask runTask(@NotNull TaskType taskType, @NotNull DarkWaterJavaPlugin plugin, @NotNull Runnable task) {
        if (taskType == TaskType.ASYNC) {
            return Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        } else {
            return Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    public static @NotNull String formatReplaceKeys(@NotNull ReplaceKeys key) {
        return "{" + key + "}";
    }

    private static @NotNull String getWord(@NotNull String text, @NotNull Word word) {
        var result = text.split(":");
        if (word.equals(Word.FIRST)) {
            return result[0];
        }
        return result[1];
    }

    public static @NotNull List<Block> getRegionBlocks(@NotNull World world, @NotNull Location loc1, @NotNull Location loc2) {
        var blocks = new ArrayList<Block>();
        for(var x = loc1.getX(); x <= loc2.getX(); x++) {
            for(var y = loc1.getY(); y <= loc2.getY(); y++) {
                for(var z = loc1.getZ(); z <= loc2.getZ(); z++) {
                    blocks.add(new Location(world, x, y, z).getBlock());
                }
            }
        }
        return blocks;
    }

    public static @NotNull String getRandomASCII(int length) {
        return RandomStringUtils.randomAscii(length).replace(">", "").replace("<", "").replace(":", "");
    }

    private DarkWaterUtils() {}
}

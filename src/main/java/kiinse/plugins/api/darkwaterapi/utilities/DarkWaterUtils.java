package kiinse.plugins.api.darkwaterapi.utilities;

import com.google.common.base.Strings;
import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.ReplaceKeys;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class DarkWaterUtils {

    private enum Word {
        FIRST,
        SECOND
    }

    public static String getProgressBar(int current, int max, int totalBars, String char1, String char2) {
        int progressBars = (int) (totalBars * ((float) current / max));
        return Strings.repeat("" + char1, progressBars) + Strings.repeat("" + char2, totalBars - progressBars);
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static boolean isClickAction(Action action) {
        return action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR);
    }

    public static boolean isStringEmpty(String string) {
        return string == null || string.isBlank();
    }

    public static String replaceWord(String text, String from, String to) {
        return text.replace(from, to);
    }

    public static String replaceWord(String text, ReplaceKeys from, String to) {
        return text.replace(formatReplaceKeys(from), to);
    }

    public static String replaceWord(String text, String[] words) {
        var result = text;
        for (var word : words) {
            result = replaceWord(result, getWord(word, Word.FIRST), getWord(word, Word.SECOND));
        }
        return result;
    }

    public static String formatReplaceKeys(ReplaceKeys key) {
        return "{" + key.toString() + "}";
    }

    private static String getWord(String text, Word word) {
        var result = text.split(":");
        if (word.equals(Word.FIRST)) {
            return result[0];
        }
        return result[1];
    }

    public static int formatVersion(String version) {
        return Integer.parseInt(version.replace(".", "").split("-")[0]);
    }

    public static String getDarkWaterApiVersion() {
        return DarkWaterAPI.getInstance().getDescription().getVersion();
    }

    public static List<Block> getRegionBlocks(World world, Location loc1, Location loc2) {
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

    public static String getRandomASCII(int length) {
        return RandomStringUtils.randomAscii(length).replace(">", "").replace("<", "").replace(":", "");
    }

    private DarkWaterUtils() {}
}

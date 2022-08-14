// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.darkwaterapi.core.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.base.Strings;
import kiinse.plugins.darkwaterapi.api.files.messages.ReplaceKeys;
import kiinse.plugins.darkwaterapi.api.utilities.TaskType;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
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

import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "UnusedReturnValue", "BooleanMethodIsAlwaysInverted"})
public class DarkUtils {

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

    public static @NotNull BukkitTask runTask(@NotNull TaskType taskType, @NotNull DarkWaterJavaPlugin plugin, @NotNull Runnable task) {
        if (taskType == TaskType.ASYNC) {
            return Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        } else {
            return Bukkit.getScheduler().runTask(plugin, task);
        }
    }
    public static long getTaskSpeed(@NotNull Runnable task) {
        var prevTime = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - prevTime;
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
        return RandomStringUtils.randomAscii(length);
    }

    public static @NotNull String generateJwtToken(@NotNull String user, @NotNull RSAPrivateKey key, int expiresInHours) {
        return JWT.create()
                .withSubject(user)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expiresInHours)))
                .sign(Algorithm.RSA256(key));
    }

    public static @NotNull String generateJwtToken(@NotNull String user, @NotNull String secret, int expiresInHours) {
        return JWT.create()
                .withSubject(user)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expiresInHours)))
                .sign(Algorithm.HMAC256(secret));
    }

    private DarkUtils() {}
}

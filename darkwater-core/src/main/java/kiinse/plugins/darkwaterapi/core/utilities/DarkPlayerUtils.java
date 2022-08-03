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

import kiinse.plugins.darkwaterapi.api.utilities.PermissionsKeys;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.JumpSchedule;
import kiinse.plugins.darkwaterapi.core.schedulers.darkwater.MoveSchedule;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

@SuppressWarnings("unused")
public class DarkPlayerUtils {

    public static boolean isActing(@NotNull Player player) {
        return isJumping(player) || player.isSprinting() || player.isInWater() || isClimbing(player);
    }

    public static boolean isWalking(@NotNull Player player) {
        var value = MoveSchedule.getNotMovingMap().get(player.getUniqueId());
        return value != null && value <= 10;
    }

    public static boolean isJumping(@NotNull Player player) {
        return JumpSchedule.getJumpingMap().containsKey(player.getUniqueId());
    }

    public static boolean isClimbing(@NotNull Player player) {
        return player.isClimbing() && isWalking(player);
    }

    public static boolean isSurvivalAdventure(@NotNull Player player) {
        var gameMode = player.getGameMode();
        return gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE;
    }

    public static boolean isSurvivalAdventure(@NotNull CommandSender sender) {
        var gameMode = getPlayer(sender).getGameMode();
        return gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE;
    }

    public static boolean isCreativeSpectator(@NotNull Player player) {
        var gameMode = player.getGameMode();
        return gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR;
    }

    public static boolean isCreativeSpectator(@NotNull CommandSender sender) {
        var gameMode = getPlayer(sender).getGameMode();
        return gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR;
    }

    public static boolean isSurvival(@NotNull Player player) {
        return player.getGameMode() == GameMode.SURVIVAL;
    }

    public static boolean isSurvival(@NotNull CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.SURVIVAL;
    }

    public static boolean isAdventure(@NotNull Player player) {
        return player.getGameMode() == GameMode.ADVENTURE;
    }

    public static boolean isAdventure(@NotNull CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.ADVENTURE;
    }

    public static boolean isCreative(@NotNull Player player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }

    public static boolean isCreative(@NotNull CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.CREATIVE;
    }

    public static boolean isSpectator(@NotNull Player player) {
        return player.getGameMode() == GameMode.SPECTATOR;
    }

    public static boolean isSpectator(@NotNull CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.SPECTATOR;
    }

    public static boolean isPoisoned(@NotNull Player player) {
        return player.hasPotionEffect(PotionEffectType.POISON);
    }

    public static boolean isPoisoned(@NotNull CommandSender sender) {
        return getPlayer(sender).hasPotionEffect(PotionEffectType.POISON);
    }

    public static boolean isInRain(@NotNull Player player) {
        var world = player.getWorld();
        var block = world.getHighestBlockAt(player.getLocation());
        return !world.isClearWeather() && block.getLocation().getY() > player.getLocation().getY() && block.getType() == Material.AIR;
    }

    public static boolean isInRain(@NotNull CommandSender sender) {
        var player = getPlayer(sender);
        var world = player.getWorld();
        var block = world.getHighestBlockAt(player.getLocation());
        return !world.isClearWeather() && block.getLocation().getY() > player.getLocation().getY() && block.getType() == Material.AIR;
    }

    public static boolean isInLava(@NotNull Player player) {
        return player.getLocation().getBlock().getType() == Material.LAVA;
    }

    public static boolean isInLava(@NotNull CommandSender sender) {
        return getPlayer(sender).getLocation().getBlock().getType() == Material.LAVA;
    }

    public static void playSound(@NotNull CommandSender sender, @NotNull Sound sound, float v, float v1) {
        if (sender instanceof ConsoleCommandSender) {
            return;
        }
        var player = getPlayer(sender);
        player.playSound(player, sound, v,  v1);
    }

    public static void playSound(@NotNull Player player, @NotNull Sound sound, float v, float v1) {
        player.playSound(player, sound, v,  v1);
    }

    public static void playSound(@NotNull CommandSender sender, @NotNull Sound sound, float v) {
        if (sender instanceof ConsoleCommandSender) {
            return;
        }
        var player = getPlayer(sender);
        player.playSound(player, sound, v, 1f);
    }

    public static void playSound(@NotNull Player player, @NotNull Sound sound, float v) {
        player.playSound(player, sound, v,  1f);
    }

    public static void playSound(@NotNull CommandSender sender, @NotNull Sound sound) {
        if (sender instanceof ConsoleCommandSender) {
            return;
        }
        var player = getPlayer(sender);
        player.playSound(player, sound, 1f,  1f);
    }

    public static void playSound(@NotNull Player player, @NotNull Sound sound) {
        player.playSound(player, sound, 1f,  1f);
    }

    public static @Nullable Player getPlayer(@NotNull UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static @Nullable Player getPlayer(@NotNull String name) {
        return Bukkit.getPlayer(name);
    }

    public static @NotNull Player getPlayer(@NotNull PlayerEvent event) {
        return event.getPlayer();
    }

    public static @NotNull Player getPlayer(@NotNull CommandSender sender) {
        return (Player) sender;
    }

    public static @NotNull String getPlayerName(@NotNull CommandSender sender) {
        return sender.getName();
    }

    public static void sendActionBar(@NotNull Player player, @NotNull String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
    }

    public static void sendActionBar(@NotNull Player player, @NotNull BaseComponent message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
    }

    public static void sendActionBar(@NotNull CommandSender sender, @NotNull String message) {
        getPlayer(sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
    }

    public static void sendActionBar(@NotNull CommandSender sender, @NotNull BaseComponent message) {
        getPlayer(sender).spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
    }

    public static boolean hasPermission(@NotNull CommandSender sender, @NotNull PermissionsKeys permission) {
        return sender.hasPermission(formatPermissionsKey(permission));
    }

    public static boolean hasPermission(@NotNull Player player, @NotNull PermissionsKeys permission) {
        return player.hasPermission(formatPermissionsKey(permission));
    }

    public static boolean hasPermission(@NotNull CommandSender sender, @NotNull Permission permission) {
        return sender.hasPermission(permission);
    }

    public static boolean hasPermission(@NotNull Player player, @NotNull Permission permission) {
        return player.hasPermission(permission);
    }

    public static void giveItem(@NotNull Player player, @NotNull ItemStack item) {
        player.getInventory().addItem(item);
    }

    public static void giveItem(@NotNull CommandSender sender, @NotNull ItemStack item) {
        getPlayer(sender).getInventory().addItem(item);
    }

    public static @NotNull String getPlayerID(@NotNull String player) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + player.replace(" ", "_")).openConnection().getInputStream()));
        return new JSONObject(bufferedReader.readLine()).getString("id");
    }

    public static @NotNull String getPlayerTextures(String uuid) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openConnection().getInputStream()));
        var sb = new StringBuilder();
        while (bufferedReader.ready()) {
            sb.append(bufferedReader.readLine());
        }
        var data = new JSONObject(sb.toString()).getJSONArray("properties").get(0).toString();
        return new JSONObject(data).getString("value");
    }

    private static String formatPermissionsKey(@NotNull PermissionsKeys permission) {
        return permission.toString().toLowerCase().replace("_", ".");
    }

    private DarkPlayerUtils() {}
}

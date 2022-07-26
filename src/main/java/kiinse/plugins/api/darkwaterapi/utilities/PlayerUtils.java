package kiinse.plugins.api.darkwaterapi.utilities;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.utilities.interfaces.PermissionsKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerUtils {

    public static boolean isActing(Player player) {
        return isJumping(player) || player.isSprinting() || player.isInLava() || isClimbing(player);
    }

    public static boolean isWalking(Player player) {
        var value = DarkWaterAPI.getInstance().getMoveSchedule().getNotMovingMap().get(player.getUniqueId());
        return value != null && value <= 10;
    }

    public static boolean isJumping(Player player) {
        return DarkWaterAPI.getInstance().getJumpSchedule().getJumpingMap().containsKey(player.getUniqueId());
    }

    public static boolean isClimbing(Player player) {
        return player.isClimbing() && isWalking(player);
    }

    public static boolean isSurvivalAdventure(Player player) {
        var gameMode = player.getGameMode();
        return gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE;
    }

    public static boolean isSurvivalAdventure(CommandSender sender) {
        var gameMode = getPlayer(sender).getGameMode();
        return gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE;
    }

    public static boolean isCreativeSpectator(Player player) {
        var gameMode = player.getGameMode();
        return gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR;
    }

    public static boolean isCreativeSpectator(CommandSender sender) {
        var gameMode = getPlayer(sender).getGameMode();
        return gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR;
    }

    public static boolean isSurvival(Player player) {
        return player.getGameMode() == GameMode.SURVIVAL;
    }

    public static boolean isSurvival(CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.SURVIVAL;
    }

    public static boolean isAdventure(Player player) {
        return player.getGameMode() == GameMode.ADVENTURE;
    }

    public static boolean isAdventure(CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.ADVENTURE;
    }

    public static boolean isCreative(Player player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }

    public static boolean isCreative(CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.CREATIVE;
    }

    public static boolean isSpectator(Player player) {
        return player.getGameMode() == GameMode.SPECTATOR;
    }

    public static boolean isSpectator(CommandSender sender) {
        return getPlayer(sender).getGameMode() == GameMode.SPECTATOR;
    }

    public static boolean isPoisoned(Player player) {
        return player.hasPotionEffect(PotionEffectType.POISON);
    }

    public static boolean isPoisoned(CommandSender sender) {
        return getPlayer(sender).hasPotionEffect(PotionEffectType.POISON);
    }

    public static void playSound(CommandSender sender, Sound sound, float v, float v1) {
        if (sender instanceof ConsoleCommandSender) {
            return;
        }
        var player = getPlayer(sender);
        player.playSound(player, sound, v,  v1);
    }

    public static void playSound(Player player, Sound sound, float v, float v1) {
        player.playSound(player, sound, v,  v1);
    }

    public static void playSound(CommandSender sender, Sound sound, float v) {
        if (sender instanceof ConsoleCommandSender) {
            return;
        }
        var player = getPlayer(sender);
        player.playSound(player, sound, v, 1f);
    }

    public static void playSound(Player player, Sound sound, float v) {
        player.playSound(player, sound, v,  1f);
    }

    public static void playSound(CommandSender sender, Sound sound) {
        if (sender instanceof ConsoleCommandSender) {
            return;
        }
        var player = getPlayer(sender);
        player.playSound(player, sound, 1f,  1f);
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player, sound, 1f,  1f);
    }

    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    public static Player getPlayer(PlayerEvent event) {
        return event.getPlayer();
    }

    public static Player getPlayer(CommandSender sender) {
        return (Player) sender;
    }

    public static String getPlayerName(Player player) {
        return ((TextComponent) player.displayName()).content();
    }

    public static String getPlayerName(CommandSender sender) {
        return ((TextComponent) getPlayer(sender).displayName()).content();
    }

    public static void sendActionBar(Player player, String message) {
        player.sendActionBar(Component.text(message));
    }

    public static void sendActionBar(Player player, Component message) {
        player.sendActionBar(message);
    }

    public static void sendActionBar(CommandSender sender, String message) {
        sender.sendActionBar(Component.text(message));
    }

    public static void sendActionBar(CommandSender sender, Component message) {
        sender.sendActionBar(message);
    }

    public static boolean hasPermission(CommandSender sender, PermissionsKeys permission) {
        return sender.hasPermission(formatPermissionsKey(permission));
    }

    public static boolean hasPermission(Player player, PermissionsKeys permission) {
        return player.hasPermission(formatPermissionsKey(permission));
    }

    public static boolean hasPermission(CommandSender sender, Permission permission) {
        return sender.hasPermission(permission);
    }

    public static boolean hasPermission(Player player, Permission permission) {
        return player.hasPermission(permission);
    }

    public static void giveItem(Player player, ItemStack item) {
        player.getInventory().addItem(item);
    }

    public static void giveItem(CommandSender sender, ItemStack item) {
        getPlayer(sender).getInventory().addItem(item);
    }

    private static String formatPermissionsKey(PermissionsKeys permission) {
        return permission.toString().toLowerCase().replace("_", ".");
    }

    private PlayerUtils() {}
}

package kiinse.plugins.api.darkwaterapi.commands.darkwaterapi;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandClass;
import kiinse.plugins.api.darkwaterapi.files.messages.SendMessagesImpl;
import kiinse.plugins.api.darkwaterapi.files.messages.interfaces.SendMessages;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Replace;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkPluginManager;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class DarkWaterCommands implements CommandClass {

    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final DarkPluginManager pluginManager = darkWaterAPI.getPluginManager();
    private final SendMessages sendMessages = new SendMessagesImpl(darkWaterAPI);

    @Override
    @Command(command = "/darkwater reload", permission = "darkwater.reload", parameters = 1)
    public void mainCommand(CommandSender sender, String[] args) {
        if (hasPlugin(sender, args[0])) {
            try {
                pluginManager.reloadPlugin(args[0]);
                sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_RELOADED, Replace.PLUGIN, args[0]);
                PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
            } catch (Exception e) {
                sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_ERROR);
                darkWaterAPI.sendLog(Level.SEVERE, "Error on plugin '" + args[0] + "' reload! Message: " + e.getMessage());
            }
        }
    }

    @Command(command = "/darkwater enable", permission = "darkwater.enable", parameters = 1)
    public void enable(CommandSender sender, String[] args) {
        if (hasPlugin(sender, args[0])) {
            try {
                pluginManager.enablePlugin(args[0]);
                sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_ENABLED, Replace.PLUGIN, args[0]);
                PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
            } catch (Exception e) {
                sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_ERROR);
                darkWaterAPI.sendLog(Level.SEVERE, "Error on plugin '" + args[0] + "' enable! Message: " + e.getMessage());
            }
        }
    }

    @Command(command = "/darkwater disable", permission = "darkwater.disable", parameters = 1)
    public void disable(CommandSender sender, String[] args) {
        if (hasPlugin(sender, args[0])) {
            try {
                pluginManager.disablePlugin(args[0]);
                sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_DISABLED, Replace.PLUGIN, args[0]);
                PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
            } catch (Exception e) {
                sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_ERROR);
                darkWaterAPI.sendLog(Level.SEVERE, "Error on plugin '" + args[0] + "' disable! Message: " + e.getMessage());
            }
        }
    }

    private boolean hasPlugin(CommandSender sender, String plugin) {
        if (!pluginManager.hasPlugin(plugin)) {
            sendMessages.sendMessageWithPrefix(sender, Message.PLUGIN_NOT_FOUND, Replace.PLUGIN, plugin);
            PlayerUtils.playSound(sender, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE);
            return false;
        }
        return true;
    }
}

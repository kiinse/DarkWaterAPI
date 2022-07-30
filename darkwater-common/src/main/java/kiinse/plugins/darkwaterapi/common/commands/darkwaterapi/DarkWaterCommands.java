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

package kiinse.plugins.darkwaterapi.common.commands.darkwaterapi;

import kiinse.plugins.darkwaterapi.api.commands.Command;
import kiinse.plugins.darkwaterapi.api.commands.CommandClass;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.api.loader.DarkPluginManager;
import kiinse.plugins.darkwaterapi.common.DarkWaterAPI;
import kiinse.plugins.darkwaterapi.common.files.Replace;
import kiinse.plugins.darkwaterapi.core.utilities.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

@SuppressWarnings("unused")
public class DarkWaterCommands implements CommandClass {

    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final DarkPluginManager pluginManager = darkWaterAPI.getPluginManager();
    private final MessagesUtils messagesUtils = darkWaterAPI.getMessagesUtils(darkWaterAPI);

    @Override
    @Command(command = "/darkwater reload", permission = "darkwater.reload", parameters = 1)
    public void mainCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (hasPlugin(sender, args[0])) {
            try {
                pluginManager.reloadPlugin(args[0]);
                messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_RELOADED, Replace.PLUGIN, args[0]);
                PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
            } catch (Exception e) {
                messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_ERROR);
                darkWaterAPI.sendLog(Level.SEVERE, "Error on plugin '" + args[0] + "' reload! Message: " + e.getMessage());
            }
        }
    }

    @Command(command = "/darkwater enable", permission = "darkwater.enable", parameters = 1)
    public void enable(@NotNull CommandSender sender, @NotNull String[] args) {
        if (hasPlugin(sender, args[0])) {
            try {
                pluginManager.enablePlugin(args[0]);
                messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_ENABLED, Replace.PLUGIN, args[0]);
                PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
            } catch (Exception e) {
                messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_ERROR);
                darkWaterAPI.sendLog(Level.SEVERE, "Error on plugin '" + args[0] + "' enable! Message: " + e.getMessage());
            }
        }
    }

    @Command(command = "/darkwater disable", permission = "darkwater.disable", parameters = 1)
    public void disable(@NotNull CommandSender sender, @NotNull String[] args) {
        if (hasPlugin(sender, args[0])) {
            try {
                pluginManager.disablePlugin(args[0]);
                messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_DISABLED, Replace.PLUGIN, args[0]);
                PlayerUtils.playSound(sender, Sound.BLOCK_AMETHYST_BLOCK_HIT);
            } catch (Exception e) {
                messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_ERROR);
                darkWaterAPI.sendLog(Level.SEVERE, "Error on plugin '" + args[0] + "' disable! Message: " + e.getMessage());
            }
        }
    }

    private boolean hasPlugin(@NotNull CommandSender sender, @NotNull String plugin) {
        if (!pluginManager.hasPlugin(plugin)) {
            messagesUtils.sendMessageWithPrefix(sender, Message.PLUGIN_NOT_FOUND, Replace.PLUGIN, plugin);
            PlayerUtils.playSound(sender, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE);
            return false;
        }
        return true;
    }
}

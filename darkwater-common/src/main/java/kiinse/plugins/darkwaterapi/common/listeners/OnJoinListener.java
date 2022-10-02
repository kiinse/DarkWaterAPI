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

package kiinse.plugins.darkwaterapi.common.listeners;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.files.enums.Config;
import kiinse.plugins.darkwaterapi.api.files.filemanager.YamlFile;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocales;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.darkwaterapi.common.files.Replace;
import kiinse.plugins.darkwaterapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class OnJoinListener implements Listener {

    private final DarkWaterJavaPlugin plugin;
    private final PlayerLocales locales;
    private final YamlFile config;
    private final MessagesUtils messagesUtils;

    public OnJoinListener(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
        messagesUtils = new DarkMessagesUtils(plugin);
        this.locales = plugin.getDarkWaterAPI().getPlayerLocales();
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> setLocales(event.getPlayer()), 200);
    }

    private void setLocales(@NotNull Player player) {
        var interfaceLocale = locales.getInterfaceLocale(player);
        if (!locales.isLocalized(player)) {
            locales.setLocale(player, interfaceLocale);
            if (config.getBoolean(Config.FIRST_JOIN_MESSAGE)) {
                messagesUtils.sendMessage(player, Message.FIRST_JOIN, Replace.LOCALE, interfaceLocale.toString());
                DarkPlayerUtils.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            plugin.sendLog(Level.CONFIG,
                           "The player &d" + DarkPlayerUtils.getPlayerName(player) + "&6 has been added to the plugin. His language is defined as " + interfaceLocale);
        }
        plugin.sendLog(Level.CONFIG, "Player &d" + DarkPlayerUtils.getPlayerName(player) + "&6 joined. His locale is " + locales.getLocale(player));
    }
}

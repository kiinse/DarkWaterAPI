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
package kiinse.me.plugins.darkwaterapi.common.listeners

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.filemanager.YamlFile
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocales
import kiinse.me.plugins.darkwaterapi.api.files.messages.MessagesUtils
import kiinse.me.plugins.darkwaterapi.api.files.messages.Message
import kiinse.me.plugins.darkwaterapi.api.files.enums.Config
import kiinse.me.plugins.darkwaterapi.core.files.messages.DarkMessagesUtils
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkPlayerUtils
import kiinse.me.plugins.darkwaterapi.common.files.Replace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import java.util.logging.Level

class OnJoinListener(private val plugin: DarkWaterJavaPlugin) : Listener {
    
    private val locales: PlayerLocales = plugin.darkWaterAPI.playerLocales
    private val config: YamlFile = plugin.getConfiguration()
    private val messagesUtils: MessagesUtils = DarkMessagesUtils(plugin)

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable { setLocales(event.player) }, 200)
    }

    private fun setLocales(player: Player) {
        val interfaceLocale= locales.getInterfaceLocale(player)
        if (!locales.isLocalized(player)) {
            locales.setLocale(player, interfaceLocale)
            if (config.getBoolean(Config.FIRST_JOIN_MESSAGE)) {
                messagesUtils.sendMessage(player, Message.FIRST_JOIN, Replace.LOCALE, interfaceLocale.toString())
                DarkPlayerUtils.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
            }
            plugin.sendLog(Level.CONFIG, "The player &d${DarkPlayerUtils.getPlayerName(player)}&6 has been added to the plugin. His language is defined as $interfaceLocale")
        }
        plugin.sendLog(Level.CONFIG, "Player &d${DarkPlayerUtils.getPlayerName(player)}&6 joined. His locale is ${locales.getLocale(player)}")
    }
}
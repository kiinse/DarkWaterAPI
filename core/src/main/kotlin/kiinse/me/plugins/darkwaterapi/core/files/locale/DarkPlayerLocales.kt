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
package kiinse.me.plugins.darkwaterapi.core.files.locale

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.locale.LocaleStorage
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocales
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkPlayerUtils
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.logging.Level

class DarkPlayerLocales(plugin: DarkWaterJavaPlugin, storage: LocaleStorage) : PlayerLocales {

    private val plugin: DarkWaterJavaPlugin
    private val storage: LocaleStorage

    init {
        this.plugin = plugin
        this.storage = storage
    }

    override fun isLocalized(player: Player): Boolean {
        return storage.isLocalesDataContains(player)
    }

    override fun getLocale(player: Player): PlayerLocale {
        return if (isLocalized(player)) storage.getLocalesData(player)!! else storage.defaultLocale
    }

    override fun getLocale(sender: CommandSender): PlayerLocale {
        if (sender is ConsoleCommandSender) return storage.defaultLocale
        val player = DarkPlayerUtils.getPlayer(sender)
        return if (isLocalized(player)) storage.getLocalesData(player)!! else storage.defaultLocale
    }

    override fun convertStringToLocale(locale: String): PlayerLocale {
        return Locale(locale)
    }

    override fun setLocale(player: Player, playerLocale: PlayerLocale) {
        if (isLocalized(player) && storage.removeLocalesData(player))
            plugin.sendLog(Level.CONFIG, "Player '${DarkPlayerUtils.getPlayerName(player)}' locale has been removed")
        if (storage.putInLocalesData(player, if (storage.isAllowedLocale(playerLocale)) playerLocale else storage.defaultLocale))
            plugin.sendLog(Level.CONFIG, "Player '${DarkPlayerUtils.getPlayerName(player)}' locale has been added. Locale: $playerLocale")
    }

    override fun getInterfaceLocale(player: Player): PlayerLocale {
        return convertStringToLocale(player.locale.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
    }
}
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
package kiinse.me.plugins.darkwaterapi.common.commands.locale

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.locale.LocaleStorage
import kiinse.me.plugins.darkwaterapi.common.utilities.Permission
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkPlayerUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*

class LocaleTab(plugin: DarkWaterJavaPlugin) : TabCompleter {

    private val storage: LocaleStorage = plugin.darkWaterAPI.localeStorage

    override fun onTabComplete(sender: CommandSender, cmd: Command, commandLabel: String, args: Array<String>): List<String> {
        val list = ArrayList<String>()
        if (sender is Player && cmd.name.equals("locale", ignoreCase = true)) {
            if (args.size == 1) {
                if (DarkPlayerUtils.hasPermission(sender, Permission.LOCALE_HELP)) list.add("help")
                if (DarkPlayerUtils.hasPermission(sender, Permission.LOCALE_LIST)) list.add("list")
                if (DarkPlayerUtils.hasPermission(sender, Permission.LOCALE_GET)) list.add("get")
                if (DarkPlayerUtils.hasPermission(sender, Permission.LOCALE_CHANGE)) {
                    list.add("change")
                    list.add("set")
                }
            } else if (args.size == 2) {
                if (args[0] == "set" && DarkPlayerUtils.hasPermission(sender, Permission.LOCALE_CHANGE))
                    list.addAll(storage.allowedLocalesListString)
                if (args[0] == "get" && DarkPlayerUtils.hasPermission(sender, Permission.LOCALE_GET))
                    Bukkit.getOnlinePlayers().forEach{ list.add(DarkPlayerUtils.getPlayerName(it)) }
            }
            list.sort()
        }
        return list
    }
}
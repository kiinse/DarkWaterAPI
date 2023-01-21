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
package kiinse.me.plugins.darkwaterapi.common.commands.darkwaterapi

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.loader.PluginManager
import kiinse.me.plugins.darkwaterapi.common.utilities.Permission
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkPlayerUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*

class DarkWaterTab(plugin: DarkWaterJavaPlugin) : TabCompleter {

    private val pluginManager: PluginManager = plugin.darkWaterAPI.pluginManager

    override fun onTabComplete(sender: CommandSender, cmd: Command, commandLabel: String, args: Array<String>): List<String> {
        val list = ArrayList<String>()
        if (sender is Player && cmd.name.equals("darkwater", ignoreCase = true)) {
            if (args.size == 1) {
                if (DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_RELOAD)) {
                    list.add("reload")
                }
                if (DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_DISABLE)) {
                    list.add("disable")
                }
                if (DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_ENABLE)) {
                    list.add("enable")
                }
            } else if (args.size == 2 && hasSenderPermissionsToPluginList(sender)) {
                pluginManager.pluginsList.forEach {
                    list.add(it.name)
                }
            }
            list.sort()
        }
        return list
    }

    private fun hasSenderPermissionsToPluginList(sender: CommandSender): Boolean {
        return DarkPlayerUtils.hasOneOfPermissions(sender, arrayOf(Permission.DARKWATER_RELOAD, Permission.DARKWATER_DISABLE, Permission.DARKWATER_ENABLE))
    }
}
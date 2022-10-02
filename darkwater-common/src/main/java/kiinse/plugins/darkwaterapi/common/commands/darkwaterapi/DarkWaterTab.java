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

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.loader.PluginManager;
import kiinse.plugins.darkwaterapi.common.utilities.Permission;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DarkWaterTab implements TabCompleter {

    private final PluginManager pluginManager;

    public DarkWaterTab(@NotNull DarkWaterJavaPlugin plugin) {
        this.pluginManager = plugin.getDarkWaterAPI().getPluginManager();
    }

    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel,
                                               @NotNull String[] args) {
        var list = new ArrayList<String>();
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("darkwater")) {
            if (args.length == 1) {
                if (DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_RELOAD)) {
                    list.add("reload");
                }
                if (DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_DISABLE)) {
                    list.add("disable");
                }
                if (DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_ENABLE)) {
                    list.add("enable");
                }
            } else if (args.length == 2 && hasSenderPermissionsToPluginList(sender)) {
                for (var plugin : pluginManager.getPluginsList()) {
                    list.add(plugin.getName());
                }
            }
            Collections.sort(list);
        }
        return list;
    }

    private boolean hasSenderPermissionsToPluginList(@NotNull CommandSender sender) {
        return DarkPlayerUtils.hasPermission(sender, Permission.DARKWATER_RELOAD) || DarkPlayerUtils.hasPermission(sender,
                                                                                                                   Permission.DARKWATER_DISABLE) || DarkPlayerUtils.hasPermission(
                sender,
                Permission.DARKWATER_ENABLE);
    }
}
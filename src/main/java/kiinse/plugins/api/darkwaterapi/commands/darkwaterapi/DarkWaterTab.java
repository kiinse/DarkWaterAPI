package kiinse.plugins.api.darkwaterapi.commands.darkwaterapi;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkPluginManager;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import kiinse.plugins.api.darkwaterapi.utilities.enums.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DarkWaterTab implements TabCompleter {

    private final DarkPluginManager pluginManager = DarkWaterAPI.getInstance().getPluginManager();

    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, @NotNull String[] args) {
        var list = new ArrayList<String>();
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("darkwater")) {
            if (args.length == 1) {
                if (PlayerUtils.hasPermission(sender, Permission.DARKWATER_RELOAD)) {
                    list.add("reload");
                }
                if (PlayerUtils.hasPermission(sender, Permission.DARKWATER_DISABLE)) {
                    list.add("disable");
                }
                if (PlayerUtils.hasPermission(sender, Permission.DARKWATER_ENABLE)) {
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
        return PlayerUtils.hasPermission(sender, Permission.DARKWATER_RELOAD) || PlayerUtils.hasPermission(sender, Permission.DARKWATER_DISABLE) || PlayerUtils.hasPermission(sender, Permission.DARKWATER_ENABLE);
    }
}
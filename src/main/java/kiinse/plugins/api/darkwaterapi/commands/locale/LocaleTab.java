package kiinse.plugins.api.darkwaterapi.commands.locale;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import kiinse.plugins.api.darkwaterapi.utilities.utils.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocaleTab implements TabCompleter {

    private final LocaleStorage storage = DarkWaterAPI.getInstance().getLocaleStorage();

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        var list = new ArrayList<String>();
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("locale")) {
            if (args.length == 1) {
                if (PlayerUtils.hasPermission(sender, Permission.LOCALE_HELP)) {
                    list.add("help");
                }
                if (PlayerUtils.hasPermission(sender, Permission.LOCALE_LIST)) {
                    list.add("list");
                }
                if (PlayerUtils.hasPermission(sender, Permission.LOCALE_GET)) {
                    list.add("get");
                }
                if (PlayerUtils.hasPermission(sender, Permission.LOCALE_CHANGE)) {
                    list.add("change");
                    list.add("set");
                }
            } else if (args.length == 2) {
                if (args[0].equals("set") && PlayerUtils.hasPermission(sender, Permission.LOCALE_CHANGE)) {
                    list.addAll(storage.getAllowedLocalesListString());
                }
                if (args[0].equals("get") && PlayerUtils.hasPermission(sender, Permission.LOCALE_GET)) {
                    for (var player : Bukkit.getOnlinePlayers()) {
                        list.add(PlayerUtils.getPlayerName(player));
                    }
                }
            }
            Collections.sort(list);
        }
        return list;
    }
}

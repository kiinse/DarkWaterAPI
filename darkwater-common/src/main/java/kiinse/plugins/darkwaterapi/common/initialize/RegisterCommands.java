package kiinse.plugins.darkwaterapi.common.initialize;

import kiinse.plugins.darkwaterapi.api.exceptions.exceptions.CommandException;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.common.commands.darkwaterapi.DarkWaterCommands;
import kiinse.plugins.darkwaterapi.common.commands.darkwaterapi.DarkWaterTab;
import kiinse.plugins.darkwaterapi.common.commands.locale.LocaleCommands;
import kiinse.plugins.darkwaterapi.common.commands.locale.LocaleTab;
import kiinse.plugins.darkwaterapi.core.commands.CommandManager;
import kiinse.plugins.darkwaterapi.common.commands.statistic.StatisticCommands;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterCommands {

    public RegisterCommands(@NotNull DarkWaterJavaPlugin plugin) throws NullPointerException, CommandException {
        plugin.sendLog("Registering commands...");
        new CommandManager(plugin)
                .registerCommands(new LocaleCommands())
                .registerCommands(new DarkWaterCommands())
                .registerCommands(new StatisticCommands());
        Objects.requireNonNull(plugin.getCommand("locale")).setTabCompleter(new LocaleTab());
        Objects.requireNonNull(plugin.getCommand("darkwater")).setTabCompleter(new DarkWaterTab());
        plugin.sendLog("Commands registered");
    }
}

package kiinse.plugins.api.darkwaterapi.initialize;

import kiinse.plugins.api.darkwaterapi.commands.darkwaterapi.DarkWaterCommands;
import kiinse.plugins.api.darkwaterapi.commands.darkwaterapi.DarkWaterTab;
import kiinse.plugins.api.darkwaterapi.commands.locale.LocaleCommands;
import kiinse.plugins.api.darkwaterapi.commands.locale.LocaleTab;
import kiinse.plugins.api.darkwaterapi.commands.manager.CommandException;
import kiinse.plugins.api.darkwaterapi.commands.manager.CommandManager;
import kiinse.plugins.api.darkwaterapi.commands.statistic.StatisticCommands;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterCommands {

    public RegisterCommands(@NotNull DarkWaterJavaPlugin plugin) throws NullPointerException, CommandException {
        plugin.sendLog("Registering commands...");
        var commandManager = new CommandManager(plugin);
        commandManager.registerCommands(new LocaleCommands());
        commandManager.registerCommands(new DarkWaterCommands());
        commandManager.registerCommands(new StatisticCommands());
        Objects.requireNonNull(plugin.getCommand("locale")).setTabCompleter(new LocaleTab());
        Objects.requireNonNull(plugin.getCommand("darkwater")).setTabCompleter(new DarkWaterTab());
        plugin.sendLog("Commands registered");
    }
}

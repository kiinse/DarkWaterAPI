package kiinse.me.plugins.darkwaterapi.common.initialize

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.common.commands.darkwaterapi.DarkWaterCommands
import kiinse.me.plugins.darkwaterapi.common.commands.darkwaterapi.DarkWaterTab
import kiinse.me.plugins.darkwaterapi.common.commands.locale.LocaleCommands
import kiinse.me.plugins.darkwaterapi.common.commands.locale.LocaleTab
import kiinse.me.plugins.darkwaterapi.common.commands.statistic.StatisticCommands
import kiinse.me.plugins.darkwaterapi.core.commands.CommandManager

class RegisterCommands(plugin: DarkWaterJavaPlugin) {
    init {
        plugin.sendLog("Registering commands...")
        CommandManager(plugin)
                .registerCommand(LocaleCommands(plugin))
                .registerCommand(DarkWaterCommands(plugin))
                .registerCommand(StatisticCommands(plugin))
        plugin.getCommand("locale")!!.tabCompleter = LocaleTab(plugin)
        plugin.getCommand("darkwater")!!.tabCompleter = DarkWaterTab(plugin)
        plugin.sendLog("Commands registered")
    }
}
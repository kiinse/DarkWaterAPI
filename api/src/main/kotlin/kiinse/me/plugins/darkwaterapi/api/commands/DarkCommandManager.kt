package kiinse.me.plugins.darkwaterapi.api.commands

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.CommandException
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.entity.Player
import java.lang.reflect.Method
import java.util.logging.Level

@Suppress("UNUSED")
abstract class DarkCommandManager : CommandExecutor {
    protected val plugin: DarkWaterJavaPlugin
    protected val failureHandler: CommandFailureHandler
    protected val registeredSubCommandTable: MutableMap<String, RegisteredCommand> = HashMap()
    protected val registeredMainCommandTable: MutableMap<String, RegisteredCommand> = HashMap()
    protected val mainCommandTable: MutableMap<DarkCommand, String> = HashMap()

    protected constructor(plugin: DarkWaterJavaPlugin) {
        this.plugin = plugin
        failureHandler = plugin.darkWaterAPI.commandFailureHandler
    }

    protected constructor(plugin: DarkWaterJavaPlugin, failureHandler: CommandFailureHandler) {
        this.plugin = plugin
        this.failureHandler = failureHandler
    }

    /**
     * Registration class commands
     *
     * @param commandClass A class that inherits from CommandClass and contains command methods
     */
    @Throws(CommandException::class)
    abstract fun registerCommand(commandClass: DarkCommand): DarkCommandManager

    @Throws(CommandException::class)
    protected fun registerMainCommand(commandClass: DarkCommand, method: Method): String {
        val mainCommand = method.getAnnotation(Command::class.java)
        val command = mainCommand.command
        register(commandClass, method, plugin.server.getPluginCommand(command), command, mainCommand, true)
        return command
    }

    @Throws(CommandException::class)
    protected fun registerMainCommand(darkCommand: DarkCommand): String {
        val mainCommand = darkCommand.javaClass.getAnnotation(Command::class.java)
        val command = mainCommand.command
        register(darkCommand, plugin.server.getPluginCommand(command), mainCommand)
        return command
    }

    @Throws(CommandException::class)
    protected fun registerSubCommand(commandClass: DarkCommand, method: Method) {
        val annotation = method.getAnnotation(SubCommand::class.java)
        val mainCommand = mainCommandTable[commandClass]
        if (annotation != null && annotation.command != mainCommand) {
            val cmd = mainCommand + " " + annotation.command
            register(commandClass, method, plugin.server.getPluginCommand(cmd), cmd, annotation, false)
        }
    }

    @Throws(CommandException::class)
    protected fun register(commandClass: DarkCommand, method: Method, pluginCommand: PluginCommand?,
                           command: String, annotation: Any, isMainCommand: Boolean) {
        register(pluginCommand, command)
        (if (isMainCommand) registeredMainCommandTable else registeredSubCommandTable)[command] = object : RegisteredCommand(method, commandClass, annotation) {}
        plugin.sendLog(Level.CONFIG, "Command '&d$command&6' registered!")
    }

    @Throws(CommandException::class)
    protected fun register(commandClass: DarkCommand, pluginCommand: PluginCommand?, annotation: Command) {
        val command = annotation.command
        register(pluginCommand, command)
        registeredMainCommandTable[command] = object : RegisteredCommand(null, commandClass, annotation) {}
    }

    @Throws(CommandException::class)
    protected fun register(pluginCommand: PluginCommand?, command: String) {
        if (registeredSubCommandTable.containsKey(command) || registeredMainCommandTable.containsKey(command)) throw CommandException("Command '$command' already registered!")
        if (pluginCommand == null) throw CommandException("Unable to register command command '$command'. Did you put it in plugin.yml?")
        pluginCommand.setExecutor(this)
    }

    @Throws(CommandException::class)
    protected fun getMainCommandMethod(darkCommand: Class<out DarkCommand?>): Method {
        darkCommand.methods.forEach {
            if (it.getAnnotation(Command::class.java) != null) return it
        }
        val name = darkCommand.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        throw CommandException("Main command in class '${name[name.size - 1]}' not found!")
    }

    protected fun isDisAllowNonPlayer(wrapper: RegisteredCommand, sender: CommandSender, disAllowNonPlayer: Boolean): Boolean {
        if (sender !is Player && disAllowNonPlayer) {
            failureHandler.handleFailure(CommandFailReason.NOT_PLAYER, sender, wrapper)
            return true
        }
        return false
    }

    protected fun hasNotPermissions(wrapper: RegisteredCommand, sender: CommandSender, permission: String): Boolean {
        if (permission != "" && !sender.hasPermission(permission)) {
            failureHandler.handleFailure(CommandFailReason.NO_PERMISSION, sender, wrapper)
            return true
        }
        return false
    }

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<String>): Boolean {
        return onExecute(sender, command, label, args)
    }

    protected abstract fun onExecute(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<String>): Boolean
}
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
package kiinse.me.plugins.darkwaterapi.core.commands

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.commands.*
import kiinse.me.plugins.darkwaterapi.api.exceptions.CommandException
import org.bukkit.command.CommandSender
import java.util.*

@Suppress("unused")
open class CommandManager : DarkCommandManager {

    constructor(plugin: DarkWaterJavaPlugin) : super(plugin)
    constructor(plugin: DarkWaterJavaPlugin, failureHandler: CommandFailureHandler) : super(plugin, failureHandler)

    @Throws(CommandException::class)
    override fun registerCommand(commandClass: DarkCommand): DarkCommandManager {
        val clazz = commandClass.javaClass
        mainCommandTable[commandClass] = if (clazz.getAnnotation(Command::class.java) != null)
            registerMainCommand(commandClass) else registerMainCommand(commandClass, getMainCommandMethod(clazz))
        clazz.methods.forEach { registerSubCommand(commandClass, it) }
        return this
    }

    override fun onExecute(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<String>): Boolean {
        return if (args.isEmpty()) execute(sender, executeMainCommand(sender, command, args)) else execute(sender, executeSubCommand(sender, command, args))
    }

    private fun execute(sender: CommandSender, value: Boolean): Boolean {
        if (!value) failureHandler.handleFailure(CommandFailReason.COMMAND_NOT_FOUND, sender, null)
        return true
    }

    private fun executeMainCommand(sender: CommandSender, command: org.bukkit.command.Command, args: Array<String>): Boolean {
        registeredMainCommandTable.forEach { (_, registeredCommand) ->
            if (registeredCommand.method != null) {
                val annotation: Command? = registeredCommand.annotation as Command?
                if (annotation != null && annotation.command.equals(command.name, ignoreCase = true)) {
                    if (isDisAllowNonPlayer(registeredCommand, sender, annotation.disallowNonPlayer)
                        || hasNotPermissions(registeredCommand, sender, annotation.permission)) return true
                    invokeWrapper(registeredCommand, sender, args)
                    return true
                }
            }
        }
        return false
    }

    private fun executeSubCommand(sender: CommandSender, command: org.bukkit.command.Command, args: Array<String>): Boolean {
        val sb = StringBuilder(command.name.lowercase(Locale.getDefault()))
        args.forEach {
            sb.append(" ").append(it.lowercase(Locale.getDefault()))
            registeredSubCommandTable.forEach { (str, registeredCommand) ->
                if (str == sb.toString()) {
                    val annotation: SubCommand? = registeredCommand.annotation as SubCommand?
                    if (annotation != null) {
                        val actualParams = args.copyOfRange(annotation.command.split(" ").size, args.size)
                        if (isDisAllowNonPlayer(registeredCommand, sender, annotation.disallowNonPlayer)
                            || hasNotPermissions(registeredCommand, sender, annotation.permission)) return true
                        if (actualParams.size != annotation.parameters && !annotation.overrideParameterLimit) {
                            if (actualParams.size > annotation.parameters)
                                failureHandler.handleFailure(CommandFailReason.REDUNDANT_PARAMETER, sender, registeredCommand)
                            else
                                failureHandler.handleFailure(CommandFailReason.INSUFFICIENT_PARAMETER, sender, registeredCommand)
                            return true
                        }
                        invokeWrapper(registeredCommand, sender, actualParams)
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun invokeWrapper(wrapper: RegisteredCommand, sender: CommandSender, args: Array<String>) {
        try {
            wrapper.method?.invoke(wrapper.instance, DarkContext(sender, plugin.darkWaterAPI.playerLocales.getLocale(sender), args))
        } catch (e: Exception) {
            failureHandler.handleFailure(CommandFailReason.REFLECTION_ERROR, sender, wrapper)
            plugin.sendLog("Error on command usage! Message:", e)
        }
    }
}
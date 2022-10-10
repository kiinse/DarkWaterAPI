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

package kiinse.plugins.darkwaterapi.core.commands;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.commands.*;
import kiinse.plugins.darkwaterapi.api.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;

@SuppressWarnings({"unused"})
public class CommandManager extends DarkCommandManager {

    public CommandManager(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    public CommandManager(@NotNull DarkWaterJavaPlugin plugin, @NotNull CommandFailureHandler failureHandler) {
        super(plugin, failureHandler);
    }

    @Override
    public @NotNull DarkCommandManager registerCommand(@NotNull Object commandClass) throws CommandException {
        var clazz = commandClass.getClass();
        mainCommandTable.put(clazz, clazz.getAnnotation(Command.class) != null ?
                registerMainCommand(clazz) : registerMainCommand(clazz, getMainCommandMethod(clazz)));
        for (var method : clazz.getMethods()) registerSubCommand(clazz, method);
        return this;
    }

    @Override
    protected boolean onExecute(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label,
                                @NotNull String[] args) {
        if (args.length == 0) return execute(sender, executeMainCommand(sender, command, args));
        return execute(sender, executeSubCommand(sender, command, args));
    }

    private boolean execute(@NotNull CommandSender sender, boolean value) {
        if (!value) failureHandler.handleFailure(CommandFailReason.COMMAND_NOT_FOUND, sender, null);
        return true;
    }

    private boolean executeMainCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String[] args) {
        for (var usage : registeredMainCommandTable.entrySet()) {
            var wrapper = usage.getValue();
            if (wrapper.getMethod() != null) {
                var annotation = (Command) wrapper.getAnnotation();
                if (annotation != null && (annotation.command().equalsIgnoreCase(command.getName()) ||
                                           hasCommandInAliases(annotation.aliases(), command.getName()) )) {
                    if (isDisAllowNonPlayer(wrapper, sender, annotation.disallowNonPlayer())
                        || hasNotPermissions(wrapper, sender, annotation.permission())) return true;
                    invokeWrapper(wrapper, sender, args);
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: Проверить алиасы

    private boolean executeSubCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String[] args) {
        var sb = new StringBuilder(command.getName().toLowerCase());
        for (var argument : args) {
            sb.append(" ").append(argument.toLowerCase());
            for (var usage : registeredSubCommandTable.entrySet()) {
                if (usage.getKey().equals(sb.toString())
                    || hasCommandInAliases(((SubCommand) usage.getValue().getAnnotation()).aliases(), sb.toString())) {
                    var wrapper = usage.getValue();
                    var annotation = (SubCommand) wrapper.getAnnotation();
                    if (annotation != null) {
                        var actualParams = Arrays.copyOfRange(args, annotation.command().split(" ").length, args.length);
                        if (isDisAllowNonPlayer(wrapper, sender, annotation.disallowNonPlayer())
                            || hasNotPermissions(wrapper, sender, annotation.permission())) return true;
                        if (actualParams.length != annotation.parameters() && !annotation.overrideParameterLimit()) {
                            if (actualParams.length > annotation.parameters()) {
                                failureHandler.handleFailure(CommandFailReason.REDUNDANT_PARAMETER, sender, wrapper);
                            } else {
                                failureHandler.handleFailure(CommandFailReason.INSUFFICIENT_PARAMETER, sender, wrapper);
                            }
                            return true;
                        }
                        invokeWrapper(wrapper, sender, actualParams);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasCommandInAliases(@NotNull String[] aliases, @NotNull String command) {
        for (var alias : aliases) {
            if (alias.equalsIgnoreCase(command)) return true;
        }
        return false;
    }

    private void invokeWrapper(@NotNull RegisteredCommand wrapper, @NotNull CommandSender sender, @NotNull String[] args) {
        try {
            wrapper.getMethod().invoke(wrapper.getInstance(),
                                       new Context(sender, plugin.getDarkWaterAPI().getPlayerLocales().getLocale(sender), args));
        } catch (IllegalAccessException | InvocationTargetException e) {
            failureHandler.handleFailure(CommandFailReason.REFLECTION_ERROR, sender, wrapper);
            plugin.sendLog(Level.WARNING, "Error on command usage! Message: " + e.getMessage());
        }
    }
}
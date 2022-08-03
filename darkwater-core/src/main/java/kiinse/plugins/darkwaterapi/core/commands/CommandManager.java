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

import kiinse.plugins.darkwaterapi.api.commands.*;
import kiinse.plugins.darkwaterapi.api.exceptions.CommandException;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings({"unused"})
public class CommandManager implements CommandExecutor {

    private final DarkWaterJavaPlugin plugin;
    private final CommandFailureHandler failureHandler;
    private final Map<String, RegisteredCommand> registeredSubCommandTable = new HashMap<>();
    private final Map<String, RegisteredCommand> registeredMainCommandTable = new HashMap<>();
    private final Map<DarkCommand, String> mainCommandTable = new HashMap<>();

    /**
     * Commands manager
     * @param plugin Plugin {@link DarkWaterJavaPlugin}
     */
    public CommandManager(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
        failureHandler = plugin.getDarkWaterAPI().getCommandFailureHandler();
    }

    public CommandManager(@NotNull DarkWaterJavaPlugin plugin, @NotNull FailureHandler failureHandler) {
        this.plugin = plugin;
        this.failureHandler = failureHandler;
    }


    /**
     * Registration class commands
     * @param darkCommand A class that inherits from CommandClass and contains command methods {@link DarkCommand}
     */
    public @NotNull CommandManager registerCommand(@NotNull DarkCommand darkCommand) throws CommandException {
        if (isClassMainCommand(darkCommand)) {
            mainCommandTable.put(darkCommand, registerMainCommand(darkCommand));
        } else {
            mainCommandTable.put(darkCommand, registerMainCommand(darkCommand, getMainCommandMethod(darkCommand)));
        }
        for (var method : darkCommand.getClass().getMethods()) {
            registerSubCommand(darkCommand, method);
        }
        return this;
    }

    /**
     * Standard command handler from Bukkit
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            for (var usage : registeredMainCommandTable.entrySet()) {
                var wrapper = usage.getValue();
                if (wrapper.getMethod() != null) {
                    var annotation = (Command) wrapper.getAnnotation();
                    if (annotation != null && annotation.command().equalsIgnoreCase(command.getName())) {
                        if (isDisAllowNonPlayer(wrapper, sender, annotation.disallowNonPlayer())) {
                            return true;
                        }
                        if (hasNotPermissions(wrapper, sender, annotation.permission())) {
                            return true;
                        }
                        try {
                            wrapper.getMethod().invoke(wrapper.getInstance(), sender, args);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            failureHandler.handleFailure(CommandFailReason.REFLECTION_ERROR, sender, wrapper);
                            plugin.sendLog(Level.WARNING, "Error on command usage! Message: " + e.getMessage());
                        }
                        return true;
                    }
                }
            }
        } else {
            var sb = new StringBuilder(command.getName().toLowerCase());
            for (var argument : args) {
                sb.append(" ").append(argument.toLowerCase());
                for (var usage : registeredSubCommandTable.entrySet()) {
                    if (usage.getKey().equals(sb.toString())) {
                        var wrapper = usage.getValue();
                        var annotation = (SubCommand) wrapper.getAnnotation();
                        if (annotation != null) {
                            var actualParams = Arrays.copyOfRange(args, annotation.command().split(" ").length, args.length);
                            if (isDisAllowNonPlayer(wrapper, sender, annotation.disallowNonPlayer())) {
                                return true;
                            }
                            if (hasNotPermissions(wrapper, sender, annotation.permission())) {
                                return true;
                            }
                            if (actualParams.length != annotation.parameters() && !annotation.overrideParameterLimit()) {
                                if (actualParams.length > annotation.parameters()) {
                                    failureHandler.handleFailure(CommandFailReason.REDUNDANT_PARAMETER, sender, wrapper);
                                } else {
                                    failureHandler.handleFailure(CommandFailReason.INSUFFICIENT_PARAMETER, sender, wrapper);
                                }
                                return true;
                            }
                            try {
                                wrapper.getMethod().invoke(wrapper.getInstance(), sender, actualParams);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                failureHandler.handleFailure(CommandFailReason.REFLECTION_ERROR, sender, wrapper);
                                plugin.sendLog(Level.WARNING, "Error on command usage! Message: " + e.getMessage());
                            }
                            return true;
                        }
                    }
                }
            }
        }
        failureHandler.handleFailure(CommandFailReason.COMMAND_NOT_FOUND, sender, null);
        return true;
    }

    private @NotNull String registerMainCommand(@NotNull DarkCommand commandClass, @NotNull Method method) throws CommandException {
        var mainCommand = method.getAnnotation(Command.class);
        var command = mainCommand.command();
        register(commandClass, method, plugin.getServer().getPluginCommand(command), command, mainCommand, true);
        return command;
    }

    private @NotNull String registerMainCommand(@NotNull DarkCommand darkCommand) throws CommandException {
        var mainCommand = darkCommand.getClass().getAnnotation(Command.class);
        var command = mainCommand.command();
        register(darkCommand, plugin.getServer().getPluginCommand(command), mainCommand);
        return command;
    }

    private void registerSubCommand(@NotNull DarkCommand commandClass, @NotNull Method method) throws CommandException {
        var annotation = method.getAnnotation(SubCommand.class);
        var mainCommand = mainCommandTable.get(commandClass);
        if (annotation != null && !annotation.command().equals(mainCommand)) {
            var cmd = mainCommand + " " + annotation.command();
            register(commandClass, method, plugin.getServer().getPluginCommand(cmd), cmd, annotation, false);
        }
    }

    private void register(@NotNull DarkCommand commandClass, @NotNull Method method, @Nullable PluginCommand pluginCommand, @NotNull String command, @NotNull Object annotation, boolean isMainCommand) throws CommandException {
        register(pluginCommand, command);
        if (isMainCommand) {
            registeredMainCommandTable.put(command, new RegisteredCommand(method, commandClass, annotation));
        } else {
            registeredSubCommandTable.put(command, new RegisteredCommand(method, commandClass, annotation));
        }
        plugin.sendLog(Level.CONFIG, "Command '&d" + command + "&6' registered!");
    }

    private void register(@NotNull DarkCommand commandClass, @Nullable PluginCommand pluginCommand, @NotNull Command annotation) throws CommandException {
        var command = annotation.command();
        register(pluginCommand, command);
        registeredMainCommandTable.put(command, new RegisteredCommand(null, commandClass, annotation));
    }

    private void register(@Nullable PluginCommand pluginCommand, @NotNull String command)  throws CommandException {
        if (registeredSubCommandTable.containsKey(command) || registeredMainCommandTable.containsKey(command)) {
            throw new CommandException("Command '" + command + "' already registered!");
        }
        if (pluginCommand == null) {
            throw new CommandException("Unable to register command command '" + command + "'. Did you put it in plugin.yml?");
        }
        pluginCommand.setExecutor(this);
    }

    private @NotNull Method getMainCommandMethod(@NotNull DarkCommand darkCommand) throws CommandException {
        for (var method : darkCommand.getClass().getMethods()) {
            var mainAnnotation = method.getAnnotation(Command.class);
            if (mainAnnotation != null) {
                return method;
            }
        }
        var name = darkCommand.getClass().getName().split("\\.");
        throw new CommandException("Main command in class '" + name[name.length-1]  + "' not found!");
    }

    private boolean isClassMainCommand(@NotNull DarkCommand darkCommand) {
        var annotation = darkCommand.getClass().getAnnotation(Command.class);
        return annotation != null;
    }

    private boolean isDisAllowNonPlayer(@NotNull RegisteredCommand wrapper, @NotNull CommandSender sender, boolean disAllowNonPlayer) {
        if (!(sender instanceof Player) && disAllowNonPlayer) {
            failureHandler.handleFailure(CommandFailReason.NOT_PLAYER, sender, wrapper);
            return true;
        }
        return false;
    }

    private boolean hasNotPermissions(@NotNull RegisteredCommand wrapper, @NotNull CommandSender sender, String permission) {
        if (!permission.equals("") && !sender.hasPermission(permission)) {
            failureHandler.handleFailure(CommandFailReason.NO_PERMISSION, sender, wrapper);
            return true;
        }
        return false;
    }
}
package kiinse.plugins.api.darkwaterapi.commands.manager;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.commands.manager.annotation.Command;
import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandClass;
import kiinse.plugins.api.darkwaterapi.commands.manager.interfaces.CommandFailureHandler;
import kiinse.plugins.api.darkwaterapi.commands.manager.reasons.CommandFailReason;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CommandManager implements CommandExecutor {

    private final DarkWaterJavaPlugin plugin;
    private final CommandFailureHandler failureHandler;

    private final Map<String, RegisteredCommand> registeredCommandTable = new HashMap<>();

    /**
     * Commands manager
     * @param plugin Plugin {@link DarkWaterJavaPlugin}
     */
    public CommandManager(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
        this.failureHandler = new FailureHandler(DarkWaterAPI.getInstance());
    }

    /**
     * Registration class commands
     * @param commandClass A class that inherits from CommandClass and contains command methods {@link CommandClass}
     */
    public void registerCommands(@NotNull CommandClass commandClass) throws CommandException {
        for (var method : commandClass.getClass().getMethods()) {
            var annotation = method.getAnnotation(Command.class);
            if (annotation != null) {
                var command = annotation.command().split(" ")[0].substring(1);
                var pluginCommand = plugin.getServer().getPluginCommand(command);
                if (pluginCommand == null) {
                    throw new CommandException("Unable to register command command '" + command + "'. Did you put it in plugin.yml?");
                } else {
                    pluginCommand.setExecutor(this);
                    registeredCommandTable.put(annotation.command().substring(1), new RegisteredCommand(method, commandClass, annotation));
                    plugin.sendLog(Level.CONFIG, "Command '&d" + annotation.command().substring(1) + "&6' registered!");
                }
            }
        }
    }

    /**
     * Standard command handler from Bukkit
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        var sb = new StringBuilder();

        for (int i = -1; i <= args.length - 1; i++) {

            if (i == -1) {
                sb.append(command.getName().toLowerCase());
            } else {
                sb.append(" ").append(args[i].toLowerCase());
            }

            for (var usage : registeredCommandTable.entrySet()) {
                if (usage.getKey().equals(sb.toString())) {
                    var wrapper = usage.getValue();
                    var annotation = wrapper.getAnnotation();
                    var actualParams = Arrays.copyOfRange(args, annotation.command().split(" ").length - 1, args.length);
                    if (!(sender instanceof Player) && annotation.disallowNonPlayer()) {
                        failureHandler.handleFailure(CommandFailReason.NOT_PLAYER, sender, wrapper);
                        return true;
                    }
                    if (!annotation.permission().equals("") && !sender.hasPermission(annotation.permission())) {
                        failureHandler.handleFailure(CommandFailReason.NO_PERMISSION, sender, wrapper);
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
                        return true;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        failureHandler.handleFailure(CommandFailReason.REFLECTION_ERROR, sender, wrapper);
                        plugin.sendLog(Level.WARNING, "Error on command usage! Message: " + e.getMessage());
                    }
                    return true;
                }
            }
        }
        failureHandler.handleFailure(CommandFailReason.COMMAND_NOT_FOUND, sender, null);
        return true;
    }
}
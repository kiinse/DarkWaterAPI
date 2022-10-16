package kiinse.plugins.darkwaterapi.api.commands;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.exceptions.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class DarkCommandManager implements CommandExecutor {

    protected final DarkWaterJavaPlugin plugin;
    protected final CommandFailureHandler failureHandler;
    protected final Map<String, RegisteredCommand> registeredSubCommandTable = new HashMap<>();
    protected final Map<String, RegisteredCommand> registeredMainCommandTable = new HashMap<>();
    protected final Map<DarkCommand, String> mainCommandTable = new HashMap<>();

    protected DarkCommandManager(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
        failureHandler = plugin.getDarkWaterAPI().getCommandFailureHandler();
    }

    protected DarkCommandManager(@NotNull DarkWaterJavaPlugin plugin, @NotNull CommandFailureHandler failureHandler) {
        this.plugin = plugin;
        this.failureHandler = failureHandler;
    }

    /**
     * Registration class commands
     *
     * @param commandClass A class that inherits from CommandClass and contains command methods
     */
    public abstract @NotNull DarkCommandManager registerCommand(@NotNull DarkCommand commandClass) throws CommandException;

    protected @NotNull String registerMainCommand(@NotNull DarkCommand commandClass, @NotNull Method method) throws CommandException {
        var mainCommand = method.getAnnotation(Command.class);
        var command = mainCommand.command();
        register(commandClass, method, plugin.getServer().getPluginCommand(command), command, mainCommand, true);
        return command;
    }

    protected @NotNull String registerMainCommand(@NotNull DarkCommand darkCommand) throws CommandException {
        var mainCommand = darkCommand.getClass().getAnnotation(Command.class);
        var command = mainCommand.command();
        register(darkCommand, plugin.getServer().getPluginCommand(command), mainCommand);
        return command;
    }

    protected void registerSubCommand(@NotNull DarkCommand commandClass, @NotNull Method method) throws CommandException {
        var annotation = method.getAnnotation(SubCommand.class);
        var mainCommand = mainCommandTable.get(commandClass);
        if (annotation != null && !annotation.command().equals(mainCommand)) {
            var cmd = mainCommand + " " + annotation.command();
            register(commandClass, method, plugin.getServer().getPluginCommand(cmd), cmd, annotation, false);
        }
    }

    protected void register(@NotNull DarkCommand commandClass, @NotNull Method method, @Nullable PluginCommand pluginCommand, @NotNull String command,
                            @NotNull Object annotation, boolean isMainCommand) throws CommandException {
        register(pluginCommand, command);
        (isMainCommand ? registeredMainCommandTable : registeredSubCommandTable)
                .put(command, new RegisteredCommand(method, commandClass, annotation) {});
        plugin.sendLog(Level.CONFIG, "Command '&d" + command + "&6' registered!");
    }

    protected void register(@NotNull DarkCommand commandClass, @Nullable PluginCommand pluginCommand, @NotNull Command annotation)
            throws CommandException {
        var command = annotation.command();
        register(pluginCommand, command);
        registeredMainCommandTable.put(command, new RegisteredCommand(null, commandClass, annotation) {});
    }

    protected void register(@Nullable PluginCommand pluginCommand, @NotNull String command) throws CommandException {
        if (registeredSubCommandTable.containsKey(command) || registeredMainCommandTable.containsKey(command))
            throw new CommandException("Command '" + command + "' already registered!");
        if (pluginCommand == null)
            throw new CommandException("Unable to register command command '" + command + "'. Did you put it in plugin.yml?");
        pluginCommand.setExecutor(this);
    }

    protected @NotNull Method getMainCommandMethod(@NotNull Class<? extends DarkCommand> darkCommand) throws CommandException {
        for (var method : darkCommand.getMethods()) {
            if (method.getAnnotation(Command.class) != null) return method;
        }
        var name = darkCommand.getName().split("\\.");
        throw new CommandException("Main command in class '" + name[name.length - 1] + "' not found!");
    }

    protected boolean isDisAllowNonPlayer(@NotNull RegisteredCommand wrapper, @NotNull CommandSender sender, boolean disAllowNonPlayer) {
        if (!(sender instanceof Player) && disAllowNonPlayer) {
            failureHandler.handleFailure(CommandFailReason.NOT_PLAYER, sender, wrapper);
            return true;
        }
        return false;
    }

    protected boolean hasNotPermissions(@NotNull RegisteredCommand wrapper, @NotNull CommandSender sender, String permission) {
        if (!permission.equals("") && !sender.hasPermission(permission)) {
            failureHandler.handleFailure(CommandFailReason.NO_PERMISSION, sender, wrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label,
                             @NotNull String[] args) {
        return onExecute(sender, command, label, args);
    }

    protected abstract boolean onExecute(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label,
                                         @NotNull String[] args);
}

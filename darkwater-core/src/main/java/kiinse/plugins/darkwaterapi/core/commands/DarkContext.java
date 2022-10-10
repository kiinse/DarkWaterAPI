package kiinse.plugins.darkwaterapi.core.commands;

import kiinse.plugins.darkwaterapi.api.commands.CommandContext;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DarkContext implements CommandContext {

    private final CommandSender sender;
    private final PlayerLocale playerLocale;
    private final String[] args;

    public DarkContext(CommandSender sender, PlayerLocale playerLocale, String[] args) {
        this.sender = sender;
        this.playerLocale = playerLocale;
        this.args = args;
    }

    @Override
    public @NotNull CommandSender getSender() {
        return sender;
    }

    @Override
    public @NotNull String[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    @Override
    public @NotNull PlayerLocale getSenderLocale() {
        return playerLocale;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommandContext)) return false;
        return obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return "Sender: " + getSender().getName() + "\n" +
               "Locale: " + getSenderLocale() + "\n" +
               "Args: " + Arrays.toString(getArgs());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

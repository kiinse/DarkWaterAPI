package kiinse.plugins.api.darkwaterapi.commands.manager;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(@NotNull String message) {
        super(message);
    }

    public CommandException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public CommandException(@NotNull Throwable cause) {
        super(cause);
    }
}

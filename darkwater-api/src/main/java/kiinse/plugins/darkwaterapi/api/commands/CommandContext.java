package kiinse.plugins.darkwaterapi.api.commands;

import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface CommandContext {

    @NotNull CommandSender getSender();

    @NotNull String[] getArgs();

    @NotNull PlayerLocale getSenderLocale();

}

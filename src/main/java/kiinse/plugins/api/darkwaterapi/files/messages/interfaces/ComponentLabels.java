package kiinse.plugins.api.darkwaterapi.files.messages.interfaces;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface ComponentLabels {

    @NotNull Component parseMessage(@NotNull String msg);
}

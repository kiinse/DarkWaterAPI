package kiinse.plugins.api.darkwaterapi.files.messages.interfaces;

import kiinse.plugins.api.darkwaterapi.exceptions.JsonFileException;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;

@SuppressWarnings("unused")
public interface Messages {

    void reload() throws JsonFileException;

    @NotNull String getStringMessage(@NotNull Locale locale, @NotNull MessagesKeys message);

    @NotNull Component getComponentMessage(@NotNull Locale locale, @NotNull MessagesKeys message);

    @NotNull String getStringMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message);

    @NotNull Component getComponentMessageWithPrefix(@NotNull Locale locale, @NotNull MessagesKeys message);

    @NotNull JSONObject getAllLocaleMessages(@NotNull Locale locale);

    @NotNull HashMap<String, JSONObject> getAllMessages();

    @NotNull String getPrefix(@NotNull Locale locale);

    @NotNull String colorize(@NotNull String message);
}

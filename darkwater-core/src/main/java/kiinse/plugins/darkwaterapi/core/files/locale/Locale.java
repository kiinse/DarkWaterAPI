package kiinse.plugins.darkwaterapi.core.files.locale;

import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Locale extends PlayerLocale {

    private final String value;

    protected Locale(@NotNull String str) {
        value = str;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }

    @Override
    public boolean equals(@NotNull PlayerLocale playerLocale) {
        return Objects.equals(this.toString(), playerLocale.toString());
    }
}

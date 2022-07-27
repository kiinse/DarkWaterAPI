package kiinse.plugins.api.darkwaterapi.files.locale;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Locale {

    private final String value;

    protected Locale(@NotNull String str) {
        value = str;
    }

    public @NotNull String toString() {
        return value;
    }

    public static @NotNull Locale valueOf(@NotNull String str) {
        return new Locale(str) {};
    }


    public boolean equals(@NotNull Locale locale) {
        return Objects.equals(this.toString(), locale.toString());
    }

    public static boolean equals(@NotNull Locale locale1, @NotNull Locale locale2) {
        return Objects.equals(locale1.toString(), locale2.toString());
    }
}

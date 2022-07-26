package kiinse.plugins.api.darkwaterapi.files.locale;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Locale {

    private final String value;

    protected Locale(String str) {
        value = str;
    }

    public String toString() {
        return value;
    }

    public static Locale valueOf(String str) {
        return new Locale(str) {};
    }


    public boolean equals(Locale locale) {
        return Objects.equals(this.toString(), locale.toString());
    }

    public static boolean equals(Locale locale1, Locale locale2) {
        return Objects.equals(locale1.toString(), locale2.toString());
    }
}

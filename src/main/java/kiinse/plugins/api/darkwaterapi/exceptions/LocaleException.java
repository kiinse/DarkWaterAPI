package kiinse.plugins.api.darkwaterapi.exceptions;

@SuppressWarnings("unused")
public class LocaleException extends DarkWaterBaseException {

    public LocaleException(String message) {
        super(message);
    }

    public LocaleException(Throwable cause) {
        super(cause);
    }

    public LocaleException(String message, Throwable cause) {
        super(message, cause);
    }
}

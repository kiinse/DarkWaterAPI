package kiinse.plugins.api.darkwaterapi.exceptions;

@SuppressWarnings("unused")
public class VersioningException extends DarkWaterBaseException {

    public VersioningException(String message) {
        super(message);
    }

    public VersioningException(Throwable cause) {
        super(cause);
    }

    public VersioningException(String message, Throwable cause) {
        super(message, cause);
    }
}

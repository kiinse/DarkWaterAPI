package kiinse.plugins.darkwaterapi.api.exceptions.exceptions;

public class DarkWaterBaseException extends Exception {

    public DarkWaterBaseException() {
        super();
    }

    public DarkWaterBaseException(String message) {
        super(message);
    }

    public DarkWaterBaseException(Throwable cause) {
        super(cause);
    }

    public DarkWaterBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

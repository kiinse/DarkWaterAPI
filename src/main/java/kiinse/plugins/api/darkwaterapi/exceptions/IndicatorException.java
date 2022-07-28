package kiinse.plugins.api.darkwaterapi.exceptions;

@SuppressWarnings("unused")
public class IndicatorException extends DarkWaterBaseException {

    public IndicatorException(String message) {
        super(message);
    }

    public IndicatorException(Throwable cause) {
        super(cause);
    }

    public IndicatorException(String message, Throwable cause) {
        super(message, cause);
    }
}

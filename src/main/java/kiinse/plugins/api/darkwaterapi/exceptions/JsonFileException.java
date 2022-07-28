package kiinse.plugins.api.darkwaterapi.exceptions;

@SuppressWarnings("unused")
public class JsonFileException extends DarkWaterBaseException {

    public JsonFileException(String message) {
        super(message);
    }

    public JsonFileException(Throwable cause) {
        super(cause);
    }

    public JsonFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

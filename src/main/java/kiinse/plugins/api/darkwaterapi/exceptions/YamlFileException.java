package kiinse.plugins.api.darkwaterapi.exceptions;

@SuppressWarnings("unused")
public class YamlFileException extends DarkWaterBaseException {

    public YamlFileException(String message) {
        super(message);
    }

    public YamlFileException(Throwable cause) {
        super(cause);
    }

    public YamlFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

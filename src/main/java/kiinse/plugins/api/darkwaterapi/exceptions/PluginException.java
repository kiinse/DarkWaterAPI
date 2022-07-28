package kiinse.plugins.api.darkwaterapi.exceptions;

@SuppressWarnings("unused")
public class PluginException extends DarkWaterBaseException {

    public PluginException(String message) {
        super(message);
    }

    public PluginException(Throwable cause) {
        super(cause);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }
}

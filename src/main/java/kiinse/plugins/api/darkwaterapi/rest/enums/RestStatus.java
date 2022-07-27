package kiinse.plugins.api.darkwaterapi.rest.enums;

import org.jetbrains.annotations.NotNull;

public enum RestStatus {
    ERROR_AUTHENTICATION( 401, "An error occurred during authentication."),
    ERROR_AUTHENTICATION_DISABLED(401, "You can't use it because authentication is disabled"),
    ERROR_PING_RECEIVE(500, "An error occurred while receiving a ping"),
    ERROR(500, "Undefined error"),
    ERROR_NO_DATA(400, "There is not enough data to complete the request."),
    ERROR_RSA_EMPTY(400, "RSA Exponent or RSA modulus is empty!"),
    SUCCESS(200, "The request was completed without errors"),
    NO_DATA(204, "The request was completed, but the server has nothing to return."),
    ERROR_SERVICE_DISABLED(403, "This server is down. Contact the administrator for details."),
    NOT_FOUND(404, "Player not found!");

    private final int code;
    private final @NotNull String message;

    public int getCode() {
        return code;
    }

    public @NotNull String getMessage() {
        return message;
    }

    RestStatus(int code, @NotNull String message) {
        this.code = code;
        this.message = message;
    }
}

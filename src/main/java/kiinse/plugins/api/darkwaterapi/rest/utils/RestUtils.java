package kiinse.plugins.api.darkwaterapi.rest.utils;

import kiinse.plugins.api.darkwaterapi.rest.enums.RestStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;

@SuppressWarnings({"unchecked", "unused"})
public class RestUtils {

    private RestUtils() {}

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status) {
        return createAnswer(status, (Exception) null);
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @Nullable String data) {
        var json = createAnswer(status, (Exception) null);
        if (data != null) {
            json.put("data", data);
        }
        return json;
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @Nullable JSONObject data) {
        var json = createAnswer(status, (Exception) null);
        if (data != null) {
            json.put("data", data);
        }
        return json;
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @Nullable Exception message) {
        var json = new JSONObject();
        json.put("code", status.getCode());
        json.put("message", message == null ? status.getMessage() : message.getMessage());
        json.put("status", status.name());
        return json;
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @Nullable Exception message, @Nullable String data) {
        var json = createAnswer(status, message);
        json.put("data", data);
        return json;
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @Nullable Exception message, @Nullable JSONObject data) {
        var json = createAnswer(status, message);
        json.put("data", data);
        return json;
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @NotNull String message, @NotNull String data) {
        var json = new JSONObject();
        json.put("code", status.getCode());
        json.put("message", message);
        json.put("status", status.name());
        json.put("data", data);
        return json;
    }

    public static @NotNull JSONObject createAnswer(@NotNull RestStatus status, @NotNull String message, @NotNull JSONObject data) {
        var json = new JSONObject();
        json.put("code", status.getCode());
        json.put("message", message);
        json.put("status", status.name());
        json.put("data", data);
        return json;
    }


}

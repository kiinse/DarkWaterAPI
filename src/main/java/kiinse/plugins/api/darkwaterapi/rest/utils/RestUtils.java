package kiinse.plugins.api.darkwaterapi.rest.utils;

import org.json.simple.JSONObject;

@SuppressWarnings({"unchecked", "unused"})
public class RestUtils {

    private RestUtils() {}

    public static JSONObject createAnswer(RestStatus status) {
        return createAnswer(status, (Exception) null);
    }

    public static JSONObject createAnswer(RestStatus status, String data) {
        var json = createAnswer(status, (Exception) null);
        if (data != null) {
            json.put("data", data);
        }
        return json;
    }

    public static JSONObject createAnswer(RestStatus status, JSONObject data) {
        var json = createAnswer(status, (Exception) null);
        if (data != null) {
            json.put("data", data);
        }
        return json;
    }

    public static JSONObject createAnswer(RestStatus status, Exception message) {
        var json = new JSONObject();
        json.put("code", status.getCode());
        json.put("message", message == null ? status.getMessage() : message.getMessage());
        json.put("status", status.name());
        return json;
    }

    public static JSONObject createAnswer(RestStatus status, Exception message, String data) {
        var json = createAnswer(status, message);
        json.put("data", data);
        return json;
    }

    public static JSONObject createAnswer(RestStatus status, Exception message, JSONObject data) {
        var json = createAnswer(status, message);
        json.put("data", data);
        return json;
    }

    public static JSONObject createAnswer(RestStatus status, String message, String data) {
        var json = new JSONObject();
        json.put("code", status.getCode());
        json.put("message", message);
        json.put("status", status.name());
        json.put("data", data);
        return json;
    }

    public static JSONObject createAnswer(RestStatus status, String  message, JSONObject data) {
        var json = new JSONObject();
        json.put("code", status.getCode());
        json.put("message", message);
        json.put("status", status.name());
        json.put("data", data);
        return json;
    }


}

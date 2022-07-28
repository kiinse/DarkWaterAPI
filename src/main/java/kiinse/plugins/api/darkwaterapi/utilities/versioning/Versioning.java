package kiinse.plugins.api.darkwaterapi.utilities.versioning;

import com.vdurmont.semver4j.Semver;
import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.exceptions.VersioningException;
import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

@SuppressWarnings("unused")
public class Versioning {

    public static @NotNull Semver getLatestGithubVersion(@NotNull String url) throws VersioningException {
        try {
            var request = new HttpGet((url.endsWith("/") ? url.substring(0, url.length()-1) : url) + "/releases/latest");
            var httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();
            request.addHeader("Accept", "application/json");
            var result = new JSONObject(EntityUtils.toString(httpClient.execute(request).getEntity(), "UTF-8")).getString("tag_name");
            return new Semver(result.startsWith("v") ? result.substring(1) : result);
        } catch (IOException e) {
            throw new VersioningException("Failed to get the latest version of DarkWaterAPI to check.", e);
        }
    }

    public static @NotNull Semver getCurrentDarkWaterVersion() {
        return new Semver(DarkWaterAPI.getInstance().getDescription().getVersion());
    }

    public static @NotNull Semver getPluginVersion(DarkWaterJavaPlugin plugin) {
        return new Semver(plugin.getDescription().getVersion());
    }

    private Versioning() {}
}

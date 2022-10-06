// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.darkwaterapi.core.utilities;

import com.vdurmont.semver4j.Semver;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.exceptions.VersioningException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DarkVersionUtils {

    private DarkVersionUtils() {}

    public static @NotNull Semver getLatestGithubVersion(@NotNull String url) throws VersioningException {
        var version = getLatestGithubVersionAsString(url);
        return new Semver(version.startsWith("v") ? version.substring(1) : version);
    }

    private static @NotNull Semver getLatestSpigotVersion(int pluginId) throws VersioningException {
        return new Semver(getLatestSpigotVersionAsString(pluginId));
    }

    public static void getLatestGithubVersion(@NotNull String url, @NotNull Consumer<Semver> consumer) throws VersioningException {
        var version = getLatestGithubVersionAsString(url);
        consumer.accept(new Semver(version.startsWith("v") ? version.substring(1) : version));
    }

    private static void getLatestSpigotVersion(int pluginId, @NotNull Consumer<Semver> consumer) throws VersioningException {
        consumer.accept(new Semver(getLatestSpigotVersionAsString(pluginId)));
    }

    public static void getLatestGithubVersionAsString(@NotNull String url, @NotNull Consumer<String> consumer) throws VersioningException {
        consumer.accept(getLatestGithubVersionAsString(url));
    }

    private static void getLatestSpigotVersionAsString(int pluginId, @NotNull Consumer<String> consumer) throws VersioningException {
        consumer.accept(getLatestSpigotVersionAsString(pluginId));
    }

    public static @NotNull String getLatestGithubVersionAsString(@NotNull String url) throws VersioningException {
        try {
            var request = new HttpGet((url.endsWith("/") ? url.substring(0, url.length() - 1) : url) + "/releases/latest");
            var httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(StandardCookieSpec.STRICT).build()).build();
            request.addHeader("Accept", "application/json");
            var result = new JSONObject(EntityUtils.toString(httpClient.execute(request).getEntity(), "UTF-8")).getString("tag_name");
            return result.startsWith("v") ? result.substring(1) : result;
        } catch (IOException | ParseException e) {
            throw new VersioningException("Failed to get the latest version from '" + url + "'", e);
        }
    }

    private static @NotNull String getLatestSpigotVersionAsString(int pluginId) throws VersioningException {
        try (var inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + pluginId).openStream()) {
            var scanner = new Scanner(inputStream);
            if (scanner.hasNext()) return scanner.next();
        } catch (IOException e) {
            throw new VersioningException("Failed to get the latest version SpigotMC", e);
        }
        throw new VersioningException("Failed to get the latest version SpigotMC");
    }

    public static @NotNull Semver getPluginVersion(DarkWaterJavaPlugin plugin) {
        return new Semver(plugin.getDescription().getVersion());
    }

    public static boolean isGithubVersionGreaterThanGithub(@NotNull String url, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestGithubVersion(url).isGreaterThan(getPluginVersion(plugin));
    }

    public static boolean isSpigotVersionGreaterThanPlugin(int pluginId, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestSpigotVersion(pluginId).isGreaterThan(getPluginVersion(plugin));
    }

    public static boolean isGithubVersionLowerThanPlugin(@NotNull String url, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestGithubVersion(url).isLowerThan(getPluginVersion(plugin));
    }

    public static boolean isSpigotVersionLowerThanPlugin(int pluginId, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestSpigotVersion(pluginId).isLowerThan(getPluginVersion(plugin));
    }

    public static boolean isGithubVersionEqualsPlugin(@NotNull String url, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestGithubVersion(url).isEqualTo(getPluginVersion(plugin));
    }

    public static boolean isSpigotVersionEqualsPlugin(int pluginId, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestSpigotVersion(pluginId).isEqualTo(getPluginVersion(plugin));
    }

    public static boolean isGithubVersionGreaterOrEqualsPlugin(@NotNull String url, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestGithubVersion(url).isGreaterThanOrEqualTo(getPluginVersion(plugin));
    }

    public static boolean isSpigotVersionGreaterOrEqualsPlugin(int pluginId, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestSpigotVersion(pluginId).isGreaterThanOrEqualTo(getPluginVersion(plugin));
    }

    public static boolean isGithubVersionLowerOrEqualsPlugin(@NotNull String url, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestGithubVersion(url).isLowerThanOrEqualTo(getPluginVersion(plugin));
    }

    public static boolean isSpigotVersionLowerOrEqualsPlugin(int pluginId, @NotNull DarkWaterJavaPlugin plugin) throws VersioningException {
        return getLatestSpigotVersion(pluginId).isLowerThanOrEqualTo(getPluginVersion(plugin));
    }
}

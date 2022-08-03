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
import kiinse.plugins.darkwaterapi.api.exceptions.VersioningException;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

@SuppressWarnings("unused")
public class DarkVersionUtils {

    public static @NotNull Semver getLatestGithubVersion(@NotNull String url) throws VersioningException {
        try {
            var request = new HttpGet((url.endsWith("/") ? url.substring(0, url.length()-1) : url) + "/releases/latest");
            var httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(StandardCookieSpec.STRICT).build()).build();
            request.addHeader("Accept", "application/json");
            var result = new JSONObject(EntityUtils.toString(httpClient.execute(request).getEntity(), "UTF-8")).getString("tag_name");
            return new Semver(result.startsWith("v") ? result.substring(1) : result);
        } catch (IOException | ParseException e) {
            throw new VersioningException("Failed to get the latest version from '" + url + "'", e);
        }
    }

    public static @NotNull Semver getPluginVersion(DarkWaterJavaPlugin plugin) {
        return new Semver(plugin.getDescription().getVersion());
    }

    private DarkVersionUtils() {}
}

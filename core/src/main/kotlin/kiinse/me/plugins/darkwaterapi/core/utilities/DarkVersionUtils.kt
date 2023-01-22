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
package kiinse.me.plugins.darkwaterapi.core.utilities

import com.vdurmont.semver4j.Semver
import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.VersioningException
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.cookie.StandardCookieSpec
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder
import org.apache.hc.core5.http.ParseException
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.http.message.BasicClassicHttpRequest
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.function.Consumer

@Suppress("unused")
object DarkVersionUtils {

    @Throws(VersioningException::class)
    fun getLatestGithubVersion(url: String): Semver {
        val version = getLatestGithubVersionAsString(url)
        return Semver(if (version.startsWith("v")) version.substring(1) else version)
    }

    @Throws(VersioningException::class)
    private fun getLatestSpigotVersion(pluginId: Int): Semver {
        return Semver(getLatestSpigotVersionAsString(pluginId))
    }

    @Throws(VersioningException::class)
    fun getLatestGithubVersion(url: String, consumer: Consumer<Semver?>) {
        val version = getLatestGithubVersionAsString(url)
        consumer.accept(Semver(if (version.startsWith("v")) version.substring(1) else version))
    }

    @Throws(VersioningException::class)
    private fun getLatestSpigotVersion(pluginId: Int, consumer: Consumer<Semver>) {
        consumer.accept(Semver(getLatestSpigotVersionAsString(pluginId)))
    }

    @Throws(VersioningException::class)
    fun getLatestGithubVersionAsString(url: String, consumer: Consumer<String?>) {
        consumer.accept(getLatestGithubVersionAsString(url))
    }

    @Throws(VersioningException::class)
    private fun getLatestSpigotVersionAsString(pluginId: Int, consumer: Consumer<String>) {
        consumer.accept(getLatestSpigotVersionAsString(pluginId))
    }

    @Throws(VersioningException::class)
    fun getLatestGithubVersionAsString(url: String): String {
        return try {
            val httpClient = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(StandardCookieSpec.STRICT).build()).build()
            val req = BasicClassicHttpRequest("GET", (if (url.endsWith("/")) url.substring(0, url.length - 1) else url) + "/releases/latest")
            req.addHeader("Accept", "application/json")
            httpClient.execute(req) {
                val result = JSONObject(EntityUtils.toString(it.entity, "UTF-8")).getString("tag_name")
                if (result.startsWith("v")) return@execute result.substring(1) else return@execute result
            }
        } catch (e: IOException) {
            throw VersioningException("Failed to get the latest version from '$url'", e)
        } catch (e: ParseException) {
            throw VersioningException("Failed to get the latest version from '$url'", e)
        }
    }

    @Throws(VersioningException::class)
    private fun getLatestSpigotVersionAsString(pluginId: Int): String {
        try {
            URL("https://api.spigotmc.org/legacy/update.php?resource=$pluginId").openStream().use { inputStream ->
                val scanner = Scanner(inputStream)
                if (scanner.hasNext()) return scanner.next()
            }
        } catch (e: IOException) {
            throw VersioningException("Failed to get the latest version SpigotMC", e)
        }
        throw VersioningException("Failed to get the latest version SpigotMC")
    }

    fun getPluginVersion(plugin: DarkWaterJavaPlugin): Semver {
        return Semver(plugin.description.version)
    }

    @Throws(VersioningException::class)
    fun isGithubVersionGreaterThanGithub(url: String, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestGithubVersion(url).isGreaterThan(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isSpigotVersionGreaterThanPlugin(pluginId: Int, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestSpigotVersion(pluginId).isGreaterThan(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isGithubVersionLowerThanPlugin(url: String, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestGithubVersion(url).isLowerThan(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isSpigotVersionLowerThanPlugin(pluginId: Int, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestSpigotVersion(pluginId).isLowerThan(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isGithubVersionEqualsPlugin(url: String, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestGithubVersion(url).isEqualTo(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isSpigotVersionEqualsPlugin(pluginId: Int, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestSpigotVersion(pluginId).isEqualTo(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isGithubVersionGreaterOrEqualsPlugin(url: String, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestGithubVersion(url).isGreaterThanOrEqualTo(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isSpigotVersionGreaterOrEqualsPlugin(pluginId: Int, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestSpigotVersion(pluginId).isGreaterThanOrEqualTo(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isGithubVersionLowerOrEqualsPlugin(url: String, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestGithubVersion(url).isLowerThanOrEqualTo(getPluginVersion(plugin))
    }

    @Throws(VersioningException::class)
    fun isSpigotVersionLowerOrEqualsPlugin(pluginId: Int, plugin: DarkWaterJavaPlugin): Boolean {
        return getLatestSpigotVersion(pluginId).isLowerThanOrEqualTo(getPluginVersion(plugin))
    }
}
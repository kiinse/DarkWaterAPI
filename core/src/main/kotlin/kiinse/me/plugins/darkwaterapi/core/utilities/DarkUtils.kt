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

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.common.base.Strings
import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.messages.ReplaceKeys
import kiinse.me.plugins.darkwaterapi.api.utilities.TaskType
import org.apache.commons.lang3.RandomStringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.event.block.Action
import org.bukkit.scheduler.BukkitTask
import org.bukkit.World
import java.security.interfaces.RSAPrivateKey
import java.util.*
import java.util.concurrent.TimeUnit

@Suppress("unused")
object DarkUtils {
    fun getProgressBar(current: Int, max: Int, totalBars: Int, char1: String, char2: String): String {
        val progressBars = (totalBars * (current.toFloat() / max)).toInt()
        return Strings.repeat("" + char1, progressBars) + Strings.repeat("" + char2, totalBars - progressBars)
    }

    fun colorize(text: String): String {
        return ChatColor.translateAlternateColorCodes('&', text)
    }

    fun isClickAction(action: Action): Boolean {
        return action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR
    }

    fun replaceWord(text: String, from: String, to: String): String {
        return text.replace(from, to)
    }

    fun replaceWord(text: String, from: ReplaceKeys, to: String): String {
        return text.replace(formatReplaceKeys(from), to)
    }

    fun replaceWord(text: String, words: Array<String>): String {
        var result = text
        for (word in words) result = replaceWord(result, getWord(word, Word.FIRST), getWord(word, Word.SECOND))
        return result
    }

    fun runTask(taskType: TaskType, plugin: DarkWaterJavaPlugin, task: Runnable): BukkitTask {
        return if (taskType === TaskType.ASYNC) Bukkit.getScheduler().runTaskAsynchronously(plugin, task) else Bukkit.getScheduler().runTask(plugin, task)
    }

    fun getTaskSpeed(task: Runnable): Long {
        val prevTime = System.currentTimeMillis()
        task.run()
        return System.currentTimeMillis() - prevTime
    }

    fun formatReplaceKeys(key: ReplaceKeys): String {
        return "{$key}"
    }

    private fun getWord(text: String, word: Word): String {
        val result = text.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (word == Word.FIRST) result[0] else result[1]
    }

    fun getRegionBlocks(world: World, loc1: Location, loc2: Location): List<Block> {
        val blocks = ArrayList<Block>()
        var x = loc1.x
        while (x <= loc2.x) {
            var y = loc1.y
            while (y <= loc2.y) {
                var z = loc1.z
                while (z <= loc2.z) {
                    blocks.add(Location(world, x, y, z).block)
                    z++
                }
                y++
            }
            x++
        }
        return blocks
    }

    fun getRandomASCII(length: Int): String {
        return RandomStringUtils.randomAscii(length)
    }

    fun generateJwtToken(user: String, key: RSAPrivateKey, expiresInHours: Int): String {
        return JWT.create()
                .withSubject(user)
                .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expiresInHours.toLong())))
                .sign(Algorithm.RSA256(key))
    }

    fun generateJwtToken(user: String, secret: String, expiresInHours: Int): String {
        return JWT.create()
                .withSubject(user)
                .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expiresInHours.toLong())))
                .sign(Algorithm.HMAC256(secret))
    }

    private enum class Word {
        FIRST,
        SECOND
    }
}
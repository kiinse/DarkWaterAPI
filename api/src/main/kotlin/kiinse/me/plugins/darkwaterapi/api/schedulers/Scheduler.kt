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
package kiinse.me.plugins.darkwaterapi.api.schedulers

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import org.apache.commons.lang3.RandomStringUtils
import java.util.logging.Level

abstract class Scheduler protected constructor(plugin: DarkWaterJavaPlugin) {

    val plugin: DarkWaterJavaPlugin
    var name: String? = null
        set(name) {
            field = if (name.isNullOrBlank()) {
                RandomStringUtils.randomAscii(60).replace(">", "")
            } else { name }
        }
    var delay: Long = -1
    var period: Long = -1
    val isStarted: Boolean
        get() = plugin.server.scheduler.isCurrentlyRunning(schedulerID)
    private var schedulerID = 0

    init {
        this.plugin = plugin
    }

    open fun canStart(): Boolean {
        return true
    }

    fun start() {
        if (canStart()) {
            schedulerID = plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, { this.run() }, delay, period)
            plugin.sendLog("Scheduler '&b$name&a' started!")
            return
        }
        plugin.sendLog(Level.CONFIG, "Scheduler '&d$name&6' cannot be started because the '&dcanStart()&6' method returns &cfalse")
    }

    fun stop() {
        if (isStarted) {
            plugin.server.scheduler.cancelTask(schedulerID)
            plugin.sendLog("Scheduler '&b$name&a' stopped!")
        }
    }

    abstract fun run()
}
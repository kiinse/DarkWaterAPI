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
package kiinse.me.plugins.darkwaterapi.core.indicators

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.exceptions.IndicatorException
import kiinse.me.plugins.darkwaterapi.api.indicators.Indicator
import kiinse.me.plugins.darkwaterapi.api.indicators.IndicatorManager
import java.util.*
import java.util.logging.Level

class DarkIndicatorManager(mainPlugin: DarkWaterJavaPlugin) : IndicatorManager {

    private val mainPlugin: DarkWaterJavaPlugin
    private val indicatorsList: HashMap<String, Indicator> = HashMap<String, Indicator>()

    init {
        this.mainPlugin = mainPlugin
    }

    @Throws(IndicatorException::class)
    override fun register(plugin: DarkWaterJavaPlugin, indicator: Indicator): IndicatorManager {
        if (hasIndicator(indicator))
            throw IndicatorException("Indicator '${indicator.name}' already registered by '${indicatorsList[indicator.name]!!.plugin}'")
        if (!indicator.name.startsWith("%") || !indicator.name.endsWith("%"))
            throw IndicatorException("The '${indicator.name}' indicator must be a placeholder, i.e. start and end with % ")
        if (hasPosition(indicator)) {
            val pos = maxPosition + 1
            plugin.sendLog(Level.WARNING, "Indicator position &c${indicator.position} is already used by '&b${getIndicatorByPosition(indicator.position)?.plugin?.name}&6'\nUsing last position: &b" + pos)
            registerIndicator(plugin, Indicator.valueOf(indicator.plugin, indicator.name, pos))
            setIndicatorListToConsole()
            return this
        }
        registerIndicator(plugin, indicator)
        setIndicatorListToConsole()
        return this
    }

    override fun setIndicatorListToConsole() {
        mainPlugin.sendLog(Level.CONFIG, "Indicators list: &d")
        var j = 0
        for (i in 0..maxPosition) {
            val indicator = getIndicatorByPosition(i)
            if (indicator != null) {
                mainPlugin.sendLog(Level.CONFIG, "Position: '&d" + j + "&6' (Registered &d" + i + "&6) | Indicator: '&d${indicator.name}&6' | Plugin: '&d${indicator.plugin}&6'")
                j++
            }
        }
    }

    override fun getIndicators(): String {
        val result = StringBuilder()
        for (i in 0..maxPosition) {
            val indicator = getIndicatorByPosition(i)
            if (indicator != null) result.append(indicator.name)
        }
        return result.toString()
    }

    override val indicatorsSet: Set<Indicator>
        get() {
            val set: HashSet<Indicator> = HashSet<Indicator>()
            indicatorsList.forEach { (_, value) -> set.add(value) }
            return set
        }

    private fun registerIndicator(plugin: DarkWaterJavaPlugin, indicator: Indicator) {
        indicatorsList[indicator.name] = indicator
        plugin.sendLog("Registered indicator '&b${indicator.name}&a' by '&b${plugin.name}&a' on position &b${indicator.position}")
    }

    override val maxPosition: Int
        get() {
            var position = 0
            indicatorsList.forEach { (_, value) ->
                val indicatorPos = value.position
                if (indicatorPos > position) position = indicatorPos
            }
            return position
        }

    override fun removeIndicator(indicator: Indicator): Boolean {
        mainPlugin.sendLog("Removing indicator '&b${indicator.name}&6'...")
        indicatorsList.forEach { (key, value) ->
            if (value == indicator) {
                indicatorsList.remove(key)
                mainPlugin.sendLog("Indicator '&b${indicator.name}&a' on position &b${indicator.position}&a has been removed!")
                return true
            }
        }
        return false
    }

    override fun hasIndicator(indicator: Indicator): Boolean {
        indicatorsList.forEach { (_, value) -> if (value == indicator) return true }
        return false
    }

    override fun hasPosition(indicator: Indicator): Boolean {
        indicatorsList.forEach { (_, value) -> if (value.position == indicator.position) return true }
        return false
    }

    override fun getIndicatorByPosition(position: Int): Indicator? {
        indicatorsList.forEach { (_, value) -> if (value.position == position) return value }
        return null
    }
}
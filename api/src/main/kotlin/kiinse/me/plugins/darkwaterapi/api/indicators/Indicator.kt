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
package kiinse.me.plugins.darkwaterapi.api.indicators

import kiinse.me.plugins.darkwaterapi.api.exceptions.IndicatorException
import org.bukkit.plugin.Plugin

@Suppress("unused")
abstract class Indicator protected constructor(val plugin: Plugin, val name: String, val position: Int) {

    override fun equals(other: Any?): Boolean {
        return other != null && other is Indicator && other.hashCode() == hashCode()
    }

    override fun hashCode(): Int {
        return name.hashCode() + position.hashCode() + plugin.name.hashCode()
    }

    companion object {

        @Throws(IndicatorException::class)
        fun valueOf(plugin: Plugin, name: String, position: Int): Indicator {
            if (name.isBlank()) throw IndicatorException("Indicator name is empty!")
            if (!name.startsWith("%") || !name.endsWith("%"))
                throw IndicatorException("Invalid indicator format! Please, user placeholder %indicator%!")
            if (position < 0) throw IndicatorException("Position can't be < 0!")
            return object: Indicator(plugin, name, position) {}
        }

        fun equals(indicator1: Indicator, indicator2: Indicator): Boolean {
            return indicator1.name == indicator2.name && indicator1.position == indicator2.position
        }
    }
}
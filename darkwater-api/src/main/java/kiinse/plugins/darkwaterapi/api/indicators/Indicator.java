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

package kiinse.plugins.darkwaterapi.api.indicators;

import kiinse.plugins.darkwaterapi.api.exceptions.IndicatorException;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Indicator {

    private final int position;
    private final String name;
    private final Plugin plugin;

    protected Indicator(@NotNull Plugin plugin, @NotNull String name, int position) {
        this.position = position;
        this.name = name;
        this.plugin = plugin;
    }

    public int getPosition() {
        return position;
    }

    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    public @NotNull String getName() {
        return name;
    }

    public static @NotNull Indicator valueOf(@NotNull Plugin plugin, @NotNull String name, int position) throws IndicatorException {
        if (name.isBlank()) {
            throw new IndicatorException("Indicator name is empty!");
        }
        if (!name.startsWith("%") || !name.endsWith("%")) {
            throw new IndicatorException("Invalid indicator format! Please, user placeholder %indicator%!");
        }
        if (position < 0) {
            throw new IndicatorException("Position can't be < 0!");
        }
        return new Indicator(plugin, name, position) {};
    }

    public boolean equals(@NotNull Indicator indicator) {
        return Objects.equals(this.getName(), indicator.getName()) && this.getPosition() == indicator.getPosition();
    }

    public static boolean equals(@NotNull Indicator indicator1, @NotNull Indicator indicator2) {
        return Objects.equals(indicator1.getName(), indicator2.getName()) && indicator1.getPosition() == indicator2.getPosition();
    }
}

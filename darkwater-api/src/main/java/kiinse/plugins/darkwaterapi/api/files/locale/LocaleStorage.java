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

package kiinse.plugins.darkwaterapi.api.files.locale;

import kiinse.plugins.darkwaterapi.api.exceptions.JsonFileException;
import kiinse.plugins.darkwaterapi.api.exceptions.LocaleException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface LocaleStorage {

    @NotNull LocaleStorage load() throws LocaleException, JsonFileException;

    @NotNull LocaleStorage save() throws JsonFileException;

    boolean isAllowedLocale(@NotNull Locale locale);

    boolean putInLocalesData(@NotNull UUID uuid, @NotNull Locale locale);

    boolean putInLocalesData(@NotNull Player player, @NotNull Locale locale);

    boolean isLocalesDataContains(@NotNull UUID uuid);

    boolean isLocalesDataContains(@NotNull Player player);

    @NotNull Locale getLocalesData(@NotNull UUID uuid);

    @NotNull Locale getLocalesData(@NotNull Player player);

    boolean removeLocalesData(@NotNull UUID uuid);

    boolean removeLocalesData(@NotNull Player player);

    @NotNull Locale getDefaultLocale();

    @NotNull HashMap<UUID, Locale> getLocalesData();

    @NotNull String getAllowedLocalesString();

    @NotNull Set<Locale> getAllowedLocalesList();

    @NotNull Set<String> getAllowedLocalesListString();

}

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

package kiinse.plugins.api.darkwaterapi.placeholders;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocaleExpansion extends PlaceholderExpansion {

    private final DarkWaterAPI darkWaterAPI;

    // %locale_player%
    // %locale_list%

    public LocaleExpansion(@NotNull DarkWaterAPI darkWaterAPI){
        this.darkWaterAPI = darkWaterAPI;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getAuthor(){
        return darkWaterAPI.getDescription().getAuthors().get(0);
    }


    @Override
    public @NotNull String getIdentifier(){
        return "locale";
    }


    @Override
    public @NotNull String getVersion(){
        return darkWaterAPI.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(@Nullable Player player, @NotNull String identifier){
        if(player == null){
            return "";
        }
        return switch (identifier) {
            case "player" -> darkWaterAPI.getPlayerLocales().getPlayerLocale(player).toString();
            case "list" -> darkWaterAPI.getLocaleStorage().getAllowedLocalesString();
            default -> null;
        };
    }
}

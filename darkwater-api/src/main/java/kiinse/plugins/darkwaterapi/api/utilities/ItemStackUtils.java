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

package kiinse.plugins.darkwaterapi.api.utilities;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface ItemStackUtils {

    @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String name, @Nullable List<String> lore, int amount);

    @NotNull ItemStack getPotionItemStack(@Nullable String name, @Nullable List<String> lore, @NotNull PotionType type, int amount);

    @NotNull FurnaceRecipe getFurnaceRecipe(@NotNull String key, @NotNull ItemStack result, float experience, int cookingTime);

    @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient1,
                                                @NotNull ItemStack ingredient2);

    @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient);

    @NotNull ShapedRecipe getShapedRecipe(@NotNull String key, @NotNull ItemStack result);

    @NotNull ItemStack getPlayerHead(@NotNull Player player);

    @NotNull ItemStack getPlayerHead(@NotNull OfflinePlayer player);

    @NotNull ItemStack getPlayerHead(@NotNull UUID player) throws IOException;

    @NotNull ItemStack getPlayerHead(String player) throws IOException;

    @NotNull ItemStack makeSkull(@NotNull String base64);

    boolean checkItemStack(@NotNull ItemStack stack, @NotNull Material material);
}

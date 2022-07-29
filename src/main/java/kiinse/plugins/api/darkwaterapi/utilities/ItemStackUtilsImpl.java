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

package kiinse.plugins.api.darkwaterapi.utilities;

import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import kiinse.plugins.api.darkwaterapi.utilities.interfaces.ItemStackUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class ItemStackUtilsImpl implements ItemStackUtils {

    private final DarkWaterJavaPlugin plugin;

    public ItemStackUtilsImpl(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String name, @Nullable List<Component> lore, int amount) {
        var item = new ItemStack(material);
        var meta = item.getItemMeta();
        if (!DarkWaterUtils.isStringEmpty(name)) {
            meta.displayName(Component.text(name));
        }
        if (lore != null) {
            meta.lore(lore);
        }
        item.setItemMeta(meta);
        item.setAmount(amount);
        return item;
    }

    @Override
    public @NotNull ItemStack getPotionItemStack(@Nullable String name, @Nullable List<Component> lore, @NotNull PotionType type, int amount) {
        var item = new ItemStack(Material.POTION);
        var meta = item.getItemMeta();
        if (!DarkWaterUtils.isStringEmpty(name)) {
            meta.displayName(Component.text(name));
        }
        if (lore != null) {
            meta.lore(lore);
        }
        ((PotionMeta) meta).setBasePotionData(new PotionData(type));
        item.setAmount(amount);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public @NotNull FurnaceRecipe getFurnaceRecipe(@NotNull String key, @NotNull ItemStack result, float experience, int cookingTime) {
        return new FurnaceRecipe(new NamespacedKey(plugin, key), result, result.getType(), experience, cookingTime);
    }

    @Override
    public @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient1, @NotNull ItemStack ingredient2) {
        var recipe = new ShapelessRecipe(new NamespacedKey(plugin, key), result);
        recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient1));
        recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient2));
        return recipe;
    }

    @Override
    public @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient) {
        var recipe = new ShapelessRecipe(new NamespacedKey(plugin, key), result);
        recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient));
        return recipe;
    }

    @Override
    public @NotNull ShapedRecipe getShapedRecipe(@NotNull String key, @NotNull ItemStack result) {
        return new ShapedRecipe(new NamespacedKey(plugin, key), result);
    }

    @Override
    public @NotNull ItemStack getPlayerHead(@NotNull Player player, @NotNull String displayName, @NotNull List<Component> lore) {
        var item = new ItemStack(Material.PLAYER_HEAD, 1);
        var skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(player);
        skull.displayName(Component.text(DarkWaterUtils.colorize(displayName)));
        skull.lore(lore);
        item.setItemMeta(skull);
        return item;
    }

    @Override
    public @NotNull ItemStack getPlayerHead(@NotNull UUID player, @NotNull String displayName, @NotNull List<Component> lore) {
        var item = new ItemStack(Material.PLAYER_HEAD, 1);
        var skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(player));
        skull.displayName(Component.text(DarkWaterUtils.colorize(displayName)));
        skull.lore(lore);
        item.setItemMeta(skull);
        return item;
    }

    @Override
    public boolean checkItemStack(@NotNull ItemStack stack, @NotNull Material material) {
        return stack.getType() == material;
    }
}

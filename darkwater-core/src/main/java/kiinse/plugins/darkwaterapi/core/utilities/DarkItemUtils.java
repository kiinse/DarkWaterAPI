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

package kiinse.plugins.darkwaterapi.core.utilities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.utilities.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DarkItemUtils implements ItemStackUtils {

    private final DarkWaterJavaPlugin plugin;

    public DarkItemUtils(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String name, @Nullable List<String> lore, int amount) {
        var item = new ItemStack(material);
        var meta = item.getItemMeta();
        if (meta != null) {
            if (!DarkUtils.isStringEmpty(name)) meta.setDisplayName(name);
            if (lore != null) meta.setLore(lore);
            item.setItemMeta(meta);
            item.setAmount(amount);
        }
        return item;
    }

    @Override
    public @NotNull ItemStack getItemStack(@NotNull Material material, int amount, @NotNull Consumer<ItemMeta> meta) {
        var item = new ItemStack(material);
        var itemMeta = item.getItemMeta();
        meta.accept(itemMeta);
        item.setItemMeta(itemMeta);
        item.setAmount(amount);
        return item;
    }

    @Override
    public @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String name, @Nullable List<String> lore, int amount,
                                           @NotNull Consumer<ItemMeta> meta) {
        var item = new ItemStack(material);
        var itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
            if (lore != null) itemMeta.setLore(lore);
            meta.accept(itemMeta);
            item.setItemMeta(itemMeta);
            item.setAmount(amount);
        }
        return item;
    }

    @Override
    public @NotNull ItemStack getPotionItemStack(@Nullable String name, @Nullable List<String> lore, @NotNull PotionType type, int amount) {
        var item = new ItemStack(Material.POTION);
        var meta = item.getItemMeta();
        if (meta != null) {
            if (!DarkUtils.isStringEmpty(name)) meta.setDisplayName(name);
            if (lore != null) meta.setLore(lore);
            ((PotionMeta) meta).setBasePotionData(new PotionData(type));
        }
        item.setAmount(amount);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public @NotNull ItemStack getPotionItemStack(@NotNull PotionType type, int amount, @NotNull Consumer<ItemMeta> meta) {
        var item = new ItemStack(Material.POTION);
        var itemMeta = item.getItemMeta();
        if (itemMeta != null) ((PotionMeta) itemMeta).setBasePotionData(new PotionData(type));
        meta.accept(itemMeta);
        item.setAmount(amount);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public @NotNull ItemStack getPotionItemStack(@Nullable String name, @Nullable List<String> lore, @NotNull PotionType type, int amount,
                                                 @NotNull Consumer<ItemMeta> meta) {
        var item = new ItemStack(Material.POTION);
        var itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (!DarkUtils.isStringEmpty(name)) itemMeta.setDisplayName(name);
            if (lore != null) itemMeta.setLore(lore);
            ((PotionMeta) itemMeta).setBasePotionData(new PotionData(type));
            meta.accept(itemMeta);
        }
        item.setAmount(amount);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public @NotNull FurnaceRecipe getFurnaceRecipe(@NotNull String key, @NotNull ItemStack result, float experience, int cookingTime) {
        return new FurnaceRecipe(new NamespacedKey(plugin, key), result, result.getType(), experience, cookingTime);
    }

    @Override
    public @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient1,
                                                       @NotNull ItemStack ingredient2) {
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
    public @NotNull ItemStack getPlayerHead(@NotNull Player player) {
        return crateHead(player, meta -> {});
    }

    @Override
    public @NotNull ItemStack getPlayerHead(@NotNull Player player, @NotNull Consumer<SkullMeta> meta) {
        return crateHead(player, meta);
    }

    @Override
    public @NotNull ItemStack getPlayerHead(@NotNull OfflinePlayer player) {
        return crateHead(player, meta -> {});
    }

    @Override
    public @NotNull ItemStack getPlayerHead(@NotNull OfflinePlayer player, @NotNull Consumer<SkullMeta> meta) {
        return crateHead(player, meta);
    }

    private @NotNull ItemStack crateHead(@NotNull OfflinePlayer player, @NotNull Consumer<SkullMeta> meta) {
        var item = new ItemStack(Material.PLAYER_HEAD, 1);
        var skull = (SkullMeta) item.getItemMeta();
        if (skull != null) {
            skull.setOwningPlayer(player);
            meta.accept(skull);
            item.setItemMeta(skull);
        }
        return item;
    }

    @Override
    public @NotNull ItemStack getPlayerHead(@NotNull UUID player) throws IOException {
        return makeSkull(DarkPlayerUtils.getPlayerTextures(player.toString()));
    }

    @Override
    public @NotNull ItemStack getPlayerHead(String player) throws IOException {
        return makeSkull(DarkPlayerUtils.getPlayerTextures(DarkPlayerUtils.getPlayerID(player)));
    }

    @Override
    public @NotNull ItemStack makeSkull(@NotNull String base64) {
        var skull = new ItemStack(Material.PLAYER_HEAD);
        var meta = (SkullMeta) skull.getItemMeta();
        var profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));
        if (meta != null) {
            try {
                var profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                plugin.sendLog("There was an &cerror &6getting the custom head! Message:", e);
            }
        }
        skull.setItemMeta(meta);
        return skull;
    }

    @Override
    public boolean checkItemStack(@NotNull ItemStack stack, @NotNull Material material) {
        return stack.getType() == material;
    }
}

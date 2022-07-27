package kiinse.plugins.api.darkwaterapi.utilities.interfaces;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface ItemStackUtils {

    @NotNull ItemStack getItemStack(@NotNull Material material, @Nullable String name, @Nullable List<Component> lore, int amount);

    @NotNull ItemStack getPotionItemStack(@Nullable String name, @Nullable List<Component> lore, @NotNull PotionType type, int amount);

    @NotNull FurnaceRecipe getFurnaceRecipe(@NotNull String key, @NotNull ItemStack result, float experience, int cookingTime);

    @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient1, @NotNull ItemStack ingredient2);

    @NotNull ShapelessRecipe getShapelessRecipe(@NotNull String key, @NotNull ItemStack result, @NotNull ItemStack ingredient);

    @NotNull ShapedRecipe getShapedRecipe(@NotNull String key, @NotNull ItemStack result);

    @NotNull ItemStack getPlayerHead(@NotNull Player player, @NotNull String displayName, @NotNull List<Component> lore);

    @NotNull ItemStack getPlayerHead(@NotNull UUID player, @NotNull String displayName, @NotNull List<Component> lore);

    boolean checkItemStack(@NotNull ItemStack stack, @NotNull Material material);
}

package kiinse.plugins.api.darkwaterapi.utilities.interfaces;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface ItemStackUtils {

    ItemStack getItemStack(Material material, String name, List<Component> lore, int amount);

    ItemStack getPotionItemStack(String name, List<Component> lore, PotionType type, int amount);

    FurnaceRecipe getFurnaceRecipe(String key, ItemStack result, float experience, int cookingTime);

    ShapelessRecipe getShapelessRecipe(String key, ItemStack result, ItemStack ingredient1, ItemStack ingredient2);

    ShapelessRecipe getShapelessRecipe(String key, ItemStack result, ItemStack ingredient);

    ShapedRecipe getShapedRecipe(String key, ItemStack result);

    ItemStack getPlayerHead(Player player, String displayName, List<Component> lore);

    ItemStack getPlayerHead(UUID player, String displayName, List<Component> lore);

    boolean checkItemStack(ItemStack stack, Material material);
}

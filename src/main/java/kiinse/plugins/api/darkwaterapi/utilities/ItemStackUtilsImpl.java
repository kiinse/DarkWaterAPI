package kiinse.plugins.api.darkwaterapi.utilities;

import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
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

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class ItemStackUtilsImpl implements ItemStackUtils {

    private final DarkWaterJavaPlugin plugin;

    public ItemStackUtilsImpl(DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public ItemStack getItemStack(Material material, String name, List<Component> lore, int amount) {
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
    public ItemStack getPotionItemStack(String name, List<Component> lore, PotionType type, int amount) {
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
    public FurnaceRecipe getFurnaceRecipe(String key, ItemStack result, float experience, int cookingTime) {
        return new FurnaceRecipe(new NamespacedKey(plugin, key), result, result.getType(), experience, cookingTime);
    }

    @Override
    public ShapelessRecipe getShapelessRecipe(String key, ItemStack result, ItemStack ingredient1, ItemStack ingredient2) {
        var recipe = new ShapelessRecipe(new NamespacedKey(plugin, key), result);
        recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient1));
        recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient2));
        return recipe;
    }

    @Override
    public ShapelessRecipe getShapelessRecipe(String key, ItemStack result, ItemStack ingredient) {
        var recipe = new ShapelessRecipe(new NamespacedKey(plugin, key), result);
        recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient));
        return recipe;
    }

    @Override
    public ShapedRecipe getShapedRecipe(String key, ItemStack result) {
        return new ShapedRecipe(new NamespacedKey(plugin, key), result);
    }

    @Override
    public ItemStack getPlayerHead(Player player, String displayName, List<Component> lore) {
        var item = new ItemStack(Material.PLAYER_HEAD, 1);
        var skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(player);
        skull.displayName(Component.text(DarkWaterUtils.colorize(displayName)));
        skull.lore(lore);
        item.setItemMeta(skull);
        return item;
    }

    @Override
    public ItemStack getPlayerHead(UUID player, String displayName, List<Component> lore) {
        var item = new ItemStack(Material.PLAYER_HEAD, 1);
        var skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(player));
        skull.displayName(Component.text(DarkWaterUtils.colorize(displayName)));
        skull.lore(lore);
        item.setItemMeta(skull);
        return item;
    }

    @Override
    public boolean checkItemStack(ItemStack stack, Material material) {
        return stack.getType() == material;
    }
}

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
package kiinse.me.plugins.darkwaterapi.core.utilities

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.utilities.ItemStackUtils
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.*
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType
import java.io.IOException
import java.util.*
import java.util.function.Consumer

@Suppress("unused")
class DarkItemUtils(plugin: DarkWaterJavaPlugin) : ItemStackUtils {
    private val plugin: DarkWaterJavaPlugin

    init {
        this.plugin = plugin
    }

    override fun getItemStack(material: Material, name: String?, lore: List<String?>?, amount: Int): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        if (meta != null) {
            if (!name.isNullOrEmpty()) meta.setDisplayName(name)
            if (lore != null) meta.lore = lore
            item.itemMeta = meta
            item.amount = amount
        }
        return item
    }

    override fun getItemStack(material: Material, amount: Int, meta: Consumer<ItemMeta?>): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        meta.accept(itemMeta)
        item.itemMeta = itemMeta
        item.amount = amount
        return item
    }

    override fun getItemStack(material: Material, name: String?, lore: List<String?>?, amount: Int, meta: Consumer<ItemMeta?>): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        if (itemMeta != null) {
            itemMeta.setDisplayName(name)
            if (lore != null) itemMeta.lore = lore
            meta.accept(itemMeta)
            item.itemMeta = itemMeta
            item.amount = amount
        }
        return item
    }

    override fun getPotionItemStack(name: String?, lore: List<String?>?, type: PotionType, amount: Int): ItemStack {
        val item = ItemStack(Material.POTION)
        val meta = item.itemMeta
        if (meta != null) {
            if (!name.isNullOrEmpty()) meta.setDisplayName(name)
            if (lore != null) meta.lore = lore
            (meta as PotionMeta).basePotionData = PotionData(type)
        }
        item.amount = amount
        item.itemMeta = meta
        return item
    }

    override fun getPotionItemStack(type: PotionType, amount: Int, meta: Consumer<ItemMeta?>): ItemStack {
        val item = ItemStack(Material.POTION)
        val itemMeta = item.itemMeta
        if (itemMeta != null) (itemMeta as PotionMeta).basePotionData = PotionData(type)
        meta.accept(itemMeta)
        item.amount = amount
        item.itemMeta = itemMeta
        return item
    }

    override fun getPotionItemStack(name: String?, lore: List<String?>?, type: PotionType, amount: Int, meta: Consumer<ItemMeta?>): ItemStack {
        val item = ItemStack(Material.POTION)
        val itemMeta = item.itemMeta
        if (itemMeta != null) {
            if (!name.isNullOrEmpty()) itemMeta.setDisplayName(name)
            if (lore != null) itemMeta.lore = lore
            (itemMeta as PotionMeta).basePotionData = PotionData(type)
            meta.accept(itemMeta)
        }
        item.amount = amount
        item.itemMeta = itemMeta
        return item
    }

    override fun getFurnaceRecipe(key: String, result: ItemStack, experience: Float, cookingTime: Int): FurnaceRecipe {
        return FurnaceRecipe(NamespacedKey(plugin, key), result, result.type, experience, cookingTime)
    }

    override fun getShapelessRecipe(key: String, result: ItemStack, ingredient1: ItemStack, ingredient2: ItemStack): ShapelessRecipe {
        val recipe = ShapelessRecipe(NamespacedKey(plugin, key), result)
        recipe.addIngredient(RecipeChoice.ExactChoice(ingredient1))
        recipe.addIngredient(RecipeChoice.ExactChoice(ingredient2))
        return recipe
    }

    override fun getShapelessRecipe(key: String, result: ItemStack, ingredient: ItemStack): ShapelessRecipe {
        val recipe = ShapelessRecipe(NamespacedKey(plugin, key), result)
        recipe.addIngredient(RecipeChoice.ExactChoice(ingredient))
        return recipe
    }

    override fun getShapedRecipe(key: String, result: ItemStack): ShapedRecipe {
        return ShapedRecipe(NamespacedKey(plugin, key), result)
    }

    override fun getPlayerHead(player: Player): ItemStack {
        return createHead(player) { }
    }

    override fun getPlayerHead(player: Player, meta: Consumer<SkullMeta>): ItemStack {
        return createHead(player, meta)
    }

    override fun getPlayerHead(player: OfflinePlayer): ItemStack {
        return createHead(player) { }
    }

    override fun getPlayerHead(player: OfflinePlayer, meta: Consumer<SkullMeta>): ItemStack {
        return createHead(player, meta)
    }

    private fun createHead(player: OfflinePlayer, meta: Consumer<SkullMeta>): ItemStack {
        val item = ItemStack(Material.PLAYER_HEAD, 1)
        val skull = item.itemMeta
        if (skull is SkullMeta) {
            skull.owningPlayer = player
            meta.accept(skull)
            item.itemMeta = skull
        }
        return item
    }

    @Throws(IOException::class)
    override fun getPlayerHead(player: UUID): ItemStack {
        return makeSkull(DarkPlayerUtils.getPlayerTextures(player.toString()))
    }

    @Throws(IOException::class)
    override fun getPlayerHead(player: String): ItemStack {
        return makeSkull(DarkPlayerUtils.getPlayerTextures(DarkPlayerUtils.getPlayerID(player)))
    }

    override fun makeSkull(base64: String): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)
        val meta = skull.itemMeta
        val profile = GameProfile(UUID.randomUUID(), null)
        profile.properties.put("textures", Property("textures", base64))
        if (meta is SkullMeta) {
            try {
                val profileField = meta.javaClass.getDeclaredField("profile")
                profileField.isAccessible = true
                profileField[meta] = profile
            } catch (e: NoSuchFieldException) {
                plugin.sendLog("There was an &cerror &6getting the custom head! Message:", e)
            } catch (e: IllegalAccessException) {
                plugin.sendLog("There was an &cerror &6getting the custom head! Message:", e)
            }
            skull.itemMeta = meta
        }
        return skull
    }

    override fun checkItemStack(stack: ItemStack, material: Material): Boolean {
        return stack.type == material
    }
}
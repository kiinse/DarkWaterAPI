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
package kiinse.me.plugins.darkwaterapi.api.utilities

import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionType
import java.io.IOException
import java.util.*
import java.util.function.Consumer

@Suppress("unused")
interface ItemStackUtils {
    fun getItemStack(material: Material, name: String?, lore: List<String?>?, amount: Int): ItemStack
    fun getItemStack(material: Material, amount: Int, meta: Consumer<ItemMeta?>): ItemStack
    fun getItemStack(material: Material, name: String?, lore: List<String?>?, amount: Int, meta: Consumer<ItemMeta?>): ItemStack
    fun getPotionItemStack(name: String?, lore: List<String?>?, type: PotionType, amount: Int): ItemStack
    fun getPotionItemStack(type: PotionType, amount: Int, meta: Consumer<ItemMeta?>): ItemStack
    fun getPotionItemStack(name: String?, lore: List<String?>?, type: PotionType, amount: Int, meta: Consumer<ItemMeta?>): ItemStack
    fun getFurnaceRecipe(key: String, result: ItemStack, experience: Float, cookingTime: Int): FurnaceRecipe
    fun getShapelessRecipe(key: String, result: ItemStack, ingredient1: ItemStack, ingredient2: ItemStack): ShapelessRecipe
    fun getShapelessRecipe(key: String, result: ItemStack, ingredient: ItemStack): ShapelessRecipe
    fun getShapedRecipe(key: String, result: ItemStack): ShapedRecipe
    fun getPlayerHead(player: Player): ItemStack
    fun getPlayerHead(player: Player, meta: Consumer<SkullMeta>): ItemStack
    fun getPlayerHead(player: OfflinePlayer): ItemStack
    fun getPlayerHead(player: OfflinePlayer, meta: Consumer<SkullMeta>): ItemStack
    @Throws(IOException::class) fun getPlayerHead(player: UUID): ItemStack
    @Throws(IOException::class) fun getPlayerHead(player: String): ItemStack
    fun makeSkull(base64: String): ItemStack
    fun checkItemStack(stack: ItemStack, material: Material): Boolean
}
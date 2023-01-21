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
package kiinse.me.plugins.darkwaterapi.core.gui

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale
import kiinse.me.plugins.darkwaterapi.api.gui.GuiAction
import kiinse.me.plugins.darkwaterapi.api.gui.GuiItem
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkPlayerUtils
import kiinse.me.plugins.darkwaterapi.core.utilities.DarkUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.*

@Suppress("unused")
abstract class DarkGUI protected constructor(private val plugin: DarkWaterJavaPlugin) : InventoryHolder {

    val uuid: UUID = UUID.randomUUID()
    private val actions: MutableMap<Int, GuiAction> = HashMap<Int, GuiAction>()
    var playerLocale: PlayerLocale? = null
    private var inventory: Inventory? = null
    var name: String = uuid.toString()
    var size: Int = 36
        set(size) {
            field = if (size <= 0) 36 else size
        }

    override fun getInventory(): Inventory {
        return inventory!!
    }

    init {
        inventoriesByUUID[uuid] = this
    }

    protected fun setItem(item: GuiItem): DarkGUI {
        val stack = item.itemStack()
        val slot = item.slot()
        val meta = stack.itemMeta
        meta?.setDisplayName(DarkUtils.colorize(item.name()))
        stack.itemMeta = meta
        this.inventory?.setItem(slot, stack)
        actions[slot] = item.action()
        return this
    }

    fun getActions(): Map<Int, GuiAction> {
        return actions
    }

    fun open(player: Player) {
        if (playerLocale == null) playerLocale = plugin.darkWaterAPI.playerLocales.getLocale(player)
        this.inventory = Bukkit.createInventory(this, size, DarkUtils.colorize(name))
        onInventoryCreate(plugin)
        player.openInventory(this.inventory!!)
        openInventories[player.uniqueId] = uuid
    }

    fun open(sender: CommandSender) {
        if (playerLocale == null) playerLocale = plugin.darkWaterAPI.playerLocales.getLocale(sender)
        this.inventory = Bukkit.createInventory(this, size, DarkUtils.colorize(name))
        val player = DarkPlayerUtils.getPlayer(sender)
        onInventoryCreate(plugin)
        player.openInventory(this.inventory!!)
        openInventories[player.uniqueId] = uuid
    }

    protected abstract fun onInventoryCreate(plugin: DarkWaterJavaPlugin)

    fun setRows(rows: Int): DarkGUI {
        size = if (rows <= 0) 36 else rows * 9
        return this
    }

    protected fun getPlugin(): DarkWaterJavaPlugin {
        return plugin
    }

    fun delete(): DarkGUI {
        for (player in Bukkit.getOnlinePlayers()) {
            val playerUniqueId: UUID = player.uniqueId
            if (openInventories.containsKey(playerUniqueId) && openInventories[playerUniqueId] == uuid) {
                openInventories.remove(playerUniqueId)
            }
        }
        inventoriesByUUID.remove(uuid)
        return this
    }

    companion object {
        val inventoriesByUUID: MutableMap<UUID, DarkGUI> = HashMap()
        val openInventories: MutableMap<UUID, UUID> = HashMap()
    }
}
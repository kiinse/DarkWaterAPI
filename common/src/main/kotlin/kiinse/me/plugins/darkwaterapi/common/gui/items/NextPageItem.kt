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
package kiinse.me.plugins.darkwaterapi.common.gui.items

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import kiinse.me.plugins.darkwaterapi.api.files.locale.PlayerLocale
import kiinse.me.plugins.darkwaterapi.api.files.messages.Message
import kiinse.me.plugins.darkwaterapi.api.gui.GuiAction
import kiinse.me.plugins.darkwaterapi.api.gui.GuiItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class NextPageItem(val plugin: DarkWaterJavaPlugin, playerLocale: PlayerLocale, val action: GuiAction) : GuiItem {

    private val name: String = plugin.getMessages().getStringMessage(playerLocale, Message.GUI_NEXT_PAGE)

    override fun slot(): Int {
        return 32
    }

    override fun itemStack(): ItemStack {
        return ItemStack(Material.SPECTRAL_ARROW)
    }

    override fun name(): String {
        return name
    }

    override fun action(): GuiAction {
        return action
    }
}
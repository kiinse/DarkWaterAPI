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

package kiinse.plugins.darkwaterapi.core.gui;

import kiinse.plugins.darkwaterapi.core.utilities.DarkWaterUtils;
import kiinse.plugins.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.darkwaterapi.api.gui.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class GUI {

    private final UUID uuid;
    private final Inventory inv;
    private final Map<Integer, GuiAction> actions;
    protected static final Map<UUID, GUI> inventoriesByUUID = new HashMap<>();
    protected static final Map<UUID, UUID> openInventories = new HashMap<>();

    public GUI(int size, @NotNull String name) {
        uuid = UUID.randomUUID();
        inv = Bukkit.createInventory(null, size, DarkWaterUtils.colorize(name));
        actions = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }

    public @NotNull UUID getUuid() {
        return uuid;
    }

    public @NotNull Inventory getInventory() {
        return inv;
    }

    public @NotNull GUI setItem(@NotNull GuiItem item){
        var stack = item.itemStack();
        var slot = item.slot();
        var meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(DarkWaterUtils.colorize(item.name()));
        stack.setItemMeta(meta);
        inv.setItem(slot, stack);
        actions.put(slot, item.action());
        return this;
    }

    public void open(@NotNull Player player){
        player.openInventory(inv);
        openInventories.put(player.getUniqueId(), getUuid());
    }

    public static @NotNull Map<UUID, GUI> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static @NotNull Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public @NotNull Map<Integer, GuiAction> getActions() {
        return actions;
    }

    public @NotNull GUI delete(){
        for (var player : Bukkit.getOnlinePlayers()) {
            if (openInventories.get(player.getUniqueId()).equals(getUuid())){
                player.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUuid());
        return this;
    }

}
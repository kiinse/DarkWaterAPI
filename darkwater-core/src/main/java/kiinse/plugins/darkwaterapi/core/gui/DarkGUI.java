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

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import kiinse.plugins.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class DarkGUI implements InventoryHolder {

    protected static final Map<UUID, DarkGUI> inventoriesByUUID = new HashMap<>();
    protected static final Map<UUID, UUID> openInventories = new HashMap<>();
    private final UUID uuid;
    private final Map<Integer, GuiAction> actions;
    private final DarkWaterJavaPlugin plugin;
    private PlayerLocale playerLocale;
    private Inventory inv;
    private String name;
    private int size;

    protected DarkGUI(DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
        uuid = UUID.randomUUID();
        actions = new HashMap<>();
        this.name = uuid.toString();
        this.size = 36;
        inventoriesByUUID.put(getUuid(), this);
    }

    public static @NotNull Map<UUID, DarkGUI> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static @NotNull Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public @NotNull UUID getUuid() {
        return uuid;
    }

    public @NotNull Inventory getInventory() {
        return inv;
    }

    protected @NotNull DarkGUI setItem(@NotNull GuiItem item) {
        var stack = item.itemStack();
        var slot = item.slot();
        var meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(DarkUtils.colorize(item.name()));
        stack.setItemMeta(meta);
        inv.setItem(slot, stack);
        actions.put(slot, item.action());
        return this;
    }

    public @NotNull Map<Integer, GuiAction> getActions() {
        return actions;
    }

    public void open(@NotNull Player player) {
        if (this.playerLocale == null) this.playerLocale = plugin.getDarkWaterAPI().getPlayerLocales().getLocale(player);
        this.inv = Bukkit.createInventory(this, size, DarkUtils.colorize(name));
        inventory(plugin);
        player.openInventory(inv);
        openInventories.put(player.getUniqueId(), getUuid());
    }

    public void open(@NotNull CommandSender sender) {
        if (this.playerLocale == null) this.playerLocale = plugin.getDarkWaterAPI().getPlayerLocales().getLocale(sender);
        this.inv = Bukkit.createInventory(this, size, DarkUtils.colorize(name));
        var player = DarkPlayerUtils.getPlayer(sender);
        inventory(plugin);
        player.openInventory(inv);
        openInventories.put(player.getUniqueId(), getUuid());
    }

    protected abstract void inventory(@NotNull DarkWaterJavaPlugin plugin);

    public @NotNull DarkGUI setRows(int rows) {
        this.size = rows <= 0 ? 36 : rows * 9;
        return this;
    }

    public @NotNull DarkGUI setLocale(@NotNull PlayerLocale playerLocale) {
        this.playerLocale = playerLocale;
        return this;
    }

    protected @NotNull PlayerLocale getPlayerLocale() {
        return playerLocale;
    }

    protected @NotNull DarkWaterJavaPlugin getPlugin() {
        return plugin;
    }

    protected @NotNull String getName() {
        return name;
    }

    public @NotNull DarkGUI setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    protected int getSize() {
        return size;
    }

    public @NotNull DarkGUI setSize(int size) {
        this.size = size <= 0 ? 36 : size;
        return this;
    }

    public @NotNull DarkGUI delete() {
        for (var player : Bukkit.getOnlinePlayers()) {
            if (openInventories.get(player.getUniqueId()).equals(getUuid())) player.closeInventory();
        }
        inventoriesByUUID.remove(getUuid());
        return this;
    }

}
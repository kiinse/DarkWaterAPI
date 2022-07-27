package kiinse.plugins.api.darkwaterapi.gui;

import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import net.kyori.adventure.text.Component;
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
        inv = Bukkit.createInventory(null, size, Component.text(DarkWaterUtils.colorize(name)));
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
        meta.displayName(Component.text(DarkWaterUtils.colorize(item.name())));
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
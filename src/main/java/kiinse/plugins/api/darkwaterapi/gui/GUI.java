package kiinse.plugins.api.darkwaterapi.gui;

import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUI {

    // TODO: Довести до умна, переработать логику, добавить аннотации.
    // TODO: Сделать отдельный файл для GUI с именами или добавить JSONObject в en.json и ru.json под ключом "gui"

    private final UUID uuid;
    private final Inventory inv;
    private final Map<Integer, GuiAction> actions;
    protected static final Map<UUID, GUI> inventoriesByUUID = new HashMap<>();
    protected static final Map<UUID, UUID> openInventories = new HashMap<>();

    public GUI(int size, String name) {
        uuid = UUID.randomUUID();
        inv = Bukkit.createInventory(null, size, Component.text(DarkWaterUtils.colorize(name)));
        actions = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }

    public UUID getUuid() {
        return uuid;
    }

    @SuppressWarnings("unused")
    public Inventory getInventory() {
        return inv;
    }

    public void setItem(GuiItem item){
        var stack = item.itemStack();
        var slot = item.slot();
        var action = item.action();
        var meta = stack.getItemMeta();
        assert meta != null;
        meta.displayName(Component.text(DarkWaterUtils.colorize(item.name())));
        stack.setItemMeta(meta);
        inv.setItem(slot, stack);
        if (action != null){
            actions.put(slot, action);
        }
    }

    public void open(Player player){
        player.openInventory(inv);
        openInventories.put(player.getUniqueId(), getUuid());
    }

    public static Map<UUID, GUI> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    @SuppressWarnings("unused")
    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public Map<Integer, GuiAction> getActions() {
        return actions;
    }

    public void delete(){
        for (var player : Bukkit.getOnlinePlayers()) {
            if (openInventories.get(player.getUniqueId()).equals(getUuid())){
                player.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUuid());
    }

}
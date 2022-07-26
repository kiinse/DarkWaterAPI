package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.gui.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInventoryListener implements Listener {

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        var player = event.getPlayer();
        var inventoryUUID = GUI.getOpenInventories().get(player.getUniqueId());
        if (inventoryUUID != null) {
            GUI.getInventoriesByUUID().remove(inventoryUUID);
            GUI.getOpenInventories().remove(player.getUniqueId());
        }
    }
}

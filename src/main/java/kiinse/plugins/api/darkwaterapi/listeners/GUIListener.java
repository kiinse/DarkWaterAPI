package kiinse.plugins.api.darkwaterapi.listeners;

import kiinse.plugins.api.darkwaterapi.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent e){
        if (!(e.getWhoClicked() instanceof Player player)){
            return;
        }
        var inventoryUUID = GUI.getOpenInventories().get(player.getUniqueId());
        if (inventoryUUID != null){
            e.setCancelled(true);
            var action = GUI.getInventoriesByUUID().get(inventoryUUID).getActions().get(e.getSlot());
            if (action != null){
                action.click(player);
            }
        }
    }

}

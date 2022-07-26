package kiinse.plugins.api.darkwaterapi.gui.interfaces;

import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public interface GuiItem {

    int slot();

    ItemStack itemStack();

    String name();

    GuiAction action();
}

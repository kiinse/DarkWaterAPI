package kiinse.plugins.api.darkwaterapi.gui.interfaces;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface GuiItem {

    int slot();

    @NotNull ItemStack itemStack();

    @NotNull String name();

    @NotNull GuiAction action();
}

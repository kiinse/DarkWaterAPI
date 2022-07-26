package kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items;

import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import org.bukkit.inventory.ItemStack;

public class LocaleItem implements GuiItem {

    private final int slot;
    private final String name;
    private final ItemStack stack;
    private final GuiAction action;

    public LocaleItem(int slot, String name, ItemStack stack, GuiAction action) {
        this.slot = slot;
        this.name = name;
        this.stack = stack;
        this.action = action;
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public ItemStack itemStack() {
        return stack;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public GuiAction action() {
        return action;
    }
}

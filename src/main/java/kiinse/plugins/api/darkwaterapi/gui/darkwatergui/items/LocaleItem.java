package kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items;

import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LocaleItem implements GuiItem {

    private final int slot;
    private final String name;
    private final ItemStack stack;
    private final GuiAction action;

    public LocaleItem(int slot, @NotNull String name, @NotNull ItemStack stack, @NotNull GuiAction action) {
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
    public @NotNull ItemStack itemStack() {
        return stack;
    }

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull GuiAction action() {
        return action;
    }
}

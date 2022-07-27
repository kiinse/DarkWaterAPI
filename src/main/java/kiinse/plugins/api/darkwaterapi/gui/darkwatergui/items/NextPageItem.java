package kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NextPageItem implements GuiItem {
    private final String name;
    private final GuiAction action;

    public NextPageItem(@NotNull DarkWaterAPI darkWaterAPI, @NotNull Locale locale, @NotNull GuiAction action) {
        this.name = darkWaterAPI.getMessages().getStringMessage(locale, Message.GUI_NEXT_PAGE);
        this.action = action;
    }

    @Override
    public int slot() {
        return 32;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return new ItemStack(Material.SPECTRAL_ARROW);
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

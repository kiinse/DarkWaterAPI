package kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Message;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExitItem implements GuiItem {
    private final String name;
    private final GuiAction action;

    public ExitItem(DarkWaterAPI darkWaterAPI, Locale locale, GuiAction action) {
        this.name = darkWaterAPI.getMessages().getStringMessage(locale, Message.EXIT);
        this.action = action;
    }

    @Override
    public int slot() {
        return 35;
    }

    @Override
    public ItemStack itemStack() {
        return new ItemStack(Material.BARRIER);
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

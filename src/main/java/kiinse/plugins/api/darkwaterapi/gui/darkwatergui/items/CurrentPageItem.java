package kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.utils.Replace;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CurrentPageItem implements GuiItem {

    private final String name;
    private final GuiAction action;

    public CurrentPageItem(DarkWaterAPI darkWaterAPI, Locale locale, int page, GuiAction action) {
        this.name = DarkWaterUtils.replaceWord(darkWaterAPI.getMessages().getStringMessage(locale, Message.GUI_CURRENT_PAGE), Replace.PAGE, String.valueOf(page));
        this.action = action;
    }

    @Override
    public int slot() {
        return 31;
    }

    @Override
    public ItemStack itemStack() {
        return new ItemStack(Material.NETHER_STAR);
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

package kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Replace;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiAction;
import kiinse.plugins.api.darkwaterapi.gui.interfaces.GuiItem;
import kiinse.plugins.api.darkwaterapi.utilities.DarkWaterUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CurrentPageItem implements GuiItem {

    private final String name;
    private final GuiAction action;

    public CurrentPageItem(@NotNull DarkWaterAPI darkWaterAPI, @NotNull Locale locale, int page, @NotNull GuiAction action) {
        this.name = DarkWaterUtils.replaceWord(darkWaterAPI.getMessages().getStringMessage(locale, Message.GUI_CURRENT_PAGE), Replace.PAGE, String.valueOf(page));
        this.action = action;
    }

    @Override
    public int slot() {
        return 31;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return new ItemStack(Material.NETHER_STAR);
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

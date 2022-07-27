package kiinse.plugins.api.darkwaterapi.gui.darkwatergui;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.files.locale.Locale;
import kiinse.plugins.api.darkwaterapi.files.locale.interfaces.LocaleStorage;
import kiinse.plugins.api.darkwaterapi.files.messages.enums.Message;
import kiinse.plugins.api.darkwaterapi.gui.GUI;
import kiinse.plugins.api.darkwaterapi.gui.darkwatergui.items.*;
import kiinse.plugins.api.darkwaterapi.utilities.PlayerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
public class LocaleGUI extends GUI {
    private final LocaleStorage localeStorage = DarkWaterAPI.getInstance().getLocaleStorage();
    private final DarkWaterAPI darkWaterAPI = DarkWaterAPI.getInstance();
    private final Locale playerLocale;
    private final String name;

    public LocaleGUI(int page, @NotNull String name, @NotNull Locale playerLocale) {
        super(36, name);
        this.name = name;
        this.playerLocale = playerLocale;
        var locales = getGuiPages();
        addItems(page, locales)
                .setPreviousPageItem(page, locales)
                .setCurrentPageItem(page, locales)
                .setNextPageItem(page, locales)
                .setExitItem();
    }

    private @NotNull LocaleGUI addItems(int page, @NotNull Map<Integer, List<Locale>> locales) {
        var messages = darkWaterAPI.getMessages();
        int position = 9;
        for (var locale : locales.get(page)) {
            var item = new ItemStack(Material.GOLD_BLOCK);
            var list = new ArrayList<Component>();
            var meta = item.getItemMeta();
            list.add(messages.getComponentMessage(playerLocale, Message.SET_THIS_LOCALE));
            meta.lore(list);
            item.setItemMeta(meta);
            setItem(new LocaleItem(position, "&f" + locale.toString(), item, player -> {
                player.performCommand("locale set " + locale);
                delete();
            }));
            position++;
        }
        return this;
    }

    private @NotNull LocaleGUI setPreviousPageItem(int page, @NotNull Map<Integer, List<Locale>> locales) {
        if (locales.containsKey(page-1)) {
            setItem(new PreviousPageItem(darkWaterAPI, playerLocale, (player -> {
                delete();
                PlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
                new LocaleGUI(page-1, name, playerLocale).open(player);
            })));
        }
        return this;
    }

    private @NotNull LocaleGUI setCurrentPageItem(int page, @NotNull Map<Integer, List<Locale>> locales) {
        if (locales.size() > 1) {
            setItem(new CurrentPageItem(darkWaterAPI, playerLocale, page, (player -> {})));
        }
        return this;
    }

    private @NotNull LocaleGUI setNextPageItem(int page, @NotNull Map<Integer, List<Locale>> locales) {
        if (locales.containsKey(page+1)) {
            setItem(new NextPageItem(darkWaterAPI, playerLocale, (player -> {
                delete();
                PlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
                new LocaleGUI(page+1, name, playerLocale).open(player);
            })));
        }
        return this;
    }

    private @NotNull LocaleGUI setExitItem() {
        setItem(new ExitItem(darkWaterAPI, playerLocale, (player -> {
            delete();
            PlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
        })));
        return this;
    }

    public @NotNull Map<Integer, List<Locale>> getGuiPages() {
        var hashmap = new HashMap<Integer, List<Locale>>();
        var list = new ArrayList<Locale>();
        int localesCount = 0;
        int size = 0;
        int page = 1;
        for (var locale : localeStorage.getAllowedLocalesList()) {
            size++;
            list.add(locale);
            if (size == 9 || localesCount == localeStorage.getAllowedLocalesList().size()-1) {
                hashmap.put(page, new ArrayList<>(list));
                page++;
                size = 0;
                list.clear();
            }
            localesCount++;
        }
        return hashmap;
    }
}

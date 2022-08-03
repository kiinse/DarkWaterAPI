// MIT License
//
// Copyright (c) 2022 kiinse
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package kiinse.plugins.darkwaterapi.common.gui;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.files.locale.Locale;
import kiinse.plugins.darkwaterapi.common.gui.items.CurrentPageItem;
import kiinse.plugins.darkwaterapi.common.gui.items.ExitItem;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.common.gui.items.NextPageItem;
import kiinse.plugins.darkwaterapi.common.gui.items.PreviousPageItem;
import kiinse.plugins.darkwaterapi.common.gui.items.LocaleItem;
import kiinse.plugins.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.darkwaterapi.core.utilities.DarkPlayerUtils;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("UnusedReturnValue")
public class LocaleGUI extends DarkGUI {

    private static LocaleFlags localeFlags;
    private int page;

    public LocaleGUI(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    protected void inventory(@NotNull DarkWaterJavaPlugin plugin) {
        var locales = getGuiPages();
        addItems(locales)
                .setPreviousPageItem(locales)
                .setCurrentPageItem(locales)
                .setNextPageItem(locales)
                .setExitItem();
    }

    public @NotNull DarkGUI setPage(int page) {
        this.page = page;
        return this;
    }

    private @NotNull LocaleGUI addItems(@NotNull Map<Integer, HashSet<Locale>> locales) {
        int position = 9;
        for (var locale : locales.get(page)) {
            var item = localeFlags.getFlag(locale);
            var list = new ArrayList<String>();
            var meta = item.getItemMeta();
            list.add(getPlugin().getMessages().getStringMessage(locale, Message.SET_THIS_LOCALE));
            if (meta != null) {
                meta.setLore(list);
                item.setItemMeta(meta);
            }
            setItem(new LocaleItem(position, "&f" + locale, item, player -> {
                player.performCommand("locale set " + locale);
                delete();
            }));
            position++;
        }
        return this;
    }

    private @NotNull LocaleGUI setPreviousPageItem(@NotNull Map<Integer, HashSet<Locale>> locales) {
        if (locales.containsKey(page-1)) {
            setItem(new PreviousPageItem(getPlugin(), getPlayerLocale(), (player -> {
                delete();
                DarkPlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
                new LocaleGUI(getPlugin())
                        .setPage(page-1)
                        .setName(getName())
                        .setLocale(getPlayerLocale())
                        .open(player);
            })));
        }
        return this;
    }

    private @NotNull LocaleGUI setCurrentPageItem(@NotNull Map<Integer, HashSet<Locale>> locales) {
        if (locales.size() > 1) {
            setItem(new CurrentPageItem(getPlugin(), getPlayerLocale(), page, (player -> {})));
        }
        return this;
    }

    private @NotNull LocaleGUI setNextPageItem(@NotNull Map<Integer, HashSet<Locale>> locales) {
        if (locales.containsKey(page+1)) {
            setItem(new NextPageItem(getPlugin(), getPlayerLocale(), (player -> {
                delete();
                DarkPlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
                new LocaleGUI(getPlugin())
                        .setPage(page+1)
                        .setName(getName())
                        .setLocale(getPlayerLocale())
                        .open(player);
            })));
        }
        return this;
    }

    private @NotNull LocaleGUI setExitItem() {
        setItem(new ExitItem(getPlugin(), getPlayerLocale(), (player -> {
            delete();
            DarkPlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
        })));
        return this;
    }

    private @NotNull Map<Integer, HashSet<Locale>> getGuiPages() {
        var storage = getPlugin().getDarkWaterAPI().getLocaleStorage();
        var hashmap = new HashMap<Integer, HashSet<Locale>>();
        var list = new HashSet<Locale>();
        int localesCount = 0;
        int size = 0;
        int pageNum = 1;
        for (var locale : storage.getAllowedLocalesList()) {
            size++;
            list.add(locale);
            if (size == 9 || localesCount == storage.getAllowedLocalesList().size()-1) {
                hashmap.put(pageNum, new HashSet<>(list));
                pageNum++;
                size = 0;
                list.clear();
            }
            localesCount++;
        }
        return hashmap;
    }

    public static void setLocaleFlags(@NotNull LocaleFlags lcFlags) {
        localeFlags = lcFlags;
    }
}

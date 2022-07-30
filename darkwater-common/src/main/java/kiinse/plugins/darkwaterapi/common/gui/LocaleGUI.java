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

import kiinse.plugins.darkwaterapi.api.files.locale.Locale;
import kiinse.plugins.darkwaterapi.api.files.locale.LocaleStorage;
import kiinse.plugins.darkwaterapi.common.DarkWaterAPI;
import kiinse.plugins.darkwaterapi.common.gui.items.CurrentPageItem;
import kiinse.plugins.darkwaterapi.common.gui.items.ExitItem;
import kiinse.plugins.darkwaterapi.api.files.messages.Message;
import kiinse.plugins.darkwaterapi.common.gui.items.NextPageItem;
import kiinse.plugins.darkwaterapi.common.gui.items.PreviousPageItem;
import kiinse.plugins.darkwaterapi.common.gui.items.LocaleItem;
import kiinse.plugins.darkwaterapi.core.gui.GUI;
import kiinse.plugins.darkwaterapi.core.utilities.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    private @NotNull LocaleGUI addItems(int page, @NotNull Map<Integer, HashSet<Locale>> locales) {
        var messages = darkWaterAPI.getMessages();
        int position = 9;
        for (var locale : locales.get(page)) {
            var item = new ItemStack(Material.GOLD_BLOCK);
            var list = new ArrayList<String>();
            var meta = item.getItemMeta();
            list.add(messages.getStringMessage(playerLocale, Message.SET_THIS_LOCALE));
            if (meta != null) {
                meta.setLore(list);
                item.setItemMeta(meta);
            }
            setItem(new LocaleItem(position, "&f" + locale.toString(), item, player -> {
                player.performCommand("locale set " + locale);
                delete();
            }));
            position++;
        }
        return this;
    }

    private @NotNull LocaleGUI setPreviousPageItem(int page, @NotNull Map<Integer, HashSet<Locale>> locales) {
        if (locales.containsKey(page-1)) {
            setItem(new PreviousPageItem(darkWaterAPI, playerLocale, (player -> {
                delete();
                PlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP);
                new LocaleGUI(page-1, name, playerLocale).open(player);
            })));
        }
        return this;
    }

    private @NotNull LocaleGUI setCurrentPageItem(int page, @NotNull Map<Integer, HashSet<Locale>> locales) {
        if (locales.size() > 1) {
            setItem(new CurrentPageItem(darkWaterAPI, playerLocale, page, (player -> {})));
        }
        return this;
    }

    private @NotNull LocaleGUI setNextPageItem(int page, @NotNull Map<Integer, HashSet<Locale>> locales) {
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

    public @NotNull Map<Integer, HashSet<Locale>> getGuiPages() {
        var hashmap = new HashMap<Integer, HashSet<Locale>>();
        var list = new HashSet<Locale>();
        int localesCount = 0;
        int size = 0;
        int page = 1;
        for (var locale : localeStorage.getAllowedLocalesList()) {
            size++;
            list.add(locale);
            if (size == 9 || localesCount == localeStorage.getAllowedLocalesList().size()-1) {
                hashmap.put(page, new HashSet<>(list));
                page++;
                size = 0;
                list.clear();
            }
            localesCount++;
        }
        return hashmap;
    }
}

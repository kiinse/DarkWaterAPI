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
import kiinse.plugins.darkwaterapi.api.files.enums.File;
import kiinse.plugins.darkwaterapi.api.files.filemanager.FilesManager;
import kiinse.plugins.darkwaterapi.api.files.locale.PlayerLocale;
import kiinse.plugins.darkwaterapi.api.utilities.ItemStackUtils;
import kiinse.plugins.darkwaterapi.core.utilities.DarkItemUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LocaleFlags extends FilesManager {

    private final Properties properties;
    private final ItemStackUtils itemStackUtils;

    public LocaleFlags(@NotNull DarkWaterJavaPlugin plugin) throws IOException {
        super(plugin);
        if (isFileNotExists(File.LOCALES_PROPERTIES))
            copyFile(File.LOCALES_PROPERTIES);
        properties = new Properties();
        try (var reader = new FileReader(getFile(File.LOCALES_PROPERTIES))) {
            properties.load(reader);
        }
        itemStackUtils = new DarkItemUtils(plugin);
    }

    public @NotNull ItemStack getFlag(@NotNull PlayerLocale playerLocale) {
        return itemStackUtils.makeSkull(properties.getProperty("gui." + playerLocale, properties.getProperty("gui.default")));
    }
}

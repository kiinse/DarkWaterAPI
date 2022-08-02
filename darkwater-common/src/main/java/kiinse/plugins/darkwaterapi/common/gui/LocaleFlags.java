package kiinse.plugins.darkwaterapi.common.gui;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.files.enums.File;
import kiinse.plugins.darkwaterapi.api.files.filemanager.FilesManager;
import kiinse.plugins.darkwaterapi.api.files.locale.Locale;
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
        if (isFileNotExists(File.LOCALES_PROPERTIES)) {
            copyFile(File.LOCALES_PROPERTIES);
        }
        properties = new Properties();
        try (var reader = new FileReader(getFile(File.LOCALES_PROPERTIES))) {
            properties.load(reader);
        }
        itemStackUtils = new DarkItemUtils(plugin);
    }

    public @NotNull ItemStack getFlag(@NotNull Locale locale) {
        return itemStackUtils.makeSkull(properties.getProperty("gui." + locale, properties.getProperty("gui.default")));
    }
}

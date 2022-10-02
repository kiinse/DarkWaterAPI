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

package kiinse.plugins.darkwaterapi.api.files.filemanager;

import kiinse.plugins.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.darkwaterapi.api.exceptions.YamlFileException;
import kiinse.plugins.darkwaterapi.api.files.enums.Config;
import kiinse.plugins.darkwaterapi.api.files.enums.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class YamlFile extends FilesManager {

    private final FilesKeys fileName;
    private YamlConfiguration file;

    public YamlFile(@NotNull DarkWaterJavaPlugin plugin, @NotNull FilesKeys fileName) {
        super(plugin);
        if (isFileNotExists(fileName)) {
            copyFile(fileName);
        }
        this.fileName = fileName;
        this.file = YamlConfiguration.loadConfiguration(getFile(fileName));
        try {
            checkVersion(plugin);
        } catch (YamlFileException e) {
            plugin.sendLog(Level.WARNING,
                           "An error occurred while copying the new version of the file '&c" + getFile(fileName).getName() + "&6'! Message: " + e.getMessage());
        }
        plugin.sendLog("File '&b" + getFileName(fileName) + "&a' loaded");
    }

    private void checkVersion(@NotNull DarkWaterJavaPlugin plugin) throws YamlFileException {
        var cfgVersion = getDouble(Config.CONFIG_VERSION);
        var tmpCfg = File.CONFIG_TMP_YML;
        deleteFile(tmpCfg);
        copyFile(fileName, tmpCfg);
        var newVersion = YamlConfiguration.loadConfiguration(getFile(tmpCfg)).getDouble(getKeyString(Config.CONFIG_VERSION));
        if (newVersion > cfgVersion || newVersion < cfgVersion) {
            try {
                var oldCfg = File.CONFIG_OLD_YML;
                deleteFile(oldCfg);
                copyFileInFolder(fileName, oldCfg);
                deleteFile(fileName);
                copyFile(fileName);
                this.file = YamlConfiguration.loadConfiguration(getFile(fileName));
                var cfgName = getFile(fileName).getName();
                plugin.sendLog(Level.WARNING,
                               "Version mismatch found for file '&c" + cfgName + "&6'. This file has been renamed to '&c" + getFile(oldCfg).getName() + "&6' and a new file '&c" + cfgName + "&6' has been created");
            } catch (Exception e) {
                throw new YamlFileException(e);
            }
        }
        deleteFile(tmpCfg);
    }

    public void reload() {
        this.file = YamlConfiguration.loadConfiguration(getFile(fileName));
    }

    public @NotNull String getString(@NotNull YamlKeys key) {
        var string = file.getString(getKeyString(key));
        return string != null ? string : "";
    }

    public boolean getBoolean(@NotNull YamlKeys key) {
        return file.getBoolean(getKeyString(key));
    }

    public double getDouble(@NotNull YamlKeys key) {
        return file.getDouble(getKeyString(key));
    }

    public int getInt(@NotNull YamlKeys key) {
        return file.getInt(getKeyString(key));
    }

    public @Nullable ItemStack getItemStack(@NotNull YamlKeys key) {
        return file.getItemStack(getKeyString(key));
    }

    public @NotNull List<String> getStringList(@NotNull YamlKeys key) {
        return file.getStringList(getKeyString(key));
    }

    private @NotNull String getKeyString(@NotNull YamlKeys key) {
        return key.toString().toLowerCase().replace("_", ".");
    }

}

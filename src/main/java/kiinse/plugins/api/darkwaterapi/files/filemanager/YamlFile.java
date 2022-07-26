package kiinse.plugins.api.darkwaterapi.files.filemanager;

import kiinse.plugins.api.darkwaterapi.files.config.interfaces.ConfigKeys;
import kiinse.plugins.api.darkwaterapi.files.config.utils.Config;
import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.FilesKeys;
import kiinse.plugins.api.darkwaterapi.files.filemanager.utils.File;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class YamlFile extends FilesManager {

    private YamlConfiguration file;
    private final FilesKeys fileName;

    public YamlFile(DarkWaterJavaPlugin plugin, FilesKeys fileName) {
        super(plugin);
        if (isFileNotExists(fileName)) {
            copyFile(fileName);
        }
        this.fileName = fileName;
        this.file = YamlConfiguration.loadConfiguration(getFile(fileName));
        checkVersion(plugin);
        plugin.sendLog("File '&b" + getFileName(fileName) + "&a' loaded");
    }

    private void checkVersion(DarkWaterJavaPlugin plugin) {
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
                plugin.sendLog(Level.WARNING, "Version mismatch found for file '&c" + cfgName + "&6'. This file has been renamed to '&c" + getFile(oldCfg).getName() + "&6' and a new file '&c" + cfgName + "&6' has been created");
            } catch (Exception e) {
                plugin.sendLog(Level.WARNING, "An error occurred while copying the new version of the file '&c" + getFile(fileName).getName() + "&6'! Message: " + e.getMessage());
            }
        }
        deleteFile(tmpCfg);
    }

    public void reload() {
        this.file = YamlConfiguration.loadConfiguration(getFile(fileName));
    }

    public String getString(ConfigKeys key) {
        return file.getString(getKeyString(key));
    }

    public boolean getBoolean(ConfigKeys key) {
        return file.getBoolean(getKeyString(key));
    }

    public double getDouble(ConfigKeys key) {
        return file.getDouble(getKeyString(key));
    }

    public int getInt(ConfigKeys key) {
        return file.getInt(getKeyString(key));
    }

    public ItemStack getItemStack(ConfigKeys key) {
        return file.getItemStack(getKeyString(key));
    }

    public List<String> getStringList(ConfigKeys key) {
        return file.getStringList(getKeyString(key));
    }

    private String getKeyString(ConfigKeys key) {
        return key.toString().toLowerCase().replace("_", ".");
    }

}

package kiinse.plugins.api.darkwaterapi.files.config;

import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Configuration extends YamlFile {

    public Configuration(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin, plugin.getConfigurationFileName());
    }

}

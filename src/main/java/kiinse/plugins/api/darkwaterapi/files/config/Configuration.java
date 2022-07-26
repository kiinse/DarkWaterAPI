package kiinse.plugins.api.darkwaterapi.files.config;

import kiinse.plugins.api.darkwaterapi.files.filemanager.YamlFile;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;

public class Configuration extends YamlFile {

    public Configuration(DarkWaterJavaPlugin plugin) {
        super(plugin, plugin.getConfigurationFileName());
    }

}

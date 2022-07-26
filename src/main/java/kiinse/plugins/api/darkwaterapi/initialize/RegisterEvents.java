package kiinse.plugins.api.darkwaterapi.initialize;

import kiinse.plugins.api.darkwaterapi.DarkWaterAPI;
import kiinse.plugins.api.darkwaterapi.listeners.*;

public class RegisterEvents {

    public RegisterEvents(DarkWaterAPI darkWaterAPI){
        darkWaterAPI.sendLog("Registering listeners...");
        var pluginManager = darkWaterAPI.getServer().getPluginManager();
        pluginManager.registerEvents(new EntityDeathListener(), darkWaterAPI);
        pluginManager.registerEvents(new JumpListener(darkWaterAPI), darkWaterAPI);
        pluginManager.registerEvents(new MoveListener(darkWaterAPI), darkWaterAPI);
        pluginManager.registerEvents(new GUIListener(), darkWaterAPI);
        pluginManager.registerEvents(new CloseInventoryListener(), darkWaterAPI);
        pluginManager.registerEvents(new OnJoinListener(), darkWaterAPI);
        pluginManager.registerEvents(new OnQuitListener(darkWaterAPI), darkWaterAPI);
        darkWaterAPI.sendLog("Listeners registered");
    }
}

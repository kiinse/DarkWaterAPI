package kiinse.plugins.api.darkwaterapi.files.filemanager;

import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.FilesKeys;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
public class JsonFile extends FilesManager {

    private final DarkWaterJavaPlugin plugin;
    private final File file;

    public JsonFile(@NotNull DarkWaterJavaPlugin plugin, @NotNull FilesKeys fileName) {
        super(plugin);
        this.plugin = plugin;
        if (isFileNotExists(fileName)) {
            copyFile(fileName);
        }
        this.file = getFile(fileName);
    }

    public @NotNull JSONObject getJsonFromFile() throws IOException {
        try (var br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            var line = br.readLine();
            if (line == null) {
                return new JSONObject();
            }
            var json = new JSONObject(Files.readString(Paths.get(file.getAbsolutePath())));
            plugin.sendLog("File '&b" + file.getName() + "&a' loaded");
            return json;
        }
    }

    public void saveJsonToFile(@NotNull JSONObject json) throws IOException {
        if (!file.exists() && file.createNewFile()) {
            plugin.sendLog("File '&b" + file.getName() + "&a' created");
        }
        var lines = List.of(json.toString());
        Files.write(Paths.get(file.getAbsolutePath()), lines, StandardCharsets.UTF_8);
        plugin.sendLog("File '&b" + file.getName() + "&a' saved");
    }

}

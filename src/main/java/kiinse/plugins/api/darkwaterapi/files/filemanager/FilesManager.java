package kiinse.plugins.api.darkwaterapi.files.filemanager;

import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.DirectoriesKeys;
import kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces.FilesKeys;
import kiinse.plugins.api.darkwaterapi.loader.DarkWaterJavaPlugin;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;

public abstract class FilesManager {

    private final DarkWaterJavaPlugin plugin;

    protected FilesManager(DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createFile(FilesKeys file) throws IOException {
        var destFile = getFile(file);
        if (destFile.createNewFile()) {
            plugin.sendLog(Level.CONFIG, "File '&d" + destFile.getName() + "&6' created");
        }
    }

    public void createDirectory(DirectoriesKeys directory) throws SecurityException {
        var destDirectory = getFile(directory);
        if (destDirectory.exists()) {
            deleteFile(directory);
        }
        if (destDirectory.mkdirs()) {
            plugin.sendLog(Level.CONFIG, "Directory '&d" + destDirectory.getName() + "&6' created");
        }
    }

    public void copyFile(FilesKeys file) {
        copyFileMethod(file, file);
    }

    public void copyFile(FilesKeys oldFile, FilesKeys newFile) {
        copyFileMethod(oldFile, newFile);
    }

    private void copyFileMethod(FilesKeys oldFile, FilesKeys newFile) {
        var destFile = getFile(newFile);
        if (!destFile.exists()) {
            var inputStream = accessFile(oldFile);
            if (inputStream != null) {
                try {
                    FileUtils.copyInputStreamToFile(inputStream, destFile);
                    plugin.sendLog(Level.CONFIG, "File '&d" + destFile.getName() + "&6' created");
                } catch (IOException e) {
                    plugin.sendLog(Level.WARNING, "Error on copying file '&c" + destFile.getName() + "&6'! Message: " + e.getMessage());
                }
            } else {
                plugin.sendLog(Level.WARNING, "File '&c" + getFileName(oldFile) + "&6' not found inside plugin jar. Creating a new file...");
                try {
                    createFile(newFile);
                } catch (IOException e) {
                    plugin.sendLog(Level.SEVERE, "Error on creating file '" + destFile.getName() + "'! Message: " + e.getMessage());
                }
            }
        }
    }

    public void copyFile(DirectoriesKeys directory) {
        var destDirectory = getFile(directory);
        if (!destDirectory.exists() || listFilesInDirectory(directory).isEmpty()) {
            try {
                createDirectory(directory);
                for (var file : getFilesInDirectoryInJar(directory)) {
                    FileUtils.copyInputStreamToFile(accessFile(getFileName(directory) + "/" + file), new File(getDataFolder() + getFileName(directory) + File.separator + file));
                }
            } catch (Exception e) {
                plugin.sendLog(Level.WARNING, "Error on copying directory '&c" + destDirectory.getName() + "&6'! Message: " + e.getMessage());
                deleteFile(directory);
            }
        }
    }

    public List<File> listFilesInDirectory(DirectoriesKeys directory) {
        var folder = getFile(directory);
        var list = new ArrayList<File>();
        if (folder == null) {
            return list;
        }
        Collections.addAll(list, Objects.requireNonNull(folder.listFiles()));
        return list;
    }

    public void copyFileInFolder(FilesKeys file1, FilesKeys file2) throws IOException {
        var file = getFile(file2);
        var oldFile = getFile(file1);
        if (!file.exists()) {
            FileUtils.copyFile(oldFile, file);
            plugin.sendLog(Level.CONFIG, "File '&d" + oldFile.getName() + "&6' copied to file '&d" + file.getName() + "&6'");
        }
    }

    public File getFile(FilesKeys file) {
        return new File(getDataFolder() + getFileName(file));
    }

    public File getFile(DirectoriesKeys directory) {
        return new File(getDataFolder() + getFileName(directory));
    }

    public InputStream accessFile(FilesKeys file) {
        var input = plugin.getClass().getResourceAsStream(getFileName(file));
        if (input == null) {
            input = plugin.getClass().getClassLoader().getResourceAsStream(getFileName(file));
        }
        return input;
    }

    private InputStream accessFile(String file) {
        var input = plugin.getClass().getResourceAsStream(file);
        if (input == null) {
            input = plugin.getClass().getClassLoader().getResourceAsStream(file);
        }
        return input;
    }

    public List<String> getFilesInDirectoryInJar(DirectoriesKeys directory) throws Exception {
        var list = new ArrayList<String>();
        for (var file : getResourceUrls("classpath:/" + getFileName(directory) + "/*.*")) {
            var split = file.split("/");
            list.add(split[split.length-1]);
        }
        return list;
    }

    private List<String> getResourceUrls(String locationPattern) throws IOException {
        var resolver = new PathMatchingResourcePatternResolver(plugin.getClass().getClassLoader());
        return Arrays.stream(resolver.getResources(locationPattern))
                .map(this::toURL)
                .filter(Objects::nonNull)
                .toList();
    }

    private String toURL(Resource r) {
        try {
            if (r == null) {
                return null;
            }
            return r.getURL().toExternalForm();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean isFileNotExists(FilesKeys file) {
        return !getFile(file).exists();
    }

    public boolean isFileNotExists(DirectoriesKeys file) {
        return !getFile(file).exists();
    }

    public String getDataFolder() {
        return plugin.getDataFolder() + File.separator;
    }

    public String getFileName(FilesKeys key) {
        return key.toString().toLowerCase().replace("_", ".");
    }

    public String getFileName(DirectoriesKeys directory) {
        return directory.toString().toLowerCase();
    }

    public void deleteFile(FilesKeys file) {
        if (getFile(file).delete()) {
            plugin.sendLog(Level.CONFIG, "File '&d" + getFile(file).getName() + "&6' deleted");
        }
    }

    public void deleteFile(DirectoriesKeys file) {
        if (getFile(file).delete()) {
            plugin.sendLog(Level.CONFIG, "File '&d" + getFile(file).getName() + "&6' deleted");
        }
    }
}

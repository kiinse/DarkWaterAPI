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

package kiinse.plugins.api.darkwaterapi.files.filemanager.interfaces;

import kiinse.plugins.api.darkwaterapi.loader.interfaces.DarkWaterJavaPlugin;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;

public abstract class FilesManager {

    private final DarkWaterJavaPlugin plugin;

    protected FilesManager(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createFile(@NotNull FilesKeys file) throws IOException {
        var destFile = getFile(file);
        if (destFile.createNewFile()) {
            plugin.sendLog(Level.CONFIG, "File '&d" + destFile.getName() + "&6' created");
        }
    }

    public void createDirectory(@NotNull DirectoriesKeys directory) throws SecurityException {
        var destDirectory = getFile(directory);
        if (destDirectory.exists()) {
            deleteFile(directory);
        }
        if (destDirectory.mkdirs()) {
            plugin.sendLog(Level.CONFIG, "Directory '&d" + destDirectory.getName() + "&6' created");
        }
    }

    public void copyFile(@NotNull FilesKeys file) {
        copyFileMethod(file, file);
    }

    public void copyFile(@NotNull FilesKeys oldFile, @NotNull FilesKeys newFile) {
        copyFileMethod(oldFile, newFile);
    }

    private void copyFileMethod(@NotNull FilesKeys oldFile, @NotNull FilesKeys newFile) {
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

    public void copyFile(@NotNull DirectoriesKeys directory) {
        var destDirectory = getFile(directory);
        if (!destDirectory.exists() || listFilesInDirectory(directory).isEmpty()) {
            try {
                createDirectory(directory);
                for (var file : getFilesInDirectoryInJar(directory)) {
                    FileUtils.copyInputStreamToFile(Objects.requireNonNull(accessFile(getFileName(directory) + "/" + file)), new File(getDataFolder() + getFileName(directory) + File.separator + file));
                }
            } catch (Exception e) {
                plugin.sendLog(Level.WARNING, "Error on copying directory '&c" + destDirectory.getName() + "&6'! Message: " + e.getMessage());
                deleteFile(directory);
            }
        }
    }

    public @NotNull List<File> listFilesInDirectory(@NotNull DirectoriesKeys directory) {
        var list = new ArrayList<File>();
        Collections.addAll(list, Objects.requireNonNull(getFile(directory).listFiles()));
        return list;
    }

    public void copyFileInFolder(@NotNull FilesKeys file1, @NotNull FilesKeys file2) throws IOException {
        var file = getFile(file2);
        var oldFile = getFile(file1);
        if (!file.exists()) {
            FileUtils.copyFile(oldFile, file);
            plugin.sendLog(Level.CONFIG, "File '&d" + oldFile.getName() + "&6' copied to file '&d" + file.getName() + "&6'");
        }
    }

    public @NotNull File getFile(@NotNull FilesKeys file) {
        return new File(getDataFolder() + getFileName(file));
    }

    public @NotNull File getFile(@NotNull DirectoriesKeys directory) {
        return new File(getDataFolder() + getFileName(directory));
    }

    public @Nullable InputStream accessFile(@NotNull FilesKeys file) {
        var input = plugin.getClass().getResourceAsStream(getFileName(file));
        if (input == null) {
            input = plugin.getClass().getClassLoader().getResourceAsStream(getFileName(file));
        }
        return input;
    }

    private @Nullable InputStream accessFile(@NotNull String file) {
        var input = plugin.getClass().getResourceAsStream(file);
        if (input == null) {
            input = plugin.getClass().getClassLoader().getResourceAsStream(file);
        }
        return input;
    }

    public @NotNull List<String> getFilesInDirectoryInJar(@NotNull DirectoriesKeys directory) throws Exception {
        var list = new ArrayList<String>();
        for (var file : getResourceUrls("classpath:/" + getFileName(directory) + "/*.*")) {
            var split = file.split("/");
            list.add(split[split.length-1]);
        }
        return list;
    }

    private @NotNull List<String> getResourceUrls(@NotNull String locationPattern) throws IOException {
        var resolver = new PathMatchingResourcePatternResolver(plugin.getClass().getClassLoader());
        return Arrays.stream(resolver.getResources(locationPattern))
                .map(this::toURL)
                .filter(Objects::nonNull)
                .toList();
    }

    private @Nullable String toURL(@NotNull Resource r) {
        try {
            return r.getURL().toExternalForm();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean isFileNotExists(@NotNull FilesKeys file) {
        return !getFile(file).exists();
    }

    public boolean isFileNotExists(@NotNull DirectoriesKeys file) {
        return !getFile(file).exists();
    }

    public @NotNull String getDataFolder() {
        return plugin.getDataFolder() + File.separator;
    }

    public @NotNull String getFileName(@NotNull FilesKeys key) {
        return key.toString().toLowerCase().replace("_", ".");
    }

    public @NotNull String getFileName(@NotNull DirectoriesKeys directory) {
        return directory.toString().toLowerCase();
    }

    public void deleteFile(@NotNull FilesKeys file) {
        if (getFile(file).delete()) {
            plugin.sendLog(Level.CONFIG, "File '&d" + getFile(file).getName() + "&6' deleted");
        }
    }

    public void deleteFile(@NotNull DirectoriesKeys file) {
        if (getFile(file).delete()) {
            plugin.sendLog(Level.CONFIG, "File '&d" + getFile(file).getName() + "&6' deleted");
        }
    }
}

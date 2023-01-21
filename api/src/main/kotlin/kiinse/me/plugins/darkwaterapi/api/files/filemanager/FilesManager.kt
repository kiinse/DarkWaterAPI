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
package kiinse.me.plugins.darkwaterapi.api.files.filemanager

import kiinse.me.plugins.darkwaterapi.api.DarkWaterJavaPlugin
import org.apache.commons.io.FileUtils
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.logging.Level

@Suppress("unused")
abstract class FilesManager protected constructor(plugin: DarkWaterJavaPlugin) {
    private val plugin: DarkWaterJavaPlugin

    init {
        this.plugin = plugin
    }

    @Throws(IOException::class)
    fun createFile(file: FilesKeys) {
        val destFile = getFile(file)
        if (destFile.createNewFile()) plugin.sendLog(Level.CONFIG, "File '&d" + destFile.name + "&6' created")
    }

    @Throws(SecurityException::class)
    fun createDirectory(directory: DirectoriesKeys) {
        val destDirectory = getFile(directory)
        if (destDirectory.exists()) deleteFile(directory)
        if (destDirectory.mkdirs()) plugin.sendLog(Level.CONFIG, "Directory '&d" + destDirectory.name + "&6' created")
    }

    fun copyFile(file: FilesKeys) {
        copyFileMethod(file, file)
    }

    fun copyFile(oldFile: FilesKeys, newFile: FilesKeys) {
        copyFileMethod(oldFile, newFile)
    }

    private fun copyFileMethod(oldFile: FilesKeys, newFile: FilesKeys) {
        val destFile = getFile(newFile)
        if (!destFile.exists()) {
            val inputStream = accessFile(oldFile)
            if (inputStream != null) {
                try {
                    FileUtils.copyInputStreamToFile(inputStream, destFile)
                    plugin.sendLog(Level.CONFIG, "File '&d" + destFile.name + "&6' created")
                } catch (e: IOException) {
                    plugin.sendLog("Error on copying file '&c" + destFile.name + "&6'! Message:", e)
                }
            } else {
                plugin.sendLog(Level.WARNING, "File '&c" + getFileName(oldFile) + "&6' not found inside plugin jar. Creating a new file...")
                try {
                    createFile(newFile)
                } catch (e: IOException) {
                    plugin.sendLog("Error on creating file '" + destFile.name + "'! Message:", e)
                }
            }
        }
    }

    fun copyFile(directory: DirectoriesKeys) {
        val destDirectory = getFile(directory)
        if (!destDirectory.exists() || listFilesInDirectory(directory).isEmpty()) {
            try {
                createDirectory(directory)
                for (file in getFilesInDirectoryInJar(directory)) {
                    FileUtils.copyInputStreamToFile(Objects.requireNonNull(accessFile(getFileName(directory) + "/" + file)),
                                                    File(dataFolder + getFileName(directory) + File.separator + file))
                }
            } catch (e: Exception) {
                plugin.sendLog("Error on copying directory '&c" + destDirectory.name + "&6'! Message:", e)
                deleteFile(directory)
            }
        }
    }

    fun listFilesInDirectory(directory: DirectoriesKeys): List<File> {
        val list = ArrayList<File>()
        Collections.addAll(list, *Objects.requireNonNull(getFile(directory).listFiles()))
        return list
    }

    @Throws(IOException::class)
    fun copyFileInFolder(file1: FilesKeys, file2: FilesKeys) {
        val file = getFile(file2)
        val oldFile = getFile(file1)
        if (!file.exists()) {
            FileUtils.copyFile(oldFile, file)
            plugin.sendLog(Level.CONFIG, "File '&d" + oldFile.name + "&6' copied to file '&d" + file.name + "&6'")
        }
    }

    fun getFile(file: FilesKeys): File {
        return File(dataFolder + getFileName(file))
    }

    fun getFile(directory: DirectoriesKeys): File {
        return File(dataFolder + getFileName(directory))
    }

    fun accessFile(file: FilesKeys): InputStream? {
        var input = plugin.javaClass.getResourceAsStream(getFileName(file))
        if (input == null) input = plugin.javaClass.classLoader.getResourceAsStream(getFileName(file))
        return input
    }

    private fun accessFile(file: String): InputStream? {
        var input = plugin.javaClass.getResourceAsStream(file)
        if (input == null) input = plugin.javaClass.classLoader.getResourceAsStream(file)
        return input
    }

    @Throws(Exception::class)
    fun getFilesInDirectoryInJar(directory: DirectoriesKeys): List<String> {
        val list = ArrayList<String>()
        getResourceUrls("classpath:/" + getFileName(directory) + "/*.*").forEach { name ->
            val split = name!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            list.add(split[split.size - 1])
        }
        return list
    }

    @Throws(IOException::class)
    private fun getResourceUrls(locationPattern: String): List<String?> {
        return Arrays.stream(PathMatchingResourcePatternResolver(plugin.javaClass.classLoader).getResources(locationPattern))
                .map { r: Resource -> toURL(r) }
                .filter { obj: String? -> Objects.nonNull(obj) }
                .toList()
    }

    private fun toURL(r: Resource): String? {
        return try {
            r.url.toExternalForm()
        } catch (e: IOException) { null }
    }

    fun isFileNotExists(file: FilesKeys): Boolean {
        return !getFile(file).exists()
    }

    fun isFileNotExists(file: DirectoriesKeys): Boolean {
        return !getFile(file).exists()
    }

    fun isDirectoryEmpty(file: DirectoriesKeys): Boolean {
        return listFilesInDirectory(file).isEmpty()
    }

    val dataFolder: String
        get() = plugin.dataFolder.absolutePath + File.separator

    fun getFileName(key: FilesKeys): String {
        return key.toString().lowercase(Locale.getDefault()).replace("_", ".")
    }

    fun getFileName(directory: DirectoriesKeys): String {
        return directory.toString().lowercase(Locale.getDefault())
    }

    fun deleteFile(file: FilesKeys) {
        if (getFile(file).delete()) plugin.sendLog(Level.CONFIG, "File '&d" + getFile(file).name + "&6' deleted")
    }

    fun deleteFile(file: DirectoriesKeys) {
        if (getFile(file).delete()) plugin.sendLog(Level.CONFIG, "File '&d" + getFile(file).name + "&6' deleted")
    }
}